package oa.SampleEvaluation.common;

import oa.SampleEvaluation.common.global.CommonDataObj;

public class SampleEvaluationDataObj extends CommonDataObj {
	SampleEvaluationQuerySpec seqs;

	public SampleEvaluationDataObj(jcx.db.talk t, String tableName, String tablePKName, String tableApplicantFieldName)
			throws Exception {
		super(t, tableName, tablePKName, tableApplicantFieldName);

	}

	public SampleEvaluationQuerySpec getQuerySpec() {
		return this.seqs;
	}

	public void setQuerySpec(SampleEvaluationQuerySpec qs) {

		this.seqs = qs;

	}

}
