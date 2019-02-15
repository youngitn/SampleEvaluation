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

		setFormEMPBaseInfo();

		// 申請人基本資料

		String ownPno = getValue("OWN_PNO").trim();
		if (ownPno.length() <= 0) {
			changeForm(getFunctionName());
		} else {
			showRejectWarning(ownPno, "OWN_PNO");
		}
		setAllFieldUneditable();
		setAllFileUploadFieldEditable();
		setValue("DL", getDeadLine(getValue("APP_DATE"),getValue("URGENCY")));
		showSubFlowSignPeopleTab();
		
		System.out.println("getState----->" + getState());
		switch (FlowState.valueOf(getState().trim())) {
		case 文管人員:
			setEditable("NOTIFY_NO_CHECK", true);
			setEditable("NOTIFY_NO_TRIAL_PROD", true);
			break;
		default:
			break;

		}

		return arg0;

	}

}
