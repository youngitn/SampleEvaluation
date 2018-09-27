package oa.SampleEvaluationCheck.flow.approve;

import jcx.jform.bProcFlow;

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

		default:
			break;
		}

		message("簽核完成！");
		return true;
	}

}
