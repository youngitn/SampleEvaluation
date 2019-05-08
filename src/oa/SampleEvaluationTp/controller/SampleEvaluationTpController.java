package oa.SampleEvaluationTp.controller;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.SampleEvaluationTp.flow.approve.gateEnum.FlowStateEnum;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;

/**
 * The Class SampleEvaluationTpController.
 *
 * @author u52116
 */
public class SampleEvaluationTpController extends HprocImpl {

	/** The confirm. */
	public boolean confirm;

	/* (non-Javadoc)
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	@Override
	public String action(String arg0) throws Throwable {
		// message(getState().trim());
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
		// �N��Odefault �W�٤]�n�s�bFlowState
		// switch����~�|�ͮ�
		switch (FlowStateEnum.valueOf(getState())) {
		case ��ޤH��:
			setEditable("NOTIFY_NO_CHECK", true);
			setEditable("NOTIFY_NO_TRIAL_PROD", true);
			setEditable("EVALUATION_RESULT", true);
			break;
		default:
			break;
		}
		// �ھڤĿ諸�l�y�{��ܨ�O�_�w�i��L
		setTextAndCheckIsSubFlowRunning();
		return arg0;

	}

}
