package oa.SampleEvaluation.query;
/**
 * �N���ҿ�J��ñ�֪��A�d����쪺���ഫ��sql where ����r��
 * @author u52116
 *
 */
public class QueryStatus {

	public static String getFlowStateSqlStrByQueryCondition(String queryFlowStatus) {

		StringBuilder stateCondition = new StringBuilder();
		String sc = "";
		if (!queryFlowStatus.equals("")) {
			if ("�w����".equals(queryFlowStatus))
				sc = "= '����'";
			else if ("ñ�֤�".equals(queryFlowStatus))
				sc = "not in ('����','����')";
			else if ("�ݳB�z".equals(queryFlowStatus))
				sc = " = '�ݳB�z' ";

			stateCondition.append("AND PNO in (SELECT PNO FROM SAMPLE_EVALUATION_FLOWC WHERE F_INP_STAT " + sc + " )");
		}
		return stateCondition.toString();
	}

}
