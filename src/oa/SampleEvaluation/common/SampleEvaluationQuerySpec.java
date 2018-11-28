package oa.SampleEvaluation.common;

import oa.SampleEvaluation.common.global.FinalString;
import oa.SampleEvaluation.common.global.QuerySpec;
import oa.SampleEvaluation.controller.HprocImpl;

public class SampleEvaluationQuerySpec extends QuerySpec {

	String queryStatusTpFieldName = "Q_STATUS_TP";
	String queryStatusCheckFieldName = "Q_STATUS_CHECK";;
	String queryStatusCheck;
	String queryStatusTp;
	String queryUrgencyFieldName = FinalString.QUERY_URGENCY_FIELD_NAME;
	String querySap_codeFieldName = FinalString.QUERY_SAP_CODE_FIELD_NAME;
	String queryMaterialFieldName = FinalString.QUERY_MATERIAL_FIELD_NAME;
	String queryMfrFieldName = FinalString.QUERY_MFR_FIELD_NAME;
	String queryUrgency;
	String querySapCode;
	String queryMaterial;
	String queryMfr;

	public SampleEvaluationQuerySpec(HprocImpl h) {
		super(h);
		this.setQueryStatusCheck(h.getValue(queryStatusCheckFieldName));
		this.setQueryStatusTp(h.getValue(queryStatusTpFieldName));
		this.setQueryUrgency(h.getValue(queryUrgencyFieldName));
		this.setQueryMaterial(h.getValue(queryMaterialFieldName));
		this.setQueryMfr(h.getValue(queryMfrFieldName));
		this.setQuerySapCode(h.getValue(querySap_codeFieldName));
	}

	public String getQueryStatusCheck() {
		return queryStatusCheck;
	}

	public void setQueryStatusCheck(String queryStatusCheck) {
		this.queryStatusCheck = queryStatusCheck;
	}

	public String getQueryStatusTp() {
		return queryStatusTp;
	}

	public void setQueryStatusTp(String queryStatusTp) {
		this.queryStatusTp = queryStatusTp;
	}

	public String getQueryStatusCheckFieldName() {
		return queryStatusCheckFieldName;
	}

	public void setQueryStatusCheckFieldName(String queryStatusCheckFieldName) {
		this.queryStatusCheckFieldName = queryStatusCheckFieldName;
	}

	public String getQueryStatusTpFieldName() {
		return queryStatusTpFieldName;

	}

	public void setQueryStatusTpFieldName(String queryStatusTpFieldName) {
		this.queryStatusTpFieldName = queryStatusTpFieldName;
	}

	public String getQueryUrgencyFieldName() {
		return queryUrgencyFieldName;
	}

	public void setQueryUrgencyFieldName(String queryUrgencyFieldName) {
		this.queryUrgencyFieldName = queryUrgencyFieldName;
	}

	public String getQuerySap_codeFieldName() {
		return querySap_codeFieldName;
	}

	public void setQuerySap_codeFieldName(String querySap_codeFieldName) {
		this.querySap_codeFieldName = querySap_codeFieldName;
	}

	public String getQueryMaterialFieldName() {
		return queryMaterialFieldName;
	}

	public void setQueryMaterialFieldName(String queryMaterialFieldName) {
		this.queryMaterialFieldName = queryMaterialFieldName;
	}

	public String getQueryMfrFieldName() {
		return queryMfrFieldName;
	}

	public void setQueryMfrFieldName(String queryMfrFieldName) {
		this.queryMfrFieldName = queryMfrFieldName;
	}

	public String getQueryUrgency() {
		return queryUrgency;
	}

	public void setQueryUrgency(String queryUrgency) {
		this.queryUrgency = queryUrgency;
	}

	public String getQuerySapCode() {
		return querySapCode;
	}

	public void setQuerySapCode(String querySapCode) {
		this.querySapCode = querySapCode;
	}

	public String getQueryMaterial() {
		return queryMaterial;
	}

	public void setQueryMaterial(String queryMaterial) {
		this.queryMaterial = queryMaterial;
	}

	public String getQueryMfr() {
		return queryMfr;
	}

	public void setQueryMfr(String queryMfr) {
		this.queryMfr = queryMfr;
	}
}
