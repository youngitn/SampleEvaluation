package oa.SampleEvaluationCheck.controller;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class SampleEvaluationCheckController extends HprocImpl {

	@Override
	public String action(String arg0) throws Throwable {
		setFormEMPBaseInfo();
		for (int i = 1; i <= 10; i++) {
			setEditable("FILE_" + i, true);
		}
		// �ӽФH�򥻸��

		String ownPno = getValue("OWN_PNO").trim();
		if (ownPno.length() <= 0) {
			changeForm(getFunctionName());
		} else {
			showRejectWarning(ownPno, "OWN_PNO");
		}
		setVisible("ASSESSOR", true);
		try {
			switch (FlowState.valueOf(getState().trim())) {
			case ��g����渹:
				setEditable("NOTIFY_NO_CHECK", true);
				setEditable("NOTIFY_NO_TRIAL_PROD", true);
				break;
			case ����Ǹg��:
				setEditable("EVALUATION_RESULT", true);
				setEditable("FILE_EVALUATION_RESULT", true);
				break;
			default:
				break;
			}
		} catch (EnumConstantNotPresentException e) {
			message("enum:PageInitType.clss �o�͵L�k���Ѫ��N�~");
		}
		return arg0;

	}

}
