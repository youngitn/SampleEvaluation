package oa.SampleEvaluationTp.flow.approve;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluation.flow.approve.Approve;

import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpDaoImpl;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

import com.ysp.service.BaseService;

import jcx.jform.bProcFlow;

public class DoCheckFlow extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"
		boolean ret = true;
		ret = doReminder("將新增資訊傳遞通知單 請驗簽核流程  ");
		if (ret) {

			if (getValue("IS_CHECK").equals("0")) {

				setValue("IS_CHECK", "1");
				BaseService service = new BaseService(this);
				SampleEvaluationSubBaseDto secDto = new SampleEvaluationCheck();
				secDto.setAllValue(service);
				Approve.goSubFlow("Check", secDto, getTalk());

				String title = "簽核通知：" + this.getFunctionName() + "_請驗流程" + "( 單號：" + getValue("PNO") + " )";
				// 有請驗流程 寄出通知信
				Approve.sendSubFlowMail(service, getValue("DOC_CTRLER"), secDto, title);

				SampleEvaluationTpDaoImpl tpDao = new SampleEvaluationTpDaoImpl(getTalk());
				SampleEvaluationTp tp = new SampleEvaluationTp();
				tp.setAllValue(service);
				tpDao.update(tp);

				SampleEvaluationDaoImpl seDao = new SampleEvaluationDaoImpl(getTalk());
				SampleEvaluation se = new SampleEvaluation();
				se.setAllValue(service);
				seDao.update(se);
				ret = true;
			} else if (getValue("IS_CHECK").equals("1")) {
				message("該試製品已經請驗過了");
				ret = false;
			}
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
