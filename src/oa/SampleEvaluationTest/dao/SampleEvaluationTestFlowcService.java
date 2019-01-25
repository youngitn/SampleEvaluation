package oa.SampleEvaluationTest.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowc;

public class SampleEvaluationTestFlowcService extends BaseDao {
	public SampleEvaluationTestFlowcService(talk t) {
		this.clazz = SampleEvaluationTestFlowc.class;
		this.t = t;
	}
}
