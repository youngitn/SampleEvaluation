package oa.SampleEvaluationCheck.flow.approve;

import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class Approve extends bProcFlow {

	String table_name = "MIS_SERVICE";

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
				// �T��P�B
				// BaseService service = new BaseService(this);

				BaseDao service = new SampleEvaluationTpService(getTalk());
				SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
				tp.setOwnPno(tp.getPno() + "TP");
				service.update(tp);

				service = new SampleEvaluationCheckService(getTalk());
				SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(),
						this);
				ck.setOwnPno(ck.getPno() + "CHECK");
				service.update(ck);

				service = new SampleEvaluationService(getTalk());
				SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
				service.update(se);
				// message("ñ�֧����I");
			}
			return ret;

		case �ժ�:// �ثe���}��o�����d
			// ��h?�n�h�h��?
			// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��

			break;
		default:

			break;
		}
		return ret;

	}

	/**
	 * ���ɴ��� ���ǤJ �^��true/false
	 */
	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "�T�w�e�X���H", "���ɴ���", 0);
		if (result == 1) {
			message("�w�����e�X���");
			return false;
		}
		String space = "";
		for (int i = 0; i < 16; i++) {
			space += "&emsp;";
		}
		percent(100, space + "���e�X���A�еy��...<font color=white>");
		message("ñ�֧���");
		return true;
	}

}
