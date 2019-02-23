package oa.SampleEvaluationCheck.controller;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;

/**
 * 
 * 
 * @author u52116
 *
 */
public class SampleEvaluationCheckController extends HprocImpl {

	@Override
	public String action(String arg0) throws Throwable {
		// �ӽФH�򥻸��
		setFormEMPBaseInfo();

		String ownPno = getValue("OWN_PNO").trim();
		if (ownPno.length() <= 0) {
			changeForm(getFunctionName());
		} else {
			showRejectWarning(ownPno, "OWN_PNO");
		}
		setAllFieldUneditable();
		setAllFileUploadFieldEditable();
		setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));
		showSubFlowSignPeopleTab();

		System.out.println("getState----->" + getState());
		switch (FlowState.valueOf(getState().trim())) {
		case ��ޤH��:
			setEditable("NOTIFY_NO_CHECK", true);
			setEditable("NOTIFY_NO_TRIAL_PROD", true);
			break;
		default:
			break;

		}
		// �ھڤĿ諸�l�y�{��ܨ�O�_�w�i��L
		if ("1".equals(getValue("IS_CHECK").trim())) {
			setVisible("SUB_FLOW_TAB_CHECK", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationCheckService(getTalk()), getValue("PNO") + "CHECK");

		}
		if ("1".equals(getValue("IS_TRIAL_PRODUCTION").trim())) {
			setVisible("SUB_FLOW_TAB_TP", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationTpService(getTalk()), getValue("PNO") + "TP");
		}
		if ("1".equals(getValue("IS_TEST").trim())) {
			setVisible("SUB_FLOW_TAB_TEST", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationTestService(getTalk()), getValue("PNO") + "TEST");
		}
		return arg0;

	}

}
