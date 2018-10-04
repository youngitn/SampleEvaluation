package oa.SampleEvaluationCheck;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.UserData;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class SampleEvaluationCheckController extends hproc {

	public boolean confirm;
	public CommonDataObj cdo;

	@Override
	public String action(String arg0) throws Throwable {
		// message(getState().trim());
		setVisible("ASSESSOR", true);
		// ���d�P�_�P�B�z
		if (getState().trim().equals("��g����渹")) {
			setEditable("NOTIFYNO_CHECK", true);

		}

		// �ӽФH�򥻸��
		UserData u = new UserData(getValue("APPLICANT").trim(), getTalk());
		setValue("APPLICANTNAME", u.getHecname());
		setValue("CPNYID", u.getCpnyid());
		setValue("APPLICANT_DEPNAME", u.getDepName());
		u = new UserData(getValue("PROJECT_LEADER").trim(), getTalk());
		setValue("PROJECT_LEADERNAME", u.getHecname() + " " + u.getDepName());
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
