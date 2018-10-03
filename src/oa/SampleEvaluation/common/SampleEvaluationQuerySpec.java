package oa.SampleEvaluation.common;

public class SampleEvaluationQuerySpec extends QuerySpec {
	String queryStatusCheckFieldName;
	String queryStatusCheck;

	public String getQueryStatusCheck() {
		return queryStatusCheck;
	}

	public void setQueryStatusCheck(String queryStatusCheck) {
		this.queryStatusCheck = queryStatusCheck;
	}

	public String getQueryStatusCheckFieldName() {
		return queryStatusCheckFieldName;
	}

	public void setQueryStatusCheckFieldName(String queryStatusCheckFieldName) {
		this.queryStatusCheckFieldName = queryStatusCheckFieldName;
	}
}
