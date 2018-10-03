package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.SampleEvaluationActionController;

public class MainQuery {
	CommonDataObj cdo;
	BaseService service;
	String tablePKName;
	String tableName;
	String tableApplicantFieldName;
	String tableAppDateFieldName;
	talk innerTalk;

	public MainQuery(CommonDataObj cdo) {

		this.cdo = cdo;
		this.service = new BaseService(new SampleEvaluationActionController());
		tablePKName = cdo.getTablePKName();
		tableName = cdo.getTableName();
		tableApplicantFieldName = cdo.getTableApplicantFieldName();
		tableAppDateFieldName = cdo.getTableAppDateFieldName();
		innerTalk = cdo.getTalk();
	}

	// 取得查詢權限SQL條件
	private String getQueryRightSql() throws SQLException, Exception {
		String sql = "";
		boolean isadmin = isAdmin(cdo.getLoginUserId());
		// 如果不是系統管理員群組人員，須加入查詢權限
		if (!isadmin) {
			sql += " and (";
			// 申請人為自己部屬
			sql += "(" + cdo.getTableApplicantFieldName() + " in (select handle_user from hr_condition where empid = '"
					+ cdo.getLoginUserId() + "')) ";
			// 自己簽核過的單子
			sql += "or (a." + tablePKName + " in (select distinct " + tablePKName + " from " + tableName
					+ "_FLOWC_HIS where F_INP_ID = '" + cdo.getLoginUserId() + "')) ";

			sql += ") ";
		}
		return sql;
	}

	/**
	 * TODO 預計固定欄位名稱以簡化
	 * 
	 * @return
	 */
	public String getAdvancedCondition() {
		// 設置查詢欄位
		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();
		String queryId = qc.getQueryBillId();
		String empid = qc.getQueryEmpid();
		String sdate = qc.getQueryReqSDate();
		String edate = qc.getQueryReqEDate();
		String queryFlowStatus = qc.getQueryStatus();
		String queryFlowStatusCheck = qc.getQueryStatusCheck();

		StringBuilder advanced_sql = new StringBuilder();
		if (!"".equals(empid))
			advanced_sql.append("and " + tableApplicantFieldName + " = '" + empid + "' ");
		if (!"".equals(sdate))
			advanced_sql.append("and " + tableAppDateFieldName + " >= '" + sdate + "' ");
		if (!"".equals(edate))
			advanced_sql.append("and " + tableAppDateFieldName + " <= '" + edate + "' ");

		// status
		if ("已結案".equals(queryFlowStatus))
			advanced_sql.append("and b.F_INP_STAT = '結案' ");
		if ("簽核中".equals(queryFlowStatus))
			advanced_sql.append("and b.F_INP_STAT not in ('結案','取消') ");
		if ("待處理".equals(queryFlowStatus))
			advanced_sql.append("and b.F_INP_STAT = '待處理' ");
		if ("已結案".equals(queryFlowStatusCheck))
			advanced_sql.append("and c.F_INP_STAT = '結案' ");
		if ("簽核中".equals(queryFlowStatusCheck))
			advanced_sql.append("and c.F_INP_STAT not in ('結案','取消') ");
		if ("待處理".equals(queryFlowStatusCheck))
			advanced_sql.append("and c.F_INP_STAT = '待處理' ");

		if (!"".equals(queryId))
			advanced_sql.append("and a." + tablePKName + " like '%" + queryId + "%' ");

		advanced_sql.append(" and a." + tablePKName + " =  b." + tablePKName);
		if (!"".equals(queryFlowStatusCheck)) {
			advanced_sql.append(" and a." + tablePKName + "+'CHECK' =  c.OWN_" + tablePKName);
		}

		return advanced_sql.toString();
	}

	public String getSqlQueryStr() throws SQLException, Exception {
		StringBuilder strSql = new StringBuilder();
		// String keyName = cdo.getTablePKName();
		// String targetEmpidFieldName = cdo.getTableApplicantFieldName().trim();// TODO
		// 待簡化
		ArrayList<String> resultFieldList = cdo.getQuerySpec().getQueryResultView();
		// 單號

		strSql.append("select DISTINCT a.");
		for (String fname : resultFieldList) {
			if (fname.trim().equalsIgnoreCase(tableApplicantFieldName)) {
				strSql.append(getEmpInfoSqlQueryStr(tablePKName, tableApplicantFieldName));

			} else if (fname.trim().equalsIgnoreCase("'簽核狀態'")) {
				strSql.append(getFlowStateSqlQueryStr(tablePKName, tableName));

			} else {
				strSql.append(fname);
			}

			strSql.append(",");
		}
		String str = strSql.toString();
		str = str.substring(0, str.length() - 1);
		str += " from  " + tableName + " a," + tableName + "_FLOWC b, " + tableName + "_CHECK_FLOWC c " + " where 1=1 ";

		// 主要查詢欄位+查詢條件+權限控制
		return str + getAdvancedCondition() + getQueryRightSql();
	}

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

