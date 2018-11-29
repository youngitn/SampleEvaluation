package oa.SampleEvaluation.flow.approve;

import jcx.jform.bProcFlow;
import oa.SampleEvaluation.flow.approve.gateEnum.*;

import com.ysp.service.BaseService;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.common.global.FlowcUtil;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import jcx.db.*;

public class Approve extends bProcFlow {

	String nowState;
	talk t;
	String isCheckValue;
	String isTrialProdValue;

	public boolean action(String value) throws Throwable {
		nowState = getState();
		t = getTalk();
		SampleEvaluation s = null;
		BaseService service = new BaseService(this);
		String labExe = getValue("LAB_EXE").trim();
		String lassessor = getValue("ASSESSOR").trim();
		String docCtrler = getValue("DOC_CTRLER").trim();
		String designee = getValue("DESIGNEE").trim();
		designee.trim().split(" ");
		getValue("PNO");
		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		boolean ret = doReminder("");
		switch (FlowState.valueOf(nowState)) {
		case �ժ�:

			if ((isCheckValue.equals("1") || isTrialProdValue.equals("1")) && labExe.equals("")) {
				message("�п�ܹ���Ǹg��H��");
				ret = false;
			}

			if (isTrialProdValue.equals("1") && lassessor.equals("")) {
				message("�п�ܸջs�����H��");
				ret = false;
			}

			if ((isTrialProdValue.equals("1") || isCheckValue.equals("1")) && docCtrler.equals("")) {
				message("�п�ܤ�ޤH��");
				ret = false;
			}
			if (getValue("QR_NO").trim().equals("")) {
				message("�ж�gQR���X");
				ret = false;
			}
			if (ret) {
				// ��s�D�����M�ջs�����Ŀ����
				// ��s�D�� �����H���M����Ǹg��
				s = new SampleEvaluation();
				s.setAllValue(service);
				new SampleEvaluationDaoImpl(t).update(s);

				// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��
				if (isCheckValue.equals("1")) {

					SampleEvaluationSubBaseDto secDto = new SampleEvaluationCheck();
					secDto.setAllValue(service);
					FlowcUtil.goSubFlow("Check", secDto, t, "��g����渹");//�O�o�]�_�l���d
					String title = "ñ�ֳq���G" + this.getFunctionName() + "_����y�{";
					// ������y�{ �H�X�q���H
					MailToolInApprove.sendSubFlowMail(service, getValue("DOC_CTRLER"), secDto, title);

				}
				if (isTrialProdValue.equals("1")) {

					SampleEvaluationSubBaseDto setDto = new SampleEvaluationTp();
					setDto.setAllValue(service);
					FlowcUtil.goSubFlow("Tp", setDto, t, "�����H��");
					String title = "ñ�ֳq���G" + this.getFunctionName() + "_�ջs�y�{";
					// ���ջs�y�{ �H�X�q���H
					MailToolInApprove.sendSubFlowMail(service, getValue("ASSESSOR"), setDto, title);
				}
			}
			break;
		case ���z���D�ޤ���:
			// ��s�D����פH���
			if (ret && !designee.equals("")) {
				t.execFromPool("UPDATE  sample_evaluation  SET DESIGNEE=?" + " where pno=?",
						new Object[] { designee, getValue("PNO") });
				t.execFromPool("UPDATE  sample_evaluation_check  SET DESIGNEE=?" + " where own_pno=?",
						new Object[] { designee, getValue("OWN_PNO") });
			} else {
				message("�п�� ���z�������H�� ���");
				ret = false;
			}
			break;
		case �ݳB�z:
		case ���ʸg��T�{:
			// ��s�D����פH���
			if (ret) {
				FileItemSetChecked();
				s = new SampleEvaluation();
				s.setAllValue(service);
				new SampleEvaluationDaoImpl(t).update(s);
			}
			break;
		case ���ʸg��:

			if (getValue("IS_CHECK").equals("0") && getValue("IS_TRIAL_PRODUCTION").equals("0")) {
				ret = true;
			} else if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("�������G�P�䧨�ɤ��o����");
				ret = false;
			} else if (ret) {
				FileItemSetChecked();
				s = new SampleEvaluation();
				s.setAllValue(service);
				new SampleEvaluationDaoImpl(t).update(s);
				ret = true;
			}
			break;

		default:
			break;
		}
		return ret;
	}

	private void FileItemSetChecked() {

		if (!getValue("FILE_SPEC").equals("")) {
			setValue("PROVIDE_SPEC", "1");
		}
		if (!getValue("FILE_COA").equals("")) {
			setValue("PROVIDE_COA", "1");
		}
		if (!getValue("FILE_SDS").equals("")) {
			setValue("PROVIDE_SDS", "1");
		}
		if (!getValue("FILE_OTHERS").equals("")) {
			setValue("PROVIDE_OTHERS", "1");
		}
		if (!getValue("FILE_TEST_METHOD").equals("")) {
			setValue("PROVIDE_TEST_METHOD", "1");
		}
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
