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
		// 就算是default 名稱也要存在FlowState
		// switch條件才會生效
		switch (FlowState.valueOf(getState())) {
		case 實驗室經辦:
			setEditable("EVALUATION_RESULT", true);
			break;
		case 組長:
			break;
		case 組長確認是否請驗:
			setEditable("DOC_CTRLER", true);
			setEditable("LAB_EXE", true);
			break;
		case 結案:
			break;
		case 評估人員:
			break;
		case 試製作業跟崔:
			break;
		default:
			break;
		}
		return arg0;

	}

}
