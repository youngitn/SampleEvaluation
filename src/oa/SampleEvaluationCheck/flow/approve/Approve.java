package oa.SampleEvaluationCheck.flow.approve;

import oa.SampleEvaluation.flow.approve.BaseSubApprove;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;

public class Approve extends BaseSubApprove {

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"

		String state = getState();
		boolean ret = doReminder("");
		switch (FlowState.valueOf(state)) {
		case 文管人員:
			/*
			 * 判斷請驗單號欄位是否空值會在填寫請驗單號舊處理完畢 到實驗室經辦時 只會同步更新三表
			 */

			if (getValue("NOTIFY_NO_CHECK").trim().equals("") || getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				message("請填寫原料或試製品請驗單號,如果未進行請驗請直接在欄位中填寫原因.");
				ret = false;
			}
			if (ret) {
				syncData();
			}
			return ret;
		case 品保課長:
			if (ret) {
				syncData();
			}
			break;
		case 組長:// 目前未開放這個關卡
			// 能退?要退去哪?
			// 建立子流程FLOWC物件 使其出現在待簽核表單列表

			break;
		default:

			break;
		}
		return ret;

	}

}
