package oa.SampleEvaluation.common;

import oa.SampleEvaluation.common.global.QuerySpec;
import oa.SampleEvaluation.controller.HprocImpl;

public class SampleEvaluationQuerySpec extends QuerySpec {

	String queryStatusTpFieldName = "Q_STATUS_TP";
	String queryStatusCheckFieldName = "Q_STATUS_CHECK";;
	String queryStatusCheck;
	String queryStatusTp;

	public SampleEvaluationQuerySpec(HprocImpl h) {
		super(h);
		this.setQueryStatusCheck(h.getValue(queryStatusCheckFieldName));
		this.setQueryStatusTp(h.getValue(queryStatusTpFieldName));

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
}
