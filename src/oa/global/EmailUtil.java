package oa.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import jcx.util.convert;

/**
 * The Class EmailUtil.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class EmailUtil {

	/** The service. */
	BaseService service;

	/**
	 * Instantiates a new email util.
	 *
	 * @param service [BaseService]
	 */
	public EmailUtil(BaseService service) {
		this.service = service;
	}

	/**
	 * Gets the AllSignedPeopleEmailForLastGateToSend.
	 *
	 * @return [ArrayList<String>]
	 */
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

	/**
	 * Gets the HisOpinion.
	 *
	 * @return [String]
	 */
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

	/**
	 * Gets the DepName.
	 *
	 * @param depNo [String]
	 * @return [String]
	 * @throws Exception the exception
	 */
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
