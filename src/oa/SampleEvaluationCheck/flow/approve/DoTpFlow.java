package oa.SampleEvaluationCheck.flow.approve;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.FlowcUtil;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class DoTpFlow extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"
		boolean ret = true;
		talk t = getTalk();
		ret = doReminder("�N�s�W��T�ǻ��q���� �ջsñ�֬y�{  ");
		if (ret) {

			if (getValue("IS_TRIAL_PRODUCTION").equals("0")) {
				if ("".equals(getValue("ASSESSOR")) || "".equals(getValue("LAB_EXE"))) {
					message("�Ы��w�����H��/����Ǹg��");
					return false;
				}
				setValue("IS_TRIAL_PRODUCTION", "1");
				BaseService service = new BaseService(this);
				BaseDao bdservice = new SampleEvaluationTpService(t);
				SampleEvaluationTp tk = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
				tk.setOwnPno(tk.getPno() + "TP");
				bdservice.upsert(tk);
				FlowcUtil.goTpSubFlow(tk.getPno() + "TP", tk.getApplicant(), "�����H��", t);

				String title = "ñ�ֳq���G��T�ǻ��q����_�ջs�y�{";
				// ���ջs�y�{ �H�X�q���H
				MailToolInApprove.sendSubFlowMail(service, getValue("ASSESSOR"), tk, title);

				// �P�B����D��
				bdservice = new SampleEvaluationCheckService(t);
				SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(),
						this);
				bdservice.upsert(ck);

				// �P�B�D��
				bdservice = new SampleEvaluationService(t);
				SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
				bdservice.upsert(se);
				ret = true;
			} else if (getValue("IS_TRIAL_PRODUCTION").equals("1")) {
				message("�L�k���жi��ջs�y�{");
				ret = false;
			}
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
