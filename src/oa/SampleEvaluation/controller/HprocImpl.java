package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.DateTool;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.global.BaseDao;
import oa.global.UserData;

// TODO: Auto-generated Javadoc
/**
 * The Class HprocImpl.
 */
public abstract class HprocImpl extends hproc {

	/**
	 * Sets the all field uneditable.
	 */
	protected void setAllFieldUneditable() {
		Hashtable h = getAllcLabels();
		for (Enumeration e = h.keys(); e.hasMoreElements();) {
			String s = e.nextElement().toString();
			setEditable(s, false);

		}
	}

	/**
	 * Sets the all file upload field editable.
	 */
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

	/**
	 * Sets the form EMP base info.
	 *
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	protected void setFormEMPBaseInfo() throws SQLException, Exception {
		UserData u = new UserData(getValue("APPLICANT").trim(), getTalk());
		setValue("APPLICANT_NAME", u.getHecname());
		setValue("CPNYID", u.getCpnyid());
		setValue("APPLICANT_DEP_NAME", u.getDepName());
		u = new UserData(getValue("PROJECT_LEADER").trim(), getTalk());
		setValue("PROJECT_LEADER_NAME", u.getHecname() + " " + u.getDepName());
	}

	/**
	 * Show reject warning.
	 *
	 * @param pno [String]
	 * @throws Exception the exception
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

	/**
	 * Show reject warning.
	 *
	 * @param pno         [String]
	 * @param pnoFileName [String]
	 * @throws Exception the exception
	 */
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

	/**
	 * Gets the AllFieldNameString.
	 *
	 * @return [String]
	 */
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

	/**
	 * Gets the AllFieldNameArray.
	 *
	 * @return [String[]]
	 */
	protected String[] getAllFieldNameArray() {
		String str = getAllFieldNameString();

		return str.split(",");

	}

	/**
	 * 取得UI元素的標題.
	 *
	 * @param fieldName [String]
	 * @return [String]
	 */
	protected String getFieldCaption(String fieldName) {
		Hashtable ht = (Hashtable) getAllcLabels().get(fieldName);
		return (String) ht.get("caption");
	}

	/**
	 * Gets the DeadLine.
	 *
	 * @param appDate [String]
	 * @param urgency [String]
	 * @return [String]
	 * @throws Exception the exception
	 */
	public String getDeadLine(String appDate, String urgency) throws Exception {

		int addDaysNum = 0;
		String value = urgency;
		if (!value.isEmpty()) {
			if (value.equals("A")) {
				addDaysNum = 100;
			} else if (value.equals("B")) {
				addDaysNum = 110;
			} else if (value.equals("C")) {
				addDaysNum = 130;
			}

			// setValue("DL", DateTool.getAfterWorkDate(getValue("APP_DATE"), addDaysNum,
			// getTalk()));
		}
		return DateTool.getAfterWorkDate(appDate, addDaysNum, getTalk());
	}

	/**
	 * Not empty.
	 *
	 * @param s [String]
	 * @return true, if successful
	 */
	public boolean notEmpty(String s) {
		return (s != null && s.length() > 0);
	}

	/**
	 * File item set checker.
	 */
	public void fileItemSetChecker() {

		if (!getValue("FILE_SPEC").equals("")) {
			setValue("PROVIDE_SPEC", "1");
		}
		if (!getValue("FILE_COA").equals("")) {
			setValue("PROVIDE_COA", "1");
		}
		if (!getValue("FILE_SDS").equals("")) {
			setValue("PROVIDE_SDS", "1");
		}
		if (!getValue("FILE_OTHERS").equals("")) {
			setValue("PROVIDE_OTHERS", "1");
		}
		if (!getValue("FILE_TEST_METHOD").equals("")) {
			setValue("PROVIDE_TEST_METHOD", "1");
		}
	}

	public void setTextAndCheckIsSubFlowRunning() {
		// 根據勾選的子流程顯示其是否已進行過
		if ("1".equals(getValue("IS_CHECK").trim())) {
			setVisible("SUB_FLOW_TAB_CHECK", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationCheckService(getTalk()), getValue("PNO") + "CHECK");

		}
		if ("1".equals(getValue("IS_TRIAL_PRODUCTION").trim())) {
			setVisible("SUB_FLOW_TAB_TP", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationTpService(getTalk()), getValue("PNO") + "TP");
		}
		if ("1".equals(getValue("IS_TEST").trim())) {
			setVisible("SUB_FLOW_TAB_TEST", true);
			setTextAndCheckIsSubFlowRunning(new SampleEvaluationTestService(getTalk()), getValue("PNO") + "TEST");
		}
	}

	/**
	 * 判斷子流程是否已簽核中,<br>
	 * 如已執行子流程則關閉執行流程的按鈕與勾選格.
	 *
	 * @param dao [BaseDao]
	 * @param pno [String]
	 */
	protected void setTextAndCheckIsSubFlowRunning(BaseDao dao, String pno) {

		try {
			if (dao.findById(pno) != null) {
				if (pno.contains("CHECK")) {
					setValue("START_CHECK_FLOW_TEXT", "已進行!");
					setEditable("START_CHECK_FLOW", false);
					setEditable("IS_CHECK", false);
				}
				if (pno.contains("TEST")) {
					setValue("START_TEST_FLOW_TEXT", "已進行!");
					setEditable("START_TEST_FLOW", false);
					setEditable("IS_TEST", false);
				}
				if (pno.contains("TP")) {
					setValue("START_TP_FLOW_TEXT", "已進行!");
					setEditable("START_TP_FLOW", false);
					setEditable("IS_TRIAL_PRODUCTION", false);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
