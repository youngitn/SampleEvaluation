package oa.SampleEvaluation.common;

import java.sql.SQLException;

public class MainQuery {
	CommonDataObj cdo;

	public MainQuery(CommonDataObj cdo) {
		// TODO Auto-generated constructor stub
		this.cdo = cdo;
	}

	public String getAdvancedCondition() {

		String tablePKName = cdo.getTablePKName();
		String queryId = cdo.getQueryFieldValueBillId().trim();
		StringBuilder advanced_sql = new StringBuilder();
		String empid = cdo.getQueryFieldValueEmpid().trim();
		String sdate = cdo.getQueryFieldValueStartAppDate().trim();
		String edate = cdo.getQueryFieldValueEndAppDate().trim();
		String queryFlowStatus = cdo.getQueryFieldValueFlowStatus().trim();

		if (!"".equals(empid))
			advanced_sql.append("and " + cdo.getTableApplicantFieldName() + " = '" + empid + "' ");
		if (!"".equals(sdate))
			advanced_sql.append("and " + cdo.getTableAppDateFieldName() + " >= '" + sdate + "' ");
		if (!"".equals(edate))
			advanced_sql.append("and " + cdo.getTableAppDateFieldName() + " <= '" + edate + "' ");
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
		String targetEmpidFieldName = cdo.getTableApplicantFieldName();
		// 單號
		strSql.append("select DISTINCT a." + keyName);
		strSql.append(",");
		// 員工基本資料 姓名-工號-部門名稱
		strSql.append(getEmpInfoSqlQueryStr(keyName, targetEmpidFieldName));
		strSql.append(",");
		strSql.append("APP_TYPE");
		strSql.append(",");
		strSql.append("URGENCY");
		strSql.append(",");
		strSql.append("APP_DATE");
		strSql.append(",");
		strSql.append(getFlowStateSqlQueryStr(keyName, cdo.getTableName()));
		strSql.append(",");
		strSql.append("'明細','簽核紀錄'");

		strSql.append(" from  " + cdo.getTableName() + " a," + cdo.getTableName() + "_FLOWC b " + "where 1=1");

		// 主要查詢欄位

		return strSql.toString();
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

	public String[][] getQueryResult() throws SQLException, Exception {
		String sql = getAdvancedCondition();
		sql = getSqlQueryStr() + sql;
		String[][] list = cdo.getTalk().queryFromPool(sql);
		return list;

	}

}
