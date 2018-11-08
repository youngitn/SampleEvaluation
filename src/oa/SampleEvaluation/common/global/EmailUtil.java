package oa.SampleEvaluation.common.global;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;
import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import jcx.util.convert;

public class EmailUtil {

	BaseService service;

	public EmailUtil(BaseService service) {
		this.service = service;
	}

	public String[] getAllSignedPeopleEmailForLastGateToSend() throws SQLException, Exception {
		String[][] vid = service.getFlowHistory();
		String ausr[] = new String[vid.length];
		for (int i = 0; i < vid.length; i++) {
			ausr[i] = vid[i][1].trim();
		}
		HashSet set = new HashSet();
		set.addAll(Arrays.asList(ausr));
		String usr[] = (String[]) set.toArray(new String[0]);

		Vector V2 = new Vector();
		for (int i = 0; i < usr.length; i++) {

			V2.addElement(service.getEmail(usr[i]));
		}

		return (String[]) V2.toArray(new String[0]);
	}

	// 意見記錄
	public String getHisOpinion() {
		String[][] his = service.getFlowHistory();
		String value = "";
		for (int i = 1; i < his.length; i++) {
			if (!"AUTO".equals(his[i][3])) {
				value += service.getName(his[i][1]) + "(" + convert.FormatedDate(his[i][2].substring(0, 9), "/") + ":"
						+ his[i][3] + ");" + Mail.HTML_LINE_BREAK;
			} else {
			}
		}
		return value;
	}

	// 用單位代碼抓→單位名稱
	public String getDepName(String dep_no) throws SQLException, Exception {
		String sql = "select DEP_NAME from DEP_ACTIVE_VIEW where DEP_NO = '" + dep_no + "'";
		String rec[][] = service.getTalk().queryFromPool(sql);
		if (rec.length > 0) {
			return rec[0][0];
		} else {
			return dep_no;
		}
	}
}
