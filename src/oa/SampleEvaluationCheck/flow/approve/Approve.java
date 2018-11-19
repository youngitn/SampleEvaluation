package oa.SampleEvaluationCheck.flow.approve;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.daointerface.IFlowcDao;
import oa.SampleEvaluation.dto.FlowcDto;
import oa.SampleEvaluation.dto.FlowcHisDto;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckDaoImpl;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcDaoImpl;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisDaoImpl;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.*;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpDaoImpl;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import jcx.jform.bProcFlow;

import com.ysp.service.BaseService;
import com.ysp.util.DateTimeUtil;

import jcx.db.*;

public class Approve extends bProcFlow {

	String table_name = "MIS_SERVICE";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"

		String state = getState();
		talk t = getTalk();
		boolean ret = doReminder("");
		switch (FlowState.valueOf(state)) {
		case 填寫請驗單號:
		case 實驗室經辦:
			/*
			 * 判斷請驗單號欄位是否空值會在填寫請驗單號舊處理完畢
			 *到實驗室經辦時 只會同步更新三表 
			 * */
			
			if (getValue("NOTIFY_NO_CHECK").trim().equals("") || getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				message("請填寫原料或試製品請驗單號,如果未進行請驗請直接在欄位中填寫原因.");
				ret = false;
			}
			if (ret) {
				// 三表同步
				BaseService service = new BaseService(this);

				SampleEvaluationTpDaoImpl tpDao = new SampleEvaluationTpDaoImpl(getTalk());
				SampleEvaluationTp tp = new SampleEvaluationTp();
				tp.setAllValue(service);
				tpDao.update(tp);
				// message(service.getValue("FIELD9") + " " + tp.getFile9());
				// message(tp.getFile1());

				SampleEvaluationCheckDaoImpl ckDao = new SampleEvaluationCheckDaoImpl(getTalk());
				SampleEvaluationCheck ck = new SampleEvaluationCheck();
				ck.setAllValue(service);
				ckDao.update(ck);

				SampleEvaluationDaoImpl seDao = new SampleEvaluationDaoImpl(getTalk());
				SampleEvaluation se = new SampleEvaluation();
				se.setAllValue(service);
				seDao.update(se);
				// message("簽核完成！");
			}
			return ret;

		case 組長:// 目前未開放這個關卡
			// 能退?要退去哪?
			// 建立子流程FLOWC物件 使其出現在待簽核表單列表
			if (getValue("IS_CHECK").trim().equals("1")) {
				BaseService service = new BaseService(this);
				SampleEvaluationSubBaseDto sc = new SampleEvaluationCheck(service);
				SampleEvaluationCheckDaoImpl checkDao = new SampleEvaluationCheckDaoImpl(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update((SampleEvaluationCheck) sc);
				} else {
					// insert一筆子流程主檔
					checkDao.add((SampleEvaluationCheck) sc);

					FlowcDto flowc = new FlowcDto(sc.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					// 取得被分案組長empid
					String[] designee = getValue("DESIGNEE").trim().split(" ");
					flowc.setF_INP_ID(designee[0]);
					flowc.setF_INP_STAT("填寫請驗單號");
					flowc.setF_INP_TIME(time);
					IFlowcDao secfDao = new SampleEvaluationCheckFlowcDaoImpl();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// 建立子流程FLOWC_HIS 物件 能夠顯示簽核歷史
					time = DateTimeUtil.getApproveAddSeconds(0);
					FlowcHisDto his = new FlowcHisDto(sc.getOwnPno(), flowc.getF_INP_STAT(), time);

					his.setF_INP_ID(designee[0]);
					SampleEvaluationCheckFlowcHisDaoImpl secfhDao = new SampleEvaluationCheckFlowcHisDaoImpl();
					secfhDao.create(getTalk().getConnectionFromPool(), his);

				}
			}
			break;
		default:

			break;
		}
		return ret;

	}

	/**
	 * 溫馨提醒 不傳入 回傳true/false
	 */
	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "確定送出表單？", "溫馨提醒", 0);
		if (result == 1) {
			message("已取消送出表單");
			return false;
		}
		String space = "";
		for (int i = 0; i < 16; i++) {
			space += "&emsp;";
		}
		percent(100, space + "表單送出中，請稍候...<font color=white>");
		message("簽核完成");
		return true;
	}

}
