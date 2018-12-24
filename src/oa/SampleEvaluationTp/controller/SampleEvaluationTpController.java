package oa.SampleEvaluationTp.controller;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationTp.flow.approve.gateEnum.FlowState;

/**
 *
 * @author u52116
 *
 */
public class SampleEvaluationTpController extends HprocImpl {

	public boolean confirm;

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
		// �N��Odefault �W�٤]�n�s�bFlowState
		// switch����~�|�ͮ�
		switch (FlowState.valueOf(getState())) {
		case ����Ǹg��:
			setEditable("EVALUATION_RESULT", true);
			break;
		case �ժ�:
			break;
		case �ժ��T�{�O�_����:
			setEditable("DOC_CTRLER", true);
			setEditable("LAB_EXE", true);
			break;
		case ����:
			break;
		case �����H��:
			break;
		case �ջs�@�~��Z:
			break;
		default:
			break;
		}
		return arg0;

	}

}
