package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ysp.service.BaseService;

import jcx.db.talk;

public class CommonDataObj {

	private String tableName;// ��ƪ�W��
	private String tablePKName;// PK�W�� �ΨӰO���渹
	private String functionName;// ���W��
	private String[][] tableAllColumn;// �˸�ƪ�Ҧ���줧�}�C

	private String tableApplicantFieldName;// ��ƪ��ӽФH���W��
	private String tableAppDateFieldName;// ��ƪ�ӽФ�����W��

	private String queryFieldNameEmpid;// �d�߭����ӽФH���s���W��
	private String queryFieldNameStartAppDate;// �d�߭����ӽФ�� �_�� ���W��
	private String queryFieldNameEndAppDate;// �d�߭����ӽФ�� ���� ���W��
	private String queryFieldNameFlowStatus;// �d�߭���ñ�֪��A ���W��
	private String queryFieldNameBillId;// �d�߭����渹 ���W��

	private String queryFieldValueEmpid;// �d�߭����ӽФH���s��
	private String queryFieldValueStartAppDate;// �d�߭����ӽФ�� �_�� ����
	private String queryFieldValueEndAppDate;// �d�߭����ӽФ�� ���� ����
	private String queryFieldValueFlowStatus;// �d�߭���ñ�֪��A ����
	private String queryFieldValueBillId;// �d�߭����渹 ����
	private talk talk;
	private String queryFieldNameSubFlowStatus;
	private ArrayList<String> queryResultShowTableFieldList;
	private BaseService service;
	private ArrayList<String> qflist;

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

	/**
	 * ���o�d�߭����渹���W��
	 * 
	 * @return
	 */
	public String getQueryFieldNameBillId() {
		return queryFieldNameBillId;
	}

	/**
	 * �]�m�o�d�߭����渹���W��
	 * 
	 * @return
	 */
	public void setQueryFieldNameBillId(String queryFieldNameBillId) {
		this.queryFieldNameBillId = queryFieldNameBillId;
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

	public void setQueryFieldValueSubFlowStatus(String queryFieldNameSubFlowStatus) {
		// TODO Auto-generated method stub
		this.queryFieldNameSubFlowStatus = queryFieldNameSubFlowStatus;
	}

	public String getQueryFieldValueSubFlowStatus() {
		// TODO Auto-generated method stub
		return this.queryFieldNameSubFlowStatus;
	}

	/**
	 * �]�m�d�����C��
	 * 
	 * @param qflist
	 */
	public void setQueryInputFieldList(ArrayList<String> qflist) {
		// TODO Auto-generated method stub
		this.qflist = qflist;
	}
}
