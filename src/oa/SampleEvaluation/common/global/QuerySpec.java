package oa.SampleEvaluation.common.global;

import java.util.List;

import oa.SampleEvaluation.controller.HprocImpl;

public abstract class QuerySpec {

	String queryBillId;// 查詢單號value
	String queryEmpid;// 查詢申請人value
	String queryReqSDate;// 查詢起日value
	String queryReqEDate;// 查詢迄日value
	String queryStatus;// 查詢簽核狀態value
	String queryDepNo;// 查詢單位號碼value
	// 以下變數可自設,但以QueryFieldNameFinalString為主
	String queryBillIdFieldName = FinalString.QUERY_BILL_ID_FIELD_NAME;// 查詢單號欄位名稱
	String queryEmpidFieldName = FinalString.QUERY_EMPID_FIELD_NAME;// 查詢申請人欄位名稱
	String queryReqSDateFieldName = FinalString.QUERY_REQ_SDATE_FIELD_NAME;// 查詢起日欄位名稱
	String queryReqEDateFieldName = FinalString.QUERY_REQ_EDATE_FIELD_NAME;// 查詢迄日欄位名稱
	String queryStatusFieldName = FinalString.QUERY_STATUS_FIELD_NAME;// 查詢簽核狀態欄位名稱
	String queryDepNoFieldName = FinalString.QUERY_DEP_NO_FIELD_NAME;// 查詢單位號碼欄位名稱

	List<String> queryResultView;// Dmaker表格顯示欄位名稱arraylist,名稱為執行SQL查詢的DB欄位名稱

	public QuerySpec(HprocImpl h) {

		this.setQueryBillId(h.getValue(queryBillIdFieldName));
		this.setQueryEmpid(h.getValue(queryEmpidFieldName));
		this.setQueryReqSDate(h.getValue(queryReqSDateFieldName));
		this.setQueryReqEDate(h.getValue(queryReqEDateFieldName));
		this.setQueryStatus(h.getValue(queryStatusFieldName));
		this.setQueryDepNo(h.getValue(queryDepNoFieldName));
	}

	public List<String> getQueryResultView() {
		return queryResultView;
	}

	public void setQueryResultView(List<String> queryResultView) {
		this.queryResultView = queryResultView;
	}

	public String getQueryBillId() {
		return queryBillId;
	}

	public void setQueryBillIdFieldName(String queryBillIdFieldName) {

		this.queryBillIdFieldName = queryBillIdFieldName;

	}

	public String qetQueryBillIdFieldName() {

		return this.queryBillIdFieldName;

	}

	public void setQueryBillId(String queryBillId) {
		this.queryBillId = queryBillId;
	}

	public String getQueryEmpid() {
		return queryEmpid;
	}

	public void setQueryEmpid(String queryEmpid) {
		this.queryEmpid = queryEmpid;
	}

	public String getQueryReqSDate() {
		return queryReqSDate;
	}

	public void setQueryReqSDate(String queryReqSDate) {
		this.queryReqSDate = queryReqSDate;
	}

	public String getQueryReqEDate() {
		return queryReqEDate;
	}

	public void setQueryReqEDate(String queryReqEDate) {
		this.queryReqEDate = queryReqEDate;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getQueryEmpidFieldName() {
		return queryEmpidFieldName;
	}

	public void setQueryEmpidFieldName(String queryEmpidFieldName) {
		this.queryEmpidFieldName = queryEmpidFieldName;
	}

	public String getQueryReqSDateFieldName() {
		return queryReqSDateFieldName;
	}

	public void setQueryReqSDateFieldName(String queryReqSDateFieldName) {
		this.queryReqSDateFieldName = queryReqSDateFieldName;
	}

	public String getQueryReqEDateFieldName() {
		return queryReqEDateFieldName;
	}

	public void setQueryReqEDateFieldName(String queryReqEDateFieldName) {
		this.queryReqEDateFieldName = queryReqEDateFieldName;
	}

	public String getQueryStatusFieldName() {
		return queryStatusFieldName;
	}

	public void setQueryStatusFieldName(String queryStatusFieldName) {
		this.queryStatusFieldName = queryStatusFieldName;
	}

	public String getQueryDepNoFieldName() {
		return queryDepNoFieldName;
	}

	public void setQueryDepNoFieldName(String queryDepNoFieldName) {
		this.queryDepNoFieldName = queryDepNoFieldName;
	}

	public String getQueryDepNo() {
		return queryDepNo;
	}

	public void setQueryDepNo(String queryDepNo) {
		this.queryDepNo = queryDepNo;
	}

}
