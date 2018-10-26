package oa.SampleEvaluation.common;

import java.util.ArrayList;

public abstract class QuerySpec {

	String queryBillId;
	String queryEmpid;
	String queryReqSDate;
	String queryReqEDate;
	String queryStatus;
	String queryDepNo;
	String queryBillIdFieldName;
	String queryEmpidFieldName;
	String queryReqSDateFieldName;
	String queryReqEDateFieldName;
	String queryStatusFieldName;
	String queryDepNoFieldName;
	ArrayList<String> QueryResultView;

	public ArrayList<String> getQueryResultView() {
		return QueryResultView;
	}

	public void setQueryResultView(ArrayList<String> queryResultView) {
		QueryResultView = queryResultView;
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
