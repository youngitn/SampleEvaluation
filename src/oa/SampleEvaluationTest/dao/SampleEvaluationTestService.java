package oa.SampleEvaluationTest.dao;

import jcx.db.talk;
import oa.SampleEvaluationTest.dto.SampleEvaluationTest;
import oa.global.BaseDao;

public class SampleEvaluationTestService extends BaseDao {
	public SampleEvaluationTestService(talk t) {
		this.clazz = SampleEvaluationTest.class;
		this.t = t;
	}
}
