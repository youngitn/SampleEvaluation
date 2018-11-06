package oa.SampleEvaluationTp.rule;

import jcx.jform.bRule;

import java.util.*;

public class Rule extends bRule {
	public Vector getIDs(String value) throws Throwable {
		// 回傳值為 Vector contails 符合這條規格的帳號
		// value 為 "不動產、廠房及設備異動申請單_取得"
		String state = getState();

		Vector id = new Vector();

		// 受理單位主管所分派之人員
		if (state.equals("填寫試製單號")) {

			id.addElement("admin");
			String[] u = getData("DOC_CTRLER").trim().split(" ");
			id.addElement(u[0]);

			return id;
		}
		if (state.equals("實驗室經辦")) {

			id.addElement("admin");
			String[] u = getData("LAB_EXE").trim().split(" ");
			id.addElement(u[0]);
			return id;
		}

		if (state.equals("試製作業跟催")) {

			String[] ret = getData("DESIGNEE").trim().split(" ");

			id.addElement("admin");
			id.addElement(ret[0]);

			return id;
		}

		if (state.equals("組長")) {

			String[] ret = getData("DESIGNEE").trim().split(" ");

			id.addElement("admin");
			id.addElement(ret[0]);

			return id;
		}

		return id;
	}

	public String getInformation() {
		return "---------------\u8b93\u552e\u901a\u77e5\u55ae.Rule()----------------";
	}
}
