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
		setTextAndCheckIsSubFlowRunning();
		return arg0;

	}

}
