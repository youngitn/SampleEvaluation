package oa.SampleEvaluation.common.global;

import java.sql.SQLException;

import jcx.jform.hproc;
import jcx.util.datetime;

/**
 * 可用BaseService代理 hproc目前未出錯 供開發參考
 * 
 * @author u52116
 *
 */
public class FormInitUtil {

	UserData userdata;
	hproc c;

	public FormInitUtil(hproc c) throws Exception {
		this.c = c;
		userdata = new UserData(c.getUser(), c.getTalk());
	}

	public void doQueryPageProcess() throws Exception {

		userdata = getNowApplicant();
		// 取得user資料類別
		// 填入query畫面基本欄位資料
		c.setValue(FinalString.queryEmpidFieldName, userdata.getEmpid());
		c.setValue(FinalString.queryEmpNameFieldName, userdata.getHecname());
		c.setValue(FinalString.queryDepNoFieldName, userdata.getDepNo());
		c.setValue(FinalString.queryDepNameFieldName, userdata.getDepName());
		String today = c.getToday("YYYYmmdd");
		String edate = today;
		String sdate = datetime.dateAdd(edate, "d", -14);
		c.setValue(FinalString.queryReqSDateFieldName, sdate);
		c.setValue(FinalString.queryReqEDateFieldName, edate);
		userdata = null;
		c.setNewView("QueryPage");
	}

	public void doAddPageProcess() throws Exception {
		userdata = getNowApplicant();
		// 新增欄位申請人基本資料填入
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT", c.getUser());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDepName());
		c.setValue("APP_DATE", c.getToday("YYYYmmdd"));
		userdata = null;
	}

	public void doPendingPageProcess() throws Exception {
		setExistBillOtherData();

	}

	/**
	 * 表單申請人 != 目前使用者 <br>
	 * 明細 待處理 查詢(設定預設值)用
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getBillApplicant() throws Exception {
		return new UserData(c.getValue("APPLICANT"), c.getTalk());

	}

	/**
	 * 表單申請人 = 目前使用者 <br>
	 * 起單用<br>
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getNowApplicant() throws Exception {
		return new UserData(c.getUser(), c.getTalk());

	}

	/**
	 * 表單申請人 = 目前使用者 <br>
	 * 起單用<br>
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getSpecUserData(String empid) throws Exception {
		return new UserData(empid, c.getTalk());

	}

	public void doDetailPageProcess() throws Exception {
		setExistBillOtherData();

	}

	/**
	 * 目前為免洗方法
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void setExistBillOtherData() throws Exception {
		// 申請人正常必填 其基本資料應該都有
		userdata = getBillApplicant();
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDepName());
		String pl = c.getValue("PROJECT_LEADER").trim();
		// 專案主持人資料
		if (pl.equals("")) {
			c.setValue("PROJECT_LEADER_NAME", "");
		} else {
			userdata = null;
			userdata = getSpecUserData(pl);
			c.setValue("PROJECT_LEADER_NAME", userdata.getHecname() + " " + userdata.getDepName());
		}
		userdata = null;

	}

}
