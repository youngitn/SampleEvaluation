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
		// 就算是default 名稱也要存在FlowState
		// switch條件才會生效
		switch (FlowStateEnum.valueOf(getState())) {
		case 文管人員:
			setEditable("NOTIFY_NO_CHECK", true);
			setEditable("NOTIFY_NO_TRIAL_PROD", true);
			setEditable("EVALUATION_RESULT", true);
			break;
		default:
			break;
		}
		// 根據勾選的子流程顯示其是否已進行過
		setTextAndCheckIsSubFlowRunning();
		return arg0;

	}

}
