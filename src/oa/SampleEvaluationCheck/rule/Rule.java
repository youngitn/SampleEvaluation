package oa.SampleEvaluationCheck.rule;

import jcx.jform.bRule;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;

import java.util.*;

public class Rule extends bRule {
	public Vector<String> getIDs(String value) throws Throwable {
		// 回傳值為 Vector contails 符合這條規格的帳號
		// value 為 "不動產、廠房及設備異動申請單_取得"
		String state = getState();

		Vector<String> id = new Vector<String>();
		String[] u = null;
		// 受理單位主管所分派之人員
		switch (FlowState.valueOf(state)) {
		case 填寫請驗單號:
			id.addElement("admin");
			u = getData("DOC_CTRLER").trim().split(" ");
			id.addElement(u[0]);
			break;
		case 實驗室經辦:
			id.addElement("admin");
			u = getData("LAB_EXE").trim().split(" ");
			id.addElement(u[0]);

			break;
		case 請驗作業跟催:
			u = getData("DESIGNEE").trim().split(" ");
			id.addElement("admin");
			id.addElement(u[0]);
			break;
		case 組長:
			u = getData("DESIGNEE").trim().split(" ");

			id.addElement("admin");
			id.addElement(u[0]);
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
