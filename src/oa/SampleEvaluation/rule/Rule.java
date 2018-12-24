package oa.SampleEvaluation.rule;

import java.util.Vector;

import jcx.db.talk;
import jcx.jform.bRule;

public class Rule extends bRule {
	public Vector<String> getIDs(String value) throws Throwable {
		// 回傳值為 Vector contails 符合這條規格的帳號
		// value 為 "不動產、廠房及設備異動申請單_取得"
		String state = getState();
		String depNo = getData("RECEIPT_UNIT").trim(); // 受理單位號碼

		Vector<String> id = new Vector<String>();
		talk t = getTalk();
		// 無enum 純字串判斷
		if (state.equals("受理單位主管分案")) {

			String ret[][] = t.queryFromPool(
					" select DEP_CHIEF from DEP_ACTIVE_VIEW where DEP_NO = '" + depNo + "' and CPNYID = 'YT01' ");
			if (ret.length > 0) {
				id.addElement(ret[0][0]);

				id.addElement("admin");
			}

			return id;
		}
		// 受理單位主管所分派之人員
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
