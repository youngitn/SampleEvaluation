package oa.SampleEvaluationCheck.flow.approve;

import oa.SampleEvaluation.flow.approve.BaseSubApprove;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;

public class Approve extends BaseSubApprove {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"

		String state = getState();
		boolean ret = doReminder("");
		switch (FlowState.valueOf(state)) {
		case ��ޤH��:
			/*
			 * �P�_����渹���O�_�ŭȷ|�b��g����渹�³B�z���� �����Ǹg��� �u�|�P�B��s�T��
			 */

			if (getValue("NOTIFY_NO_CHECK").trim().equals("") || getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				message("�ж�g��Ʃθջs�~����渹,�p�G���i�����Ъ����b��줤��g��].");
				ret = false;
			}
			if (ret) {
				syncData();
			}
			return ret;
		case �~�O�Ҫ�:
			if (ret) {
				syncData();
			}
			break;
		case �ժ�:// �ثe���}��o�����d
			// ��h?�n�h�h��?
			// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��

			break;
		default:

			break;
		}
		return ret;

	}

}
