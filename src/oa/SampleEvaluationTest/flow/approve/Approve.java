package oa.SampleEvaluationTest.flow.approve;

//oa/SampleEvaluationTest/flow/approve/Approve
import oa.SampleEvaluation.flow.approve.BaseSubApprove;

public class Approve extends BaseSubApprove {

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"
		boolean ret = doReminder("");
		if (ret) {
			syncData();
		}
		return ret;

	}

}
