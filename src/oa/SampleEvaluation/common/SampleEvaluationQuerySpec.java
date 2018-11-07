package oa.SampleEvaluation.common;

public class SampleEvaluationQuerySpec extends QuerySpec {
	String queryStatusTpFieldName;
	String queryStatusCheckFieldName;
	String queryStatusCheck;
	String queryStatusTp;

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
