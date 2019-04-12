package oa.SampleEvaluationTp.flow.approve;

import oa.SampleEvaluation.flow.BaseSubApprove;

/**
 * The Class Approve.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class Approve extends BaseSubApprove {

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.flow.BaseSubApprove#action(java.lang.String)
	 */
	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准
		boolean ret = doReminder("");
		if (ret) {

			syncData();

		}
		return ret;

	}

}
