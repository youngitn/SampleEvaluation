package oa.SampleEvaluation.common.global;

import java.util.List;

import oa.SampleEvaluation.controller.HprocImpl;

public abstract class QuerySpec {

	String queryBillId;// �d�߳渹value
	String queryEmpid;// �d�ߥӽФHvalue
	String queryReqSDate;// �d�߰_��value
	String queryReqEDate;// �d�ߨ���value
	String queryStatus;// �d��ñ�֪��Avalue
	String queryDepNo;// �d�߳�츹�Xvalue
	// �H�U�ܼƥi�۳],���HQueryFieldNameFinalString���D
	String queryBillIdFieldName = FinalString.QUERY_BILL_ID_FIELD_NAME;// �d�߳渹���W��
	String queryEmpidFieldName = FinalString.QUERY_EMPID_FIELD_NAME;// �d�ߥӽФH���W��
	String queryReqSDateFieldName = FinalString.QUERY_REQ_SDATE_FIELD_NAME;// �d�߰_�����W��
	String queryReqEDateFieldName = FinalString.QUERY_REQ_EDATE_FIELD_NAME;// �d�ߨ������W��
	String queryStatusFieldName = FinalString.QUERY_STATUS_FIELD_NAME;// �d��ñ�֪��A���W��
	String queryDepNoFieldName = FinalString.QUERY_DEP_NO_FIELD_NAME;// �d�߳�츹�X���W��

	List<String> queryResultView;// Dmaker����������W��arraylist,�W�٬�����SQL�d�ߪ�DB���W��

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