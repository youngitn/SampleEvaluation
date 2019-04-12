package oa.SampleEvaluation.common;

import java.sql.SQLException;

//oa/SampleEvaluation/common/Checkbox
import jcx.jform.hproc;

/**
 * The Class Checkbox.
 *
 * @author YoungCheng(u52116) 2019/2/22
 */
public class Checkbox extends hproc {

	/**
	 * The Enum SubFlowCheckboxName.
	 *
	 * @author YoungCheng(u52116) 2019/3/19
	 */
	public enum SubFlowCheckboxName {
		
		/** The is check. */
		IS_CHECK, 
 /** The is test. */
 IS_TEST, 
 /** The is trial production. */
 IS_TRIAL_PRODUCTION
	}

	/* (non-Javadoc)
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	public String action(String value) {
		try {
			switch (SubFlowCheckboxName.valueOf(getName())) {
			case IS_CHECK:
				if (value.trim().equals("1")) {
					setVisible("SUB_FLOW_TAB_CHECK", true);
					String[][] ret = getSubFlowSignPeople("DOC_CTRLER_CHECK");
					setValue("DOC_CTRLER_CHECK", ret[0][0]);
					setVisible("START_CHECK_FLOW", true);
				} else {
					setVisible("SUB_FLOW_TAB_CHECK", false);
					setValue("DOC_CTRLER_CHECK", "");
					setValue("QC_BOSS", "");
					setVisible("START_CHECK_FLOW", false);
				}
				break;
			case IS_TEST:
				if (value.trim().equals("1")) {

					setVisible("SUB_FLOW_TAB_TEST", true);
					setVisible("START_TEST_FLOW", true);
				} else {
					setVisible("SUB_FLOW_TAB_TEST", false);
					setValue("COORDINATOR", "");
					setVisible("START_TEST_FLOW", false);
				}
				break;
			case IS_TRIAL_PRODUCTION:
				if (value.trim().equals("1")) {
					String[][] ret = getSubFlowSignPeople("DOC_CTRLER_TP");
					setValue("DOC_CTRLER_TP", ret[0][0]);
					setVisible("SUB_FLOW_TAB_TP", true);
					setVisible("START_TP_FLOW", true);
				} else {
					setVisible("SUB_FLOW_TAB_TP", false);
					setValue("ASSESSOR", "");
					setValue("DOC_CTRLER_TP", "");
					setValue("LAB_EXE", "");
					setVisible("START_TP_FLOW", false);
				}
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Gets the SubFlowSignPeople.
	 *
	 * @param selectFields [String]
	 * @return [String[][]]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public String[][] getSubFlowSignPeople(String selectFields) throws SQLException, Exception {
		return getTalk().queryFromPool("SELECT " + selectFields
				+ " FROM SAMPLE_EVALUATION_SUB_FLOW_SIGN_MAP WHERE DEPNO='" + getValue("RECEIPT_UNIT") + "'");
	}

	/**
	 * Gets the Information.
	 *
	 * @return [String]
	 */
	public String getInformation() {
		return "---------------IS_CHECK(\u9032\u884c\u8acb\u9a57\u6d41\u7a0b).html_action()----------------";
	}
}
