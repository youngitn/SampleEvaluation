package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.ysp.service.BaseService;

public class MainQuery {
	CommonDataObj cdo;
	BaseService service;

	public MainQuery(CommonDataObj cdo) {

		this.cdo = cdo;
		service = cdo.getService();
	}

	/**
	 * TODO 預計固定欄位名稱以簡化
	 * 
	 * @return
	 */
	public String getAdvancedCondition() {

		String tablePKName = cdo.getTablePKName();
		String queryId = service.getValue("QUERY_PNO");

		String empid = service.getValue("QUERY_EMP_ID");
		String sdate = service.getValue("QUERY_REQ_SDATE");
		String edate = service.getValue("QUERY_REQ_EDATE");
		String queryFlowStatus = service.getValue("r_status");

		StringBuilder advanced_sql = new StringBuilder();
		if (!"".equals(empid))
			advanced_sql.append("and " + cdo.getTableApplicantFieldName() + " = '" + empid + "' ");
		if (!"".equals(sdate))
			advanced_sql.append("and APP_DATE >= '" + sdate + "' ");
		if (!"".equals(edate))
			advanced_sql.append("and APP_DATE <= '" + edate + "' ");
		if ("已結案".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT = '結案' ");
		if ("簽核中".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT not in ('結案','取消') ");
		if ("待處理".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT = '待處理' ");

		if (!"".equals(queryId))
			advanced_sql.append("and a." + tablePKName + " like '%" + queryId + "%' ");

		advanced_sql.append(" and a." + tablePKName + " =  b." + tablePKName);
		return advanced_sql.toString();
	}

	public String getSqlQueryStr() {
		StringBuilder strSql = new StringBuilder();
		String keyName = cdo.getTablePKName();
		String targetEmpidFieldName = cdo.getTableApplicantFieldName().trim();// TODO 待簡化
		ArrayList<String> resultFieldList = cdo.getQueryResultShowTableFieldList();
		// 單號

		strSql.append("select DISTINCT a.");
		for (String fname : resultFieldList) {
			if (fname.trim().equalsIgnoreCase(targetEmpidFieldName)) {
				strSql.append(getEmpInfoSqlQueryStr(keyName, targetEmpidFieldName));

			} else if (fname.trim().equalsIgnoreCase("'簽核狀態'")) {
				strSql.append(getFlowStateSqlQueryStr(keyName, cdo.getTableName()));

			} else {
				strSql.append(fname);
			}

			strSql.append(",");
		}
		String str = strSql.toString();
		str = str.substring(0, str.length() - 1);
		str += " from  " + cdo.getTableName() + " a," + cdo.getTableName() + "_FLOWC b " + "where 1=1";

		// 主要查詢欄位

		return str;
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
		String sql = getAdvancedCondition();
		sql = getSqlQueryStr() + sql;
		String[][] list = cdo.getTalk().queryFromPool(sql);
		if (list.length > 0) {
			return getQueryResultAfterProcess(list, cdo.getQueryResultShowTableFieldList());
		}
		return null;

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
	public String[][] getQueryResultAfterProcess(String[][] queryResults, ArrayList<String> viewFieldOfResultList)
			throws Throwable {

		int sign_flow_status_index = -1;// 簽核狀態的Index
		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
			if (viewFieldOfResultList.get(i).contains("'簽核狀態'")) {
				sign_flow_status_index = i;
			}
		}

		for (int i = 0; i < queryResults.length; i++) {
			// 取得子流程目前簽核狀態
			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();

			// 取得主流程目前簽核狀態
			String mainFlowStatus = queryResults[i][sign_flow_status_index].trim();

			// 如果子流程和主流程都結案 在查詢結果表格的簽核狀態才顯示"已生效"
			if ((mainFlowStatus.equals("結案") || mainFlowStatus.equals("歸檔"))
					&& (checkFlowStatus.equals("結案") || checkFlowStatus.equals("無"))) {
				queryResults[i][sign_flow_status_index] = "<font color=blue>(已生效)</font>【主流程:" + mainFlowStatus + "】"
						+ "【請驗流程:" + checkFlowStatus + "】";
			}
			// 如果子流程和主流程有一方未結案 則進行加工處理
			else {
				queryResults[i][sign_flow_status_index] = "<font color=red>簽核中</font>" + "【主流程:" + mainFlowStatus
						+ getCurrentFlowGateAndApprover(cdo.getTablePKName(), queryResults[i][0]) + "】" + "【請驗流程:"
						+ checkFlowStatus
						+ getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), queryResults[i][0] + "CHECK")
						+ "】";
			}
		}

		return queryResults;
	}

	/**
	 * 取得目前簽核關卡名稱與簽核人員資料字串 EX:"-(關卡名稱-簽核人1,簽核人2..)" TODO 移動至mainQuery
	 * 
	 * @param pkName  資料表pk欄位名稱
	 * @param pkValue 資料表pk值
	 * @return
	 */
	public String getCurrentFlowGateAndApprover(String pkName, String pkValue) {
		Vector<String> people = service.getApprovablePeople(service.getFunctionName(),
				"a." + pkName + "='" + pkValue + "'");
		StringBuffer sb = new StringBuffer();
		if (people != null) {
			if (people.size() != 0) {
				sb.append("-(");
				for (int j = 0; j < people.size(); j++) {
					if (j != 0)
						sb.append(",");
					String id1 = (String) people.elementAt(j);
					String name1 = service.getName(id1);
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
			ret = service.getTalk().queryFromPool(sql);
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

}
