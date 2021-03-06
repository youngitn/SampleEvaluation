package oa.SampleEvaluationTp.rule;

import jcx.jform.bRule;
import oa.SampleEvaluationTp.flow.approve.gateEnum.FlowState;

import java.util.*;

public class Rule extends bRule {
	public Vector<String> getIDs(String value) throws Throwable {
		// 回傳值為 Vector contails 符合這條規格的帳號
		// value 為 "不動產、廠房及設備異動申請單_取得"
		String state = getState();
		Vector<String> id = new Vector<String>();
		String[] ret = null;
		switch (FlowState.valueOf(state)) {

		case 文管人員:
			ret = getData("DOC_CTRLER_TP").trim().split(" ");
			id.addElement("admin");
			id.addElement(ret[0]);

			break;
		case 檢驗人員:
			id.addElement("admin");
			ret = getData("LAB_EXE").trim().split(" ");
			id.addElement(ret[0]);
			break;
		case 試製人員:
			id.addElement("admin");
			ret = getData("ASSESSOR").trim().split(" ");
			id.addElement(ret[0]);
			break;
		default:
			break;
		}

		return id;
	}

	public String getInformation() {
		return "---------------\u8b93\u552e\u901a\u77e5\u55ae.Rule()----------------";
	}
}
