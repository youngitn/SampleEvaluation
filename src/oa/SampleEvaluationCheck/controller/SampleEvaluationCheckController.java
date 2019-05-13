package oa.SampleEvaluationCheck.controller;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowStateEnum;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;

/**
 * The Class SampleEvaluationCheckController.
 *
 * @author u52116
 */
public class SampleEvaluationCheckController extends HprocImpl {

	/* (non-Javadoc)
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
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

		System.out.println("getState----->" + getState());
		switch (FlowStateEnum.valueOf(getState().trim())) {
		case ��ޤH��:
			setEditable("NOTIFY_NO_CHECK", true);
			setEditable("NOTIFY_NO_TRIAL_PROD", true);
			setEditable("CHECK_DATE", true);
			break;
		case �~�O�Ҫ�:
			setEditable("NOTIFY_NO_TRIAL_PROD", true);
			
			break;
		default:
			break;

		}
		// �ھڤĿ諸�l�y�{��ܨ�O�_�w�i��L
		setTextAndCheckIsSubFlowRunning();
		return arg0;

	}

}
