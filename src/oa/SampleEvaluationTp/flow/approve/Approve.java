package oa.SampleEvaluationTp.flow.approve;

import oa.SampleEvaluation.flow.BaseSubApprove;

public class Approve extends BaseSubApprove {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�
		boolean ret = doReminder("");
		if (ret) {

			syncData();

		}
		return ret;

	}

}
