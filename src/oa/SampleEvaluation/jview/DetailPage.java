package oa.SampleEvaluation.jview;//oa/SampleEvaluation/jview/DetailPage

import java.sql.SQLException;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.UserData;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;

public class DetailPage extends hproc implements Page{

	public void setBaseInfo() throws SQLException, Exception {
		UserData appUsr = new UserData(getValue("APPLICANT"), getTalk());
		setValue("CPNYID", appUsr.getCpnyid());
		setValue("APPLICANT_NAME", appUsr.getHecname());
		setValue("APPLICANT_DEP_NAME", appUsr.getDepName());
		appUsr = null;
		// 專案主持人資料
		String proLeadEmpid = getValue("PROJECT_LEADER").trim();
		if (proLeadEmpid.equals("")) {
			setValue("PROJECT_LEADER_NAME", "");
		} else {
			UserData proLeadUsr = new UserData(proLeadEmpid, getTalk());
			setValue("PROJECT_LEADER_NAME", proLeadUsr.getHecname() + " " + proLeadUsr.getDepName());
		}
	}

	// 將資料填入表單畫面欄位
	private void fillData(String[][] data, String[][] allColumns) {
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], data[0][i]);
			}
		}
	}

	@Override
	public String action(String arg0) throws Throwable {
		CommonDataObj inercdo = new CommonDataObj(getTalk(), getTableName(), "PNO", "APPLICANT");
		String[][] data = new SampleEvaluationDaoImpl(getTalk()).findArrayById(getValue("QUERY_LIST_PNO"));
		fillData(data, inercdo.getTableAllColumn());
		setBaseInfo();

		return arg0;
	}

	@Override
	public void pageInit() throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

}
