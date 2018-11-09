package oa.SampleEvaluationTp.controller;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import oa.SampleEvaluation.common.SampleEvaluationDataObj;
import oa.SampleEvaluation.common.global.UserData;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluationTp.flow.approve.gateEnum.FlowState;

/**
 * 嘗試可測試寫法
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
		// 申請人基本資料
		setFormEMPBaseInfo();
		String ownPno = getValue("OWN_PNO").trim();
		if (ownPno.length() <= 0) {
			changeForm(getFunctionName());
		} else {

			showRejectWarning(ownPno, "OWN_PNO");
		}
		// 就算是default 名稱也要存在FlowState
		// switch條件才會生效
		switch (FlowState.valueOf(getState())) {

		default:
			for (int i = 1; i <= 10; i++) {
				setEditable("FILE_" + i, true);
			}
			break;
		case 實驗室經辦:
			setEditable("EVALUATION_RESULT", true);
			setEditable("FILE_EVALUATION_RESULT", true);
			break;
		}
		return arg0;

	}

}
