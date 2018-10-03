package oa.SampleEvaluationCheck.flow.approve;

import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisDao;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.*;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowcHis;
import jcx.jform.bProcFlow;

import com.ysp.service.BaseService;
import com.ysp.util.DateTimeUtil;

import jcx.db.*;

public class Approve extends bProcFlow {

	String table_name = "MIS_SERVICE";

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"

		String state = getState();
		talk t = getTalk();

		switch (FlowState.valueOf(state)) {
		case 填寫請驗單號:

			if (getValue("NOTIFY_NO_CHECK").trim().equals("")) {
				message("請填寫請驗單號");
				return false;
			}
			// 更新主表請驗單號欄位
			t.execFromPool("UPDATE  sample_evaluation  SET notify_no_check=?" + " where pno=?",
					new Object[] { getValue("NOTIFY_NO_CHECK"), getValue("PNO") });

			// 更新子流程主表請驗單號欄位
			t.execFromPool("UPDATE  sample_evaluation_check  SET notify_no_check=?" + " where own_pno=?",
					new Object[] { getValue("NOTIFY_NO_CHECK"), getValue("OWN_PNO") });
			message("簽核完成！");
			break;
		case 組長:

			// 建立子流程FLOWC物件 使其出現在待簽核表單列表
			if (getValue("IS_CHECK").trim().equals("1")) {
				BaseService service = new BaseService(this);
				SampleEvaluationCheck sc = new SampleEvaluationCheck();
				sc = sc.setAllValue(sc, service);
				SampleEvaluationCheckDao checkDao = new SampleEvaluationCheckDao(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update(sc);
				} else {
					// insert一筆子流程主檔
					checkDao.add(sc);

					SampleEvaluationCheckFlowc flowc = new SampleEvaluationCheckFlowc(sc.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					// 取得被分案組長empid
					String[] designee = getValue("DESIGNEE").trim().split(" ");
					flowc.setF_INP_ID(designee[0]);
					flowc.setF_INP_STAT("填寫請驗單號");
					flowc.setF_INP_TIME(time);
					SampleEvaluationCheckFlowcDao secfDao = new SampleEvaluationCheckFlowcDao();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// 建立子流程FLOWC_HIS 物件 能夠顯示簽核歷史
					time = DateTimeUtil.getApproveAddSeconds(0);
					SampleEvaluationCheckFlowcHis his = new SampleEvaluationCheckFlowcHis(sc.getOwnPno(),
							flowc.getF_INP_STAT(), time);

					his.setF_INP_ID(designee[0]);
					SampleEvaluationCheckFlowcHisDao secfhDao = new SampleEvaluationCheckFlowcHisDao();
					secfhDao.create(getTalk().getConnectionFromPool(), his);

				}
			}
			break;
		default:

			break;
		}
		return true;

	}

}
