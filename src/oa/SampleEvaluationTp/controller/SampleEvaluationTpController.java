package oa.SampleEvaluationTp.controller;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
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
		// 根據勾選的子流程顯示其是否已進行過
		if ("1".equals(getValue("IS_CHECK").trim())) {
			setVisible("SUB_FLOW_TAB_CHECK", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationCheckService(getTalk()), getValue("PNO") + "CHECK");

		}
		if ("1".equals(getValue("IS_TRIAL_PRODUCTION").trim())) {
			setVisible("SUB_FLOW_TAB_TP", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationTpService(getTalk()), getValue("PNO") + "TP");
		}
		if ("1".equals(getValue("IS_TEST").trim())) {
			setVisible("SUB_FLOW_TAB_TEST", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationTestService(getTalk()), getValue("PNO") + "TEST");
		}
		return arg0;

	}

}
