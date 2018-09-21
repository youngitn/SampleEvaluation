package oa.SampleEvaluation.common;

import java.sql.SQLException;

import jcx.jform.hproc;
import jcx.util.datetime;
/**
 * 
 * @author u52116
 *
 */
public class FormInitUtil extends BaseFormOnload {

	UserData userdata;
	
	public FormInitUtil(hproc c) throws SQLException, Exception {
		super(c);
		userdata = new UserData(c.getUser(), c.getTalk());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doQueryPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		userdata = getNowApplicant();
		// 取得user資料類別
		// 填入query畫面基本欄位資料
		c.setValue("QUERY_EMP_ID",userdata.getEmpid());
		c.setValue("QUERY_EMP_NAME", userdata.getHecname());
		c.setValue("QUERY_EMP_DEP", userdata.getDep_name());	;
		String today = c.getToday("YYYYmmdd");
		String edate = today;
		String sdate = datetime.dateAdd(edate, "d", -14);
		c.setValue("QUERY_REQ_SDATE", sdate);
		c.setValue("QUERY_REQ_EDATE", edate);
		userdata = null;
		c.setNewView("QueryPage");
	}

	@Override
	public void doAddPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		userdata = getNowApplicant();
		//新增欄位申請人基本資料填入
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT", c.getUser());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDep_name());
		c.setValue("APP_DATE", c.getToday("YYYYmmdd"));
		userdata = null;
	}

	
	@Override
	/**
	 * 顯示簽核紀錄
	 */
	public void doFlowPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		// 顯示退簽通知
		setExistBillOtherData();
		c.message("FLOW");
		c.addScript("try{showRejectWarning();}catch(e){}");

	}

	@Override
	public void doPendingPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		setExistBillOtherData();

	}

	/**
	 * 表單申請人 != 目前使用者 <br>
	 * 明細 待處理 查詢(設定預設值)用 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getBillApplicant() throws SQLException, Exception {
		// TODO Auto-generated method stub
		//c.message("doDetailPageProcess");
		return new UserData(c.getValue("APPLICANT"), c.getTalk());

	}
	
	/**
	 * 表單申請人 = 目前使用者 <br>
	 * 起單用<br>
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getNowApplicant() throws SQLException, Exception {
		// TODO Auto-generated method stub
		//c.message("doDetailPageProcess");
		return new UserData(c.getUser(), c.getTalk());

	}
	
	/**
	 * 表單申請人 = 目前使用者 <br>
	 * 起單用<br>
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getSpecUserData(String empid) throws SQLException, Exception {
		// TODO Auto-generated method stub
		//c.message("doDetailPageProcess");
		return new UserData(empid, c.getTalk());

	}
	
	@Override
	public void doDetailPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		setExistBillOtherData();

	}
	
	/**
	 * 當開啟已存在表單瀏覽時(明細,待處理,簽核中)要設置某些顯示欄位(DB沒有欄位)的資料
	 * @throws SQLException
	 * @throws Exception
	 */
	public void setExistBillOtherData() throws SQLException, Exception {
		// TODO Auto-generated method stub
		//申請人正常必填 其基本資料應該都有
		userdata = getBillApplicant();
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDep_name());
		//專案主持人資料
		if(c.getValue("PROJECT_LEADER").equals("")) {
			c.setValue("PROJECT_LEADER_NAME","");
		}else {
			c.setValue("PROJECT_LEADER_NAME",getSpecUserData(c.getValue("PROJECT_LEADER")).getHecname());
		}
		userdata = null;
		

	}

	@Override
	public void doOtherPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void doViewProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub

	}

}
