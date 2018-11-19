package oa.SampleEvaluation.common.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import jcx.util.convert;

public class EmailUtil {

	BaseService service;

	public EmailUtil(BaseService service) {
		this.service = service;
	}

	public ArrayList<String> getAllSignedPeopleEmailForLastGateToSend() {
		String[][] vid = service.getFlowHistory();
		String[] ausr = new String[vid.length];
		for (int i = 0; i < vid.length; i++) {
			ausr[i] = vid[i][1].trim();
		}
		HashSet<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(ausr));
		String[] usr = set.toArray(new String[0]);

		ArrayList<String> v = new ArrayList<String>();
		for (int i = 0; i < usr.length; i++) {

			v.add(service.getEmail(usr[i]));
		}

		return v;
	}

	// 意見記錄
	public String getHisOpinion() {
		String[][] his = service.getFlowHistory();
		StringBuilder value = new StringBuilder();
		for (int i = 1; i < his.length; i++) {
			if (!"AUTO".equals(his[i][3])) {
				value.append(service.getName(his[i][1]) + "(" + convert.FormatedDate(his[i][2].substring(0, 9), "/")
						+ ":" + his[i][3] + ");" + Mail.HTML_LINE_BREAK);
			}
		}
		return value.toString();
	}

	// 用單位代碼抓→單位名稱
	public String getDepName(String depNo) throws Exception {
		String sql = "select DEP_NAME from DEP_ACTIVE_VIEW where DEP_NO = '" + depNo + "'";
		String[][] rec = service.getTalk().queryFromPool(sql);
		if (rec.length > 0) {
			return rec[0][0];
		} else {
			return depNo;
		}
	}
}
