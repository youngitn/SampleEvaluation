package oa.SampleEvaluationTest.flow.approve;

//oa/SampleEvaluationTest/flow/approve/Approve
import oa.SampleEvaluation.flow.approve.BaseSubApprove;

public class Approve extends BaseSubApprove {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"
		boolean ret = doReminder("");
		if (ret) {
			syncData();
		}
		return ret;

	}

}
