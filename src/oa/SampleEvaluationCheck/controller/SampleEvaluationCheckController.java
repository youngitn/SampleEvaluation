package oa.SampleEvaluationCheck.controller;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;

/**
 * 嘗試可測試寫法
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
		// 申請人基本資料

		String ownPno = getValue("OWN_PNO").trim();
		if (ownPno.length() <= 0) {
			changeForm(getFunctionName());
		} else {
			showRejectWarning(ownPno, "OWN_PNO");
		}
		setVisible("ASSESSOR", true);
		try {
			switch (FlowState.valueOf(getState().trim())) {
			case 填寫請驗單號:
				setEditable("NOTIFY_NO_CHECK", true);
				setEditable("NOTIFY_NO_TRIAL_PROD", true);
				break;
			case 實驗室經辦:
				setEditable("EVALUATION_RESULT", true);
				setEditable("FILE_EVALUATION_RESULT", true);
				break;
			default:
				break;
			}
		} catch (EnumConstantNotPresentException e) {
			message("enum:PageInitType.clss 發生無法辨識的意外");
		}
		return arg0;

	}

}
