package oa.SampleEvaluation.common;

import java.sql.SQLException;

import jcx.db.talk;

public class CommonDataObj {

	private String tableName;
	private String tablePKName;
	private String functionName;
	private String[][] tableAllColumn;
	private UserData userdata;

	private String tableApplicantFieldName;
	private String tableAppDateFieldName;
	private String queryFieldNameEmpid;
	private String queryFieldNameStartAppDate;
	private String queryFieldNameEndAppDate;
	private String queryFieldNameFlowStatus;
	private String queryFieldNameBillId;
	private String queryFieldValueEmpid;
	private String queryFieldValueStartAppDate;
	private String queryFieldValueEndAppDate;
	private String queryFieldValueFlowStatus;
	private String queryFieldValueBillId;
	private talk talk;

	public CommonDataObj(String empid, talk t, String tableName, String tablePKName, String tableApplicantFieldName)
			throws SQLException, Exception {
		this.userdata = new UserData(empid, t);
		this.tableName = tableName;
		this.tablePKName = tablePKName;
		this.tableAllColumn = t.queryFromPool(
				"select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where table_name= '" + tableName + "'");
		this.tableApplicantFieldName = tableApplicantFieldName;
		this.talk = t;

	}

	public CommonDataObj() {

	}

	public talk getTalk() {
		return talk;
	}

	public void setTalk(talk t) {
		this.talk = t;
	}

	/**
	 * @return the tablePKName
	 */
	public String getTablePKName() {
		return tablePKName;
	}

	/**
	 * @param tablePKName the tablePKName to set
	 */
	public void setTablePKName(String tablePKName) {
		this.tablePKName = tablePKName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the allColumn
	 */
	public String[][] getTableAllColumn() {
		return tableAllColumn;
	}

	/**
	 * @param allColumn the allColumn to set
	 */
	public void setAllColumn(String[][] tableAllColumn) {
		this.tableAllColumn = tableAllColumn;
	}

	public UserData getUserdata() {
		return userdata;
	}

	public void setUserdata(UserData userdata) {
		this.userdata = userdata;
	}

	public String getTableApplicantFieldName() {
		return tableApplicantFieldName;
	}

	public void setTableApplicantFieldName(String tableApplicantFieldName) {
		this.tableApplicantFieldName = tableApplicantFieldName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getQueryFieldNameEmpid() {
		return queryFieldNameEmpid;
	}

	public void setQueryFieldNameEmpid(String queryFieldNameEmpid) {
		this.queryFieldNameEmpid = queryFieldNameEmpid;
	}

	public String getQueryFieldNameStartAppDate() {
		return queryFieldNameStartAppDate;
	}

	public void setQueryFieldNameStartAppDate(String queryFieldName) {
		this.queryFieldNameStartAppDate = queryFieldName;
	}

	public String getQueryFieldNameEndAppDate() {
		return queryFieldNameEndAppDate;
	}

	public void setQueryFieldNameEndAppDate(String queryFieldName) {
		this.queryFieldNameEndAppDate = queryFieldName;
	}

	public String getQueryFieldNameFlowStatus() {
		return queryFieldNameFlowStatus;
	}

	public String getQueryFieldValueEmpid() {
		return queryFieldValueEmpid;
	}

	public void setQueryFieldValueEmpid(String queryFieldValueEmpid) {
		this.queryFieldValueEmpid = queryFieldValueEmpid;
	}

	public String getQueryFieldValueStartAppDate() {
		return queryFieldValueStartAppDate;
	}

	public void setQueryFieldValueStartAppDate(String queryFieldValueStartAppDate) {
		this.queryFieldValueStartAppDate = queryFieldValueStartAppDate;
	}

	public String getQueryFieldValueEndAppDate() {
		return queryFieldValueEndAppDate;
	}

	public void setQueryFieldValueEndAppDate(String queryFieldValueEndAppDate) {
		this.queryFieldValueEndAppDate = queryFieldValueEndAppDate;
	}

	public String getQueryFieldValueFlowStatus() {
		return queryFieldValueFlowStatus;
	}

	public void setQueryFieldValueFlowStatus(String queryFieldValueFlowStatus) {
		this.queryFieldValueFlowStatus = queryFieldValueFlowStatus;
	}

	public String getQueryFieldValueBillId() {
		return queryFieldValueBillId;
	}

	public void setQueryFieldValueBillId(String queryFieldValueBillId) {
		this.queryFieldValueBillId = queryFieldValueBillId;
	}

	public void setQueryFieldNameFlowStatus(String queryFieldNameFlowStatus) {
		this.queryFieldNameFlowStatus = queryFieldNameFlowStatus;
	}

	public String getQueryFieldNameBillId() {
		return queryFieldNameBillId;
	}

	public void setQueryFieldNameBillId(String queryFieldNameBillId) {
		this.queryFieldNameBillId = queryFieldNameBillId;
	}

	public String getTableAppDateFieldName() {
		return tableAppDateFieldName;
	}

	public void setTableAppDateFieldName(String tableAppDateFieldName) {
		this.tableAppDateFieldName = tableAppDateFieldName;
	}
}
