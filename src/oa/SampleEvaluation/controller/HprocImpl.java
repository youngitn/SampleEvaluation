package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.global.UserData;

public class HprocImpl extends hproc {

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	protected void setAllFieldUneditable() {
		Hashtable h = getAllcLabels();
		for (Enumeration e = h.keys(); e.hasMoreElements();) {
			String s = e.nextElement().toString();
			setEditable(s, false);

		}
	}

	protected void setAllFileUploadFieldEditable() {
		Hashtable h = getAllcLabels();
		for (Enumeration e = h.keys(); e.hasMoreElements();) {
			String s = e.nextElement().toString();
			if (s.startsWith("FILE_")) {
				setEditable(s, true);
			}

		}
	}

	protected void setFormEMPBaseInfo() throws SQLException, Exception {
		UserData u = new UserData(getValue("APPLICANT").trim(), getTalk());
		setValue("APPLICANT_NAME", u.getHecname());
		setValue("CPNYID", u.getCpnyid());
		setValue("APPLICANT_DEP_NAME", u.getDepName());
		u = new UserData(getValue("PROJECT_LEADER").trim(), getTalk());
		setValue("PROJECT_LEADER_NAME", u.getHecname() + " " + u.getDepName());
	}

	/**
	 * @param pno
	 * @throws SQLException
	 * @throws Exception
	 */
	protected void showRejectWarning(String pno) throws Exception {
		String sql = "select f_inp_info from " + getTableName() + "_flowc where PNO = '" + pno + "'";
		String[][] ret = getTalk().queryFromPool(sql);
		if (ret.length > 0) {
			String memo = ret[0][0];
			if (memo.startsWith("[退簽]")) {
				addScript("callRejectWarning();");
			}
		}
	}

	protected void showRejectWarning(String pno, String pnoFileName) throws Exception {
		String sql = "select f_inp_info from " + getTableName() + "_flowc where " + pnoFileName + " = '" + pno + "'";
		String[][] ret = getTalk().queryFromPool(sql);
		if (ret.length > 0) {
			String memo = ret[0][0];
			if (memo.startsWith("[退簽]")) {
				addScript("callRejectWarning();");
			}
		}
	}

	protected String getAllFieldNameString() {
		String str = "";
		String strrr = "";
		Enumeration k = getAllcLabels().keys();
		while (k.hasMoreElements()) {
			str = (String) k.nextElement();
			Hashtable ht = (Hashtable) getAllcLabels().get(str);
			if (ht.get("type").equals("field")) {
				strrr += str.trim() + ",";
			}

		}

		return StringUtils.removeEnd(strrr, ",");

	}

	protected String[] getAllFieldNameArray() {
		String str = getAllFieldNameString();

		return str.split(",");

	}

	/**
	 * 取得欄位標題
	 * 
	 * @param fieldName
	 */
	protected String getFieldCaption(String fieldName) {
		Hashtable ht = (Hashtable) getAllcLabels().get(fieldName);
		return (String) ht.get("caption");
	}
}
