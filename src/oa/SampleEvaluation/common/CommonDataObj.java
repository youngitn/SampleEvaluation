package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ysp.service.BaseService;

import jcx.db.talk;

public class CommonDataObj {

	private String loginUserId;// �ثe�n�J�ϥΪ�id

	private String tableName;// ��ƪ�W��
	private String tablePKName;// PK�W�� �ΨӰO���渹
	private String functionName;// ���W��
	private String[][] tableAllColumn;// �˸�ƪ�Ҧ���줧�}�C

	private String tableApplicantFieldName;// ��ƪ��ӽФH���W��
	private String tableAppDateFieldName;// ��ƪ�ӽФ�����W��

	private talk talk;
	private ArrayList<String> queryResultShowTableFieldList;
	private BaseService service;
	private ArrayList<String> qflist;
	private QuerySpec qs;

	public ArrayList<String> getQueryResultShowTableFieldList() {
		return queryResultShowTableFieldList;
	}

	public void setQueryResultShowTableFieldList(ArrayList<String> queryResultShowTableFieldList) {
		this.queryResultShowTableFieldList = queryResultShowTableFieldList;
	}

	public CommonDataObj(talk t, String tableName, String tablePKName, String tableApplicantFieldName)
			throws SQLException, Exception {
		this.tableName = tableName;
		this.tablePKName = tablePKName;
		this.tableAllColumn = t.queryFromPool(
				"select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where table_name= '" + tableName + "'");
		this.tableApplicantFieldName = tableApplicantFieldName;
		this.talk = t;

	}

	public CommonDataObj(BaseService service, String tablePKName, String tableApplicantFieldName)
			throws SQLException, Exception {
		this.service = service;
		this.tableName = service.getTableName();
		this.tablePKName = tablePKName;
		this.talk = service.getTalk();
		this.tableAllColumn = talk.queryFromPool(
				"select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where table_name= '" + tableName + "'");
		this.tableApplicantFieldName = tableApplicantFieldName;

	}

	public BaseService getService() {
		return service;
	}

	public void setService(BaseService service) {
		this.service = service;
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

	/**
	 * �ӽФH���W��
	 * 
	 * @return
	 */
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
		this.tableAllColumn = tableAllColumn;
	}

	/**
	 * ���o�d�߭����ӽФ�����W��
	 * 
	 * @return
	 */
	public String getTableAppDateFieldName() {
		return tableAppDateFieldName;
	}

	/**
	 * �]�m�d�߭����ӽФ�����W��
	 * 
	 * @return
	 */
	public void setTableAppDateFieldName(String tableAppDateFieldName) {
		this.tableAppDateFieldName = tableAppDateFieldName;
	}

	/**
	 * �]�m�d�����C��
	 * 
	 * @param qflist
	 */
	public void setQueryInputFieldList(ArrayList<String> qflist) {
		this.qflist = qflist;
	}

	public QuerySpec getQuerySpec() {
		return this.qs;
	}

	public void setQuerySpec(SampleEvaluationQuerySpec qs) {

		this.qs = qs;

	}
}
