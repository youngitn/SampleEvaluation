package oa.SampleEvaluationTest.controller;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.flow.approve.gateEnum.FlowStateEnum;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;

/**
 * The Class SampleEvaluationTestController.
 *
 * @author u52116
 */
public class SampleEvaluationTestController extends HprocImpl {

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
		case �t�X�H��:
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
