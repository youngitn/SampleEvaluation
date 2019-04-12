package oa.SampleEvaluationCheck.rule;

import jcx.jform.bRule;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowStateEnum;

import java.util.*;

/**
 * The Class Rule.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class Rule extends bRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see jcx.jform.bRule#getIDs(java.lang.String)
	 */
	public Vector<String> getIDs(String value) throws Throwable {
		// 回傳值為 Vector contails 符合這條規格的帳號
		// value 為 "不動產、廠房及設備異動申請單_取得"
		String state = getState();

		Vector<String> id = new Vector<String>();
		String[] u = null;
		// 受理單位主管所分派之人員
		switch (FlowStateEnum.valueOf(state)) {
		case 文管人員:
			id.addElement("admin");
			u = getData("DOC_CTRLER_CHECK").trim().split(" ");
			id.addElement(u[0]);
			break;
		case 品保課長:
			if (!"".equals(getData("QC_BOSS").trim())) {
				u = getData("QC_BOSS").trim().split(" ");

				id.addElement("admin");
				id.addElement(u[0]);
			}
			break;
		default:
			break;
		}

		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jcx.jform.bBase#getInformation()
	 */
	public String getInformation() {
		return "---------------\u8b93\u552e\u901a\u77e5\u55ae.Rule()----------------";
	}
}
