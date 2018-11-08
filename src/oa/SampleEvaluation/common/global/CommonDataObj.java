package oa.SampleEvaluation.common.global;

import java.util.ArrayList;
import jcx.db.talk;

public abstract class CommonDataObj {

	private String loginUserId;// 目前登入使用者id

	private TableFieldSpec tableFieldSpec = new TableFieldSpec();
	private talk talk;
	private ArrayList<String> queryResultShowTableFieldList;

	private ArrayList<String> qflist;
	private QuerySpec qs;
	private String functionName;// 表單名稱

	public CommonDataObj(talk t, String tableName, String tablePKName, String tableApplicantFieldName)
			throws Exception {
		this.tableFieldSpec.name = tableName;
		this.tableFieldSpec.pkName = tablePKName;
		this.tableFieldSpec.allColumn = t.queryFromPool(
				"select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where table_name= '" + tableName + "'");
		this.tableFieldSpec.applicantFieldName = tableApplicantFieldName;
		this.talk = t;

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
		return tableFieldSpec.pkName;
	}

	/**
	 * @param tablePKName the tablePKName to set
	 */
	public void setTablePKName(String tablePKName) {
		this.tableFieldSpec.pkName = tablePKName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableFieldSpec.name;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableFieldSpec.name = tableName;
	}

	/**
	 * @return the allColumn
	 */
	public String[][] getTableAllColumn() {
		return tableFieldSpec.allColumn;
	}

	/**
	 * @param allColumn the allColumn to set
	 */
	public void setAllColumn(String[][] tableAllColumn) {
		this.tableFieldSpec.allColumn = tableAllColumn;
	}

	/**
	 * 申請人欄位名稱
	 * 
	 * @return
	 */
	public String getTableApplicantFieldName() {
		return tableFieldSpec.applicantFieldName;
	}

	public void setTableApplicantFieldName(String tableApplicantFieldName) {
		this.tableFieldSpec.applicantFieldName = tableApplicantFieldName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public ArrayList<String> getQflist() {
		return qflist;
	}

	public void setQflist(ArrayList<String> qflist) {
		this.qflist = qflist;
	}

	public QuerySpec getQs() {
		return qs;
	}

	public void setQs(QuerySpec qs) {
		this.qs = qs;
	}

	public void setTableAllColumn(String[][] tableAllColumn) {
		this.tableFieldSpec.allColumn = tableAllColumn;
	}

	/**
	 * 取得查詢頁面申請日期欄位名稱
	 * 
	 * @return
	 */
	public String getTableAppDateFieldName() {
		return tableFieldSpec.appDateFieldName;
	}

	/**
	 * 設置查詢頁面申請日期欄位名稱
	 * 
	 * @return
	 */
	public void setTableAppDateFieldName(String tableAppDateFieldName) {
		this.tableFieldSpec.appDateFieldName = tableAppDateFieldName;
	}

	/**
	 * 設置查詢欄位列表
	 * 
	 * @param qflist
	 */
	public void setQueryInputFieldList(ArrayList<String> qflist) {
		this.qflist = qflist;
	}

	public QuerySpec getQuerySpec() {
		return this.qs;
	}

	public void setQuerySpec(QuerySpec qs) {

		this.qs = qs;

	}

	public ArrayList<String> getQueryResultShowTableFieldList() {
		return queryResultShowTableFieldList;
	}

	public void setQueryResultShowTableFieldList(ArrayList<String> queryResultShowTableFieldList) {
		this.queryResultShowTableFieldList = queryResultShowTableFieldList;
	}

	public TableFieldSpec getTableFieldSpec() {
		return tableFieldSpec;
	}

	public void setTableFieldSpec(TableFieldSpec tableFieldSpec) {
		this.tableFieldSpec = tableFieldSpec;
	}
}
