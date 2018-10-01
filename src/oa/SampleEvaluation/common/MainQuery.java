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
		if ("�w����".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT = '����' ");
		if ("ñ�֤�".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT not in ('����','����') ");
		if ("�ݳB�z".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT = '�ݳB�z' ");

		if (!"".equals(queryId))
			advanced_sql.append("and a." + tablePKName + " like '%" + queryId + "%' ");

		advanced_sql.append(" and a." + tablePKName + " =  b." + tablePKName);
		return advanced_sql.toString();
	}

	public String getSqlQueryStr() {
		StringBuilder strSql = new StringBuilder();
		String keyName = cdo.getTablePKName();
		String targetEmpidFieldName = cdo.getTableApplicantFieldName();
		// �渹
		strSql.append("select DISTINCT a." + keyName);
		strSql.append(",");
		// ���u�򥻸�� �m�W-�u��-�����W��
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
		strSql.append("'����','ñ�֬���'");

		strSql.append(" from  " + cdo.getTableName() + " a," + cdo.getTableName() + "_FLOWC b " + "where 1=1");

		// �D�n�d�����

		return strSql.toString();
	}

	/**
	 * �ЫإD�d��SQL�r�� ���u��Ƴ��� �Nhruser�Mhruser_dept_bas �@��t�d�� ���o���u�򥻸��
	 * (�m�W-�u��-�����W��)<--�N��]���P�@��� �L�k��W�ϥ�
	 * 
	 * @param key
	 * @param targetEmpidFieldName
	 * @return
	 */
	public String getEmpInfoSqlQueryStr(String key, String targetEmpidFieldName) {

		// ���u�򥻸�� �m�W-�u��-�����W��
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
