package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.DateTool;
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

	// 設定資料上傳相關欄位為可編輯
	protected void setAllFileUploadFieldEditable() {
		Hashtable h = getAllcLabels();
		for (Enumeration e = h.keys(); e.hasMoreElements();) {
			String s = e.nextElement().toString();
			// 可編輯條件 符合設為true
			// 名稱開頭FILE_ 表示上傳欄位本體
			// 名稱開頭PROVIDE_ 表示上傳欄位的勾選項目
			if (s.startsWith("FILE_") || s.startsWith("PROVIDE_")) {
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
	 * 取得UI元素的標題
	 * 
	 * @param fieldName
	 */
	protected String getFieldCaption(String fieldName) {
		Hashtable ht = (Hashtable) getAllcLabels().get(fieldName);
		return (String) ht.get("caption");
	}

	protected void setDeadLine() throws Exception {

		int addDaysNum = 0;
		String value = getValue("URGENCY");
		if (!value.isEmpty()) {
			if (value.equals("A")) {
				addDaysNum = 100;
			} else if (value.equals("B")) {
				addDaysNum = 110;
			} else if (value.equals("C")) {
				addDaysNum = 130;
			}

			setValue("DL", DateTool.getAfterWorkDate(getValue("APP_DATE"), addDaysNum, getTalk()));
		}
	}

	protected void showSubFlowSignPeopleTab() throws Exception {
		// 根據勾選的子流程將相關資料顯示
		if ("1".equals(getValue("IS_CHECK").trim()))
			setVisible("SUB_FLOW_TAB_CHECK", true);
		if ("1".equals(getValue("IS_TRIAL_PRODUCTION").trim()))
			setVisible("SUB_FLOW_TAB_TP", true);
		if ("1".equals(getValue("IS_TEST").trim()))
			setVisible("SUB_FLOW_TAB_TEST", true);
	}
}
