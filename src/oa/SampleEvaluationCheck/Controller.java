package oa.SampleEvaluationCheck;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.UserData;

/**
 * 嘗試可測試寫法
 * 
 * @author u52116
 *
 */
public class Controller extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;

	@Override
	public String action(String arg0) throws Throwable {
		//主管分案指派人姓名 單位顯示
		UserData u = new UserData(getValue("PROJECT_LEADER").trim(),getTalk());	
		setValue("PROJECT_LEADER_NAME",u.getHecname()+" "+u.getDep_name());
		
		//申請人基本資料
		u = new UserData(getValue("APPLICANT").trim(),getTalk());
		setValue("APPLICANT_NAME",u.getHecname());
		setValue("CPNYID",u.getCpnyid());
		setValue("APPLICANT_DEP_NAME",u.getDep_name());
		
		if (getState().trim().equals("填寫請驗單號")) {
			setEditable("NOTIFY_NO_CHECK", true);
			
		}
		String ownPno = getValue("OWN_PNO").trim();
		if (ownPno.length() <= 0) {
			changeForm(getFunctionName());
		} else {
			String sql = "select f_inp_info from " + getTableName() + "_flowc where OWN_PNO = '" + ownPno + "'";
			String[][] ret = getTalk().queryFromPool(sql);
			if (ret.length > 0) {
				String memo = ret[0][0];
				if (memo.startsWith("[退簽]")) {
					addScript("callRejectWarning();");
				}
			}
		}
		return arg0;

	}

}
