package oa.SampleEvaluationTest.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationTest.dto.SampleEvaluationTest;

public class SampleEvaluationTestService extends BaseDao {
	public SampleEvaluationTestService(talk t) {
		this.clazz = SampleEvaluationTest.class;
		this.t = t;
	}
}
