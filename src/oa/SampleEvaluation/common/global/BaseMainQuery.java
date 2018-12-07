package oa.SampleEvaluation.common.global;

import java.sql.SQLException;
import java.util.List;

import com.ysp.service.BaseService;
import com.ysp.util.LogUtil;

import jcx.db.talk;
import oa.SampleEvaluation.controller.HprocImpl;

public abstract class BaseMainQuery {
	protected CommonDataObj cdo;
	/**
	 * <h1>service<br>
	 * 會用到jcx某類別的方法時,可直接new一個實作該類的類別 <br>
	 * EX:SampleEvaluationActionController extends hproc <-- <br>
	 * 不使用 getValue()等取得外部資訊的方法 <br>
	 * 僅使用getFlowHis()等方法 <br>
	 * getName()等需要系統資訊的也一樣不使用
	 */
	protected BaseService service;
	protected String tablePKName;
	protected String tableName;
	protected String tableApplicantFieldName;
	protected String tableAppDateFieldName;
	protected talk innerTalk;
	protected TableFieldSpec tableFieldSpec;
	protected UserData userData;

	public BaseMainQuery(CommonDataObj cdo) {

		this.cdo = cdo;

		this.service = new BaseService(new HprocImpl());
		tableFieldSpec = cdo.getTableFieldSpec();
		tablePKName = tableFieldSpec.pkName;
		tableName = tableFieldSpec.name;
		tableApplicantFieldName = tableFieldSpec.applicantFieldName;
		tableAppDateFieldName = tableFieldSpec.appDateFieldName;
		innerTalk = cdo.getTalk();
		try {
			userData = new UserData(cdo.getLoginUserId(), innerTalk);
		} catch (SQLException e) {
			LogUtil.getLog(getClass()).error(e);
		} catch (Exception e) {
			LogUtil.getLog(getClass()).error(e);
		}

	}

	// 取得查詢權限SQL條件
	//public abstract String getQueryRightSql() throws Exception;

	/**
	 * 
	 * 
	 * @return
	 */
	//public abstract String getAdvancedCondition();

	/**
	 * @param queryFlowStatus
	 * @param advanced_sql
	 */
	protected String statusCheck(String queryFlowStatus, String tableCode) {
		StringBuilder advanced_sql = new StringBuilder("");

		if ("已結案".equals(queryFlowStatus))
			advanced_sql.append("and " + tableCode + ".F_INP_STAT = '結案' ");
		if ("簽核中".equals(queryFlowStatus))
			advanced_sql.append("and " + tableCode + ".F_INP_STAT not in ('結案','取消') ");
		if ("待處理".equals(queryFlowStatus))
			advanced_sql.append("and " + tableCode + ".F_INP_STAT = '待處理' ");

		return advanced_sql.toString();
	}

	//public abstract String getSqlQueryStr() throws Exception;

	/**
	 * 創建主查詢SQL字串 員工資料部分 將hruser和hruser_dept_bas 作交差查詢 取得員工基本資料
	 * (姓名-工號-部門名稱)<--將其設為同一欄位 無法單獨使用
	 * 
	 * @param key
	 * @param targetEmpidFieldName
	 * @return
	 */
	public String getEmpInfoSqlQueryStr(String key, String targetEmpidFieldName) {

		// 員工基本資料 姓名-工號-部門名稱
		return "(select (hecname+'-'+empid) from hruser where empid=a." + targetEmpidFieldName + ")+'-'+"
				+ "(select dep_name from hruser_dept_bas where dep_no in (select dept_no from hruser where empid=a."
				+ targetEmpidFieldName + "))";
	}

	public String getFlowStateSqlQueryStr(String key, String tableName) {
		return "(select f_inp_stat from " + tableName + "_flowc where " + key + "=a." + key + ") ";
	}

//	public String[][] getQueryResult() throws Throwable {
//		String sql = "";
//		sql = getSqlQueryStr();
//		String[][] list = cdo.getTalk().queryFromPool(sql);
//		if (list.length > 0) {
//			return getQueryResultAfterProcess(list, cdo.getQuerySpec().getQueryResultView());
//		}
//		return list;
//
//	}

	/**
	 * @param queryResults          查詢結果 String[][]
	 * @param viewFieldOfResultList 查詢結果顯示欄位ArrayList
	 * @param c                     Controller
	 * @return
	 * @throws Throwable
	 */
//	protected abstract String[][] getQueryResultAfterProcess(String[][] queryResults,
//			List<String> viewFieldOfResultList) throws Throwable;

	/**
	 * 取得目前簽核關卡名稱與簽核人員資料字串 EX:"-(關卡名稱-簽核人1,簽核人2..)"
	 * 
	 * @param pkName  資料表pk欄位名稱
	 * @param pkValue 資料表pk值
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	protected String getCurrentFlowGateAndApprover(String pkName, String pkValue) throws SQLException, Exception {
		List<String> people = service.getApprovablePeople(cdo.getFunctionName(), "a." + pkName + "='" + pkValue + "'");
		StringBuffer sb = new StringBuffer();

		if (!people.isEmpty()) {
			sb.append("-(");
			for (int j = 0; j < people.size(); j++) {
				if (j != 0)
					sb.append(",");
				String id1 = (String) people.get(j);
				UserData u = new UserData(id1, innerTalk);
				String name1 = u.getHecname();
				sb.append(name1 + "-" + id1);
			}
			sb.append(")");
		}

		return sb.toString();
	}

	// 確定查詢權限
	protected boolean isAdmin() {
		return (userData.getDepNo().equals("1001") || userData.getEmpid().equals("52116")
				|| userData.getDepNo().equals("21"));

	}

}
