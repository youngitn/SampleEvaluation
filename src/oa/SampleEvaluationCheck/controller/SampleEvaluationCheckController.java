package oa.SampleEvaluationCheck.controller;

import oa.SampleEvaluation.common.DateTool;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;

/**
 * 
 * 
 * @author u52116
 *
 */
public class SampleEvaluationCheckController extends HprocImpl {

	@Override
	public String action(String arg0) throws Throwable {
		try {
			setFormEMPBaseInfo();
			setAllFieldUneditable();
			setAllFileUploadFieldEditable();

			// �ӽФH�򥻸��

			String ownPno = getValue("OWN_PNO").trim();
			if (ownPno.length() <= 0) {
				changeForm(getFunctionName());
			} else {
				showRejectWarning(ownPno, "OWN_PNO");
			}
			int addDaysNum = 0;

			if (getValue("URGENCY").equals("A")) {
				addDaysNum = 100;
			} else if (getValue("URGENCY").equals("B")) {
				addDaysNum = 110;
			} else if (getValue("URGENCY").equals("C")) {
				addDaysNum = 130;
			}
			
			setValue("DL", DateTool.getAfterWorkDate(getValue("APP_DATE"), addDaysNum, getTalk()));

			switch (FlowState.valueOf(getState().trim())) {
			case ��g����渹:
				setEditable("NOTIFY_NO_CHECK", true);
				setEditable("NOTIFY_NO_TRIAL_PROD", true);
				break;
			case ����Ǹg��:
				setEditable("EVALUATION_RESULT", true);
				setEditable("FILE_EVALUATION_RESULT", true);
				break;
			case �ժ�:
				break;
			case ����:
				break;
			case ����@�~���:
				break;
			case �ժ��T�{�O�_�ջs:
				setEditable("ASSESSOR", true);
				setEditable("LAB_EXE", true);
				
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
