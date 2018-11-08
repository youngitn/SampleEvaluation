package oa.SampleEvaluationTp.controller;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.SampleEvaluationDataObj;
import oa.SampleEvaluation.common.global.UserData;
import oa.SampleEvaluationTp.flow.approve.gateEnum.FlowState;

/**
 * 嘗試可測試寫法
 * 
 * @author u52116
 *
 */
public class SampleEvaluationTpController extends hproc {

	public boolean confirm;
	public SampleEvaluationDataObj cdo;

	@Override
	public String action(String arg0) throws Throwable {
		// message(getState().trim());
		// 申請人基本資料
		UserData u = new UserData(getValue("APPLICANT").trim(), getTalk());
		setValue("APPLICANT_NAME", u.getHecname());
		setValue("CPNYID", u.getCpnyid());
		setValue("APPLICANT_DEP_NAME", u.getDepName());
		String pl = getValue("PROJECT_LEADER").trim();
		if (!pl.equals("")) {
			u = new UserData(getValue("PROJECT_LEADER").trim(), getTalk());
			setValue("PROJECT_LEADER_NAME", u.getHecname() + " " + u.getDepName());
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
		// 就算是default 名稱也要存在FlowState
		// switch條件才會生效
		switch (FlowState.valueOf(getState())) {

		default:
			for (int i = 1; i <= 10; i++) {
				setEditable("FILE_" + i, true);
			}
			break;
		}
		return arg0;

	}

}
