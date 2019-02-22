package oa.SampleEvaluationTest.flow.approve;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.FlowcUtil;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class DoCheckFlow extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"
		boolean ret = true;
		talk t = getTalk();
		ret = doReminder("�N�s�W��T�ǻ��q���� ����ñ�֬y�{  ");
		if (ret) {

			if (getValue("IS_CHECK").equals("0")) {

				if ("".equals(getValue("DOC_CTRLER"))) {
					message("�п�ܤ�ޤH��");
					return false;
				}
				setValue("IS_CHECK", "1");
				BaseService service = new BaseService(this);
				BaseDao bdservice = new SampleEvaluationCheckService(t);
				SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(),
						this);
				ck.setOwnPno(ck.getPno() + "CHECK");
				bdservice.upsert(ck);
				FlowcUtil.goCheckSubFlow(ck.getPno() + "CHECK", ck.getApplicant(), "��g����渹", t);

				String title = "ñ�ֳq���G��T�ǻ��q����_����y�{";
				// ������y�{ �H�X�q���H
				MailToolInApprove.sendSubFlowMail(service, getValue("DOC_CTRLER"), ck, title);

				bdservice = new SampleEvaluationTpService(t);
				SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
				bdservice.upsert(tp);

				bdservice = new SampleEvaluationService(t);
				SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
				bdservice.upsert(se);
				ret = true;

			} else if (getValue("IS_CHECK").equals("1")) {
				message("�L�k���жi�����y�{");
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
