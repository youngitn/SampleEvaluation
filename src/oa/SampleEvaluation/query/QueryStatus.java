package oa.SampleEvaluation.query;

public class QueryStatus {

	public static String getFlowStateSqlStrByQueryCondition(String queryFlowStatus) {

		StringBuilder stateCondition = new StringBuilder();
		if (!queryFlowStatus.equals("")) {
			if ("�w����".equals(queryFlowStatus))
				stateCondition.append("= '����'");
			if ("ñ�֤�".equals(queryFlowStatus))
				stateCondition.append("not in ('����','����')");
			if ("�ݳB�z".equals(queryFlowStatus))
				stateCondition.append(" = '�ݳB�z' ");

			stateCondition.append(
					"AND PNO in (SELECT PNO FROM SAMPLE_EVALUATION_FLOWC WHERE F_INP_STAT " + stateCondition + " )");
		}
		return stateCondition.toString();
	}

	

}
