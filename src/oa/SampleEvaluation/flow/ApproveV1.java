package oa.SampleEvaluation.flow;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.enums.FlowStateEnum;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.CheckFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TestFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TpFlowBuilder;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.global.DtoUtil;

public class ApproveV1 extends bProcFlow {

	String nowState;
	talk t;
	String isCheckValue;
	String isTrialProdValue;
	String isTestValue;

	public boolean action(String value) throws Throwable {
		fileItemSetChecked();
		nowState = getState();
		t = getTalk();
		String designee = getValue("DESIGNEE").trim();

		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		this.isTestValue = getValue("IS_TEST").trim();
		boolean ret = doReminder("");

		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluationPO se = new SampleEvaluationPO();
		se.getFormData(this);

		switch (FlowStateEnum.valueOf(nowState)) {
		case �ҥD��:
			daoservice.update(se);
			// �q���g��
			String mailTitle = "�z�ӽЪ� " + this.getFunctionName() + " �D�ޤw�֭� ( �渹�G" + se.getPno() + ") ";
			MailToolInApprove.sendNotifyToApplicant(new BaseService(this), se.getApplicant(), se, mailTitle);
			break;
		case �ժ�:
			SyncDataService.subFlowSync(t, this);

			SubFlowBuilder sfbCheck = new CheckFlowBuilder();
			SubFlowBuilder sfbTp = new TpFlowBuilder();
			SubFlowBuilder sfbTest = new TestFlowBuilder();
			if (!sfbCheck.isReady(this) || !sfbTp.isReady(this) || !sfbTest.isReady(this)) {
				message("�l�y�{����ñ�֤H��줣�i�ť�");
				ret = false;
			}
			if (getValue("QR_NO") == null || "".equals(getValue("QR_NO").trim())) {
				message("QR���X���i�ť�");
				ret = false;
			}
			break;
		case ���z���D�ޤ���:

			if (ret && !designee.equals("")) {
				daoservice.update(se);
			} else {
				message("���D�ޫ����H��(�ժ�)���i�ť�");
				ret = false;
			}
			break;
		case ���ʸg��T�{:
		case �ݳB�z:

			if (ret) {
				daoservice.update(se);
			}
			break;
		case ���ʸg��:

			if ("0".equals(this.isCheckValue) && "0".equals(this.isTrialProdValue) && "0".equals(this.isTestValue)) {
				ret = true;

			} else if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("�������G�P���ɤ��i�ť�");
				ret = false;
			} else if (ret) {

				SyncDataService.subFlowSync(t, this);
				ret = true;
			}
			break;

		default:
			break;
		}
		return ret;
	}

	private void fileItemSetChecked() {

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

	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "�T�w�e�X?", "���ɴ���", 0);
		if (result == 1) {
			message("�����e�X!");
			return false;
		}
		StringBuilder space = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			space.append("&emsp;");
		}
		percent(100, space.toString() + "�B�z��<font color=white>");
		message("�w�e�X");
		return true;
	}

}