	public String[][] getQueryResult() throws Throwable {
		String sql = "";
		sql = getSqlQueryStr();
		String[][] list = cdo.getTalk().queryFromPool(sql);
		if (list.length > 0) {
			return getQueryResultAfterProcess(list, cdo.getQuerySpec().getQueryResultView());
		}
		return list;

	}

	/**
	 * TODO 移動至mainQuery 對簽核狀態做顯示加工
	 * 
	 * @param queryResults          查詢結果 String[][]
	 * @param viewFieldOfResultList 查詢結果顯示欄位ArrayList
	 * @param c                     Controller
	 * @return
	 * @throws Throwable
	 */
	private String[][] getQueryResultAfterProcess(String[][] queryResults, ArrayList<String> viewFieldOfResultList)
			throws Throwable {

		int sign_flow_status_index = getStatusIndex(viewFieldOfResultList);// 簽核狀態的Index

		for (int i = 0; i < queryResults.length; i++) {
			// 取得子流程目前簽核狀態
			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();

			// 取得主流程目前簽核狀態
			String mainFlowStatus = queryResults[i][sign_flow_status_index].trim();

			// 如果子流程和主流程都結案 在查詢結果表格的簽核狀態才顯示"已生效"
			queryResults[i][sign_flow_status_index] = getSignFlowStatus(queryResults[i][0], mainFlowStatus,
					checkFlowStatus);
		}

		return queryResults;
	}

	private String getSignFlowStatus(String id, String mainFlowStatus, String checkFlowStatus)
			throws SQLException, Exception {
		if ((mainFlowStatus.equals("結案") || mainFlowStatus.equals("歸檔"))
				&& (checkFlowStatus.equals("結案") || checkFlowStatus.equals("無"))) {
			return "<font color=blue>(已生效)</font>【主流程:" + mainFlowStatus + "】" + "【請驗流程:" + checkFlowStatus + "】";
		}
		// 如果子流程和主流程有一方未結案 則進行加工處理
		else {
			return "<font color=red>簽核中</font>" + "【主流程:" + mainFlowStatus
					+ getCurrentFlowGateAndApprover(cdo.getTablePKName(), id) + "】" + "【請驗流程:" + checkFlowStatus
					+ getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "CHECK") + "】";
		}
	}

	private int getStatusIndex(ArrayList<String> viewFieldOfResultList) {
		int sign_flow_status_index = -1;
		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
			if (viewFieldOfResultList.get(i).contains("'簽核狀態'")) {
				sign_flow_status_index = i;
			}
		}
		return sign_flow_status_index;
	}

	/**
	 * 取得目前簽核關卡名稱與簽核人員資料字串 EX:"-(關卡名稱-簽核人1,簽核人2..)" TODO 移動至mainQuery
	 * 
	 * @param pkName  資料表pk欄位名稱
	 * @param pkValue 資料表pk值
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	private String getCurrentFlowGateAndApprover(String pkName, String pkValue) throws SQLException, Exception {
		Vector<String> people = service.getApprovablePeople(cdo.getFunctionName(),
				"a." + pkName + "='" + pkValue + "'");
		StringBuffer sb = new StringBuffer();
		if (people != null) {
			if (people.size() != 0) {
				sb.append("-(");
				for (int j = 0; j < people.size(); j++) {
					if (j != 0)
						sb.append(",");
					String id1 = (String) people.elementAt(j);
					UserData u = new UserData(id1, innerTalk);
					String name1 = u.getHecname();
					u = null;
					sb.append(name1 + "-" + id1);
				}
				sb.append(")");
			}
		}

		return sb.toString();
	}

	public String getCheckFlowStatus(String ownPno) {
		String sql = "SELECT F_INP_STAT FROM SAMPLE_EVALUATION_CHECK_FLOWC WHERE OWN_PNO='" + ownPno + "'";
		String[][] ret = null;
		try {
			ret = innerTalk.queryFromPool(sql);
			if (ret.length == 0) {
				// 如果沒有請驗流程視為請驗已結案
				return "無";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret[0][0];
	}

	// 確定查詢權限
	private boolean isAdmin(String user) throws SQLException, Exception {
		String sql_hruser_dept = "select id from hruser_dept where dep_no = '1001' or ID='52116'";
		String[][] ret_hruser_dept = innerTalk.queryFromPool(sql_hruser_dept);
		if (ret_hruser_dept.length > 0) {
			for (int i = 0; i < ret_hruser_dept.length; i++) {
				if (ret_hruser_dept[i][0].equals(user)) {
					return true;
				}
			}
		}
		return false;
	}

}
