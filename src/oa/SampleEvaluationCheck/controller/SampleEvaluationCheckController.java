package oa.SampleEvaluationCheck.controller;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.SampleEvaluationDataObj;
import oa.SampleEvaluation.common.global.UserData;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class SampleEvaluationCheckController extends hproc {

	public boolean confirm;
	public SampleEvaluationDataObj cdo;

	@Override
	public String action(String arg0) throws Throwable {
		// message(getState().trim());
		setVisible("ASSESSOR", true);
		// ���d�P�_�P�B�z
		if (getState().trim().equals("��g����渹")) {
			setEditable("NOTIFYNO_CHECK", true);
			setEditable("NOTIFY_NO_TRIAL_PROD", true);

		}

		// �ӽФH�򥻸��
		UserData u = new UserData(getValue("APPLICANT").trim(), getTalk());
		setValue("APPLICANT_NAME", u.getHecname());
		setValue("CPNYID", u.getCpnyid());
		setValue("APPLICANT_DEP_NAME", u.getDepName());
		u = new UserData(getValue("PROJECT_LEADER").trim(), getTalk());
		setValue("PROJECT_LEADER_NAME", u.getHecname() + " " + u.getDepName());
		// message(u.getDepName());
		String ownPno = getValue("OWN_PNO").trim();
		if (ownPno.length() <= 0) {
			changeForm(getFunctionName());
		} else {
			String sql = "select f_inp_info from " + getTableName() + "_flowc where OWN_PNO = '" + ownPno + "'";
			String[][] ret = getTalk().queryFromPool(sql);
			if (ret.length > 0) {
				String memo = ret[0][0];
				if (memo.startsWith("[�hñ]")) {
					addScript("callRejectWarning();");
				}
			}
		}

		return arg0;

	}

}
