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
		setAllFieldUneditable();
		setAllFileUploadFieldEditable();

		// 申請人基本資料

		String ownPno = getValue("OWN_PNO").trim();
		if (ownPno.length() <= 0) {
			changeForm(getFunctionName());
		} else {
			showRejectWarning(ownPno, "OWN_PNO");
		}
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
			case 組長:
				break;
			case 結案:
				break;
			case 請驗作業跟催:
				break;
			case 組長確認是否試製:
				setEditable("ASSESSOR", true);
				setEditable("LAB_EXE", true);
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
