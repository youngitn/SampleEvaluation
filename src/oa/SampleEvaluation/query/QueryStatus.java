package oa.SampleEvaluation.query;

public class QueryStatus {

	public static String getFlowStateSqlStrByQueryCondition(String queryFlowStatus) {

		StringBuilder stateCondition = new StringBuilder();
		if (!queryFlowStatus.equals("")) {
			if ("已結案".equals(queryFlowStatus))
				stateCondition.append("= '結案'");
			if ("簽核中".equals(queryFlowStatus))
				stateCondition.append("not in ('結案','取消')");
			if ("待處理".equals(queryFlowStatus))
				stateCondition.append(" = '待處理' ");

			stateCondition.append(
					"AND PNO in (SELECT PNO FROM SAMPLE_EVALUATION_FLOWC WHERE F_INP_STAT " + stateCondition + " )");
		}
		return stateCondition.toString();
	}

	

}
