package oa.SampleEvaluationTp.controller;

import oa.SampleEvaluation.common.DateTool;
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
		// 申請人基本資料
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
		// 就算是default 名稱也要存在FlowState
		// switch條件才會生效
		switch (FlowState.valueOf(getState())) {
		case 文管人員:
			setEditable("EVALUATION_RESULT", true);
			break;
		default:
			break;
		}
		return arg0;

	}

}
