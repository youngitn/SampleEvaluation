package oa.SampleEvaluationTp.controller;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import oa.SampleEvaluation.common.SampleEvaluationDataObj;
import oa.SampleEvaluation.common.global.UserData;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationTp.flow.approve.gateEnum.FlowState;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class SampleEvaluationTpController extends HprocImpl {

	public boolean confirm;
	public SampleEvaluationDataObj cdo;

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
		// �N��Odefault �W�٤]�n�s�bFlowState
		// switch����~�|�ͮ�
		switch (FlowState.valueOf(getState())) {

		default:
			for (int i = 1; i <= 10; i++) {
				setEditable("FILE_" + i, true);
			}
			break;
		case ����Ǹg��:
			setEditable("EVALUATION_RESULT", true);
			setEditable("FILE_EVALUATION_RESULT", true);
			break;
		}
		return arg0;

	}

}
