package oa.SampleEvaluation.query;
/**
 * 將表單所輸入之簽核狀態查詢欄位的值轉換為sql where 條件字串
 * @author u52116
 *
 */
public class QueryStatus {

	public static String getFlowStateSqlStrByQueryCondition(String queryFlowStatus) {

		StringBuilder stateCondition = new StringBuilder();
		String sc = "";
		if (!queryFlowStatus.equals("")) {
			if ("已結案".equals(queryFlowStatus))
				sc = "= '結案'";
			else if ("簽核中".equals(queryFlowStatus))
				sc = "not in ('結案','取消')";
			else if ("待處理".equals(queryFlowStatus))
				sc = " = '待處理' ";

			stateCondition.append("AND PNO in (SELECT PNO FROM SAMPLE_EVALUATION_FLOWC WHERE F_INP_STAT " + sc + " )");
		}
		return stateCondition.toString();
	}

}
