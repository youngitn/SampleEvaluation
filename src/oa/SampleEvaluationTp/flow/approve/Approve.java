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
