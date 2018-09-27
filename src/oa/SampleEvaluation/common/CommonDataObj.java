package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;

import jcx.db.talk;

public class CommonDataObj {

	private String tableName;//資料表名稱 
	private String tablePKName;//PK名稱 用來記錄單號
	private String functionName;//表單名稱
	private String[][] tableAllColumn;//裝資料表所有欄位之陣列
	private UserData userdata;//員工基本資料物件

	private String tableApplicantFieldName;//資料表中申請人欄位名稱
	private String tableAppDateFieldName;//資料表申請日期欄位名稱
	
	private String queryFieldNameEmpid;//查詢頁面申請人員編欄位名稱
	private String queryFieldNameStartAppDate;//查詢頁面申請日期 起日 欄位名稱
	private String queryFieldNameEndAppDate;//查詢頁面申請日期 迄日 欄位名稱
	private String queryFieldNameFlowStatus;//查詢頁面簽核狀態 欄位名稱
	private String queryFieldNameBillId;//查詢頁面單號 欄位名稱
	
	private String queryFieldValueEmpid;//查詢頁面申請人員編值
	private String queryFieldValueStartAppDate;//查詢頁面申請日期 起日 欄位值
	private String queryFieldValueEndAppDate;//查詢頁面申請日期 迄日 欄位值
	private String queryFieldValueFlowStatus;//查詢頁面簽核狀態 欄位值
	private String queryFieldValueBillId;//查詢頁面單號 欄位值
	private talk talk;
	private String queryFieldNameSubFlowStatus;
	private ArrayList<String> queryResultShowTableFieldList;
	public ArrayList<String> getQueryResultShowTableFieldList() {
		return queryResultShowTableFieldList;
	}

	public void setQueryResultShowTableFieldList(ArrayList<String> queryResultShowTableFieldList) {
		this.queryResultShowTableFieldList = queryResultShowTableFieldList;
	}

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

	public void setQueryFieldValueSubFlowStatus(String queryFieldNameSubFlowStatus) {
		// TODO Auto-generated method stub
		this.queryFieldNameSubFlowStatus = queryFieldNameSubFlowStatus;
	}

	public String getQueryFieldValueSubFlowStatus() {
		// TODO Auto-generated method stub
		return this.queryFieldNameSubFlowStatus;
	}
}
