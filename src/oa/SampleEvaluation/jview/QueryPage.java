package oa.SampleEvaluation.jview;

import java.sql.SQLException;

import jcx.jform.hproc;
import jcx.util.datetime;
import oa.SampleEvaluation.common.UIHidderString;
import oa.SampleEvaluation.common.UserData;

public class QueryPage extends hproc implements Page {

	@Override
	public String action(String arg0) throws Throwable {
		
		// TODO Auto-generated method stub
		setNewView("QueryPage");
		addScript(UIHidderString.hideDmakerAddButton());
		setBaseInfo();
		return null;
	}

	@Override
	public void setBaseInfo() throws SQLException, Exception {
		UserData loginUsr = new UserData(getUser(), getTalk());
		// TODO Auto-generated method stub
		// userdata = getNowApplicant();
		// 取得user資料類別
		// 填入query畫面基本欄位資料
		setValue("QUERY_EMP_ID", loginUsr.getEmpid());
		setValue("QUERY_EMP_NAME", loginUsr.getHecname());
		setValue("QUERY_EMP_DEP", loginUsr.getDepName());
		String today = getToday("YYYYmmdd");
		String edate = today;
		String sdate = datetime.dateAdd(edate, "d", -14);
		setValue("QUERY_REQ_SDATE", sdate);
		setValue("QUERY_REQ_EDATE", edate);
		loginUsr = null;

	}

	@Override
	public void pageInit() throws SQLException, Exception {
		// TODO Auto-generated method stub

	}

}
