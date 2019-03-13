package oa.SampleEvaluationTest.service;

import jcx.db.talk;
import oa.SampleEvaluationTest.model.SampleEvaluationTestPO;
import oa.global.BaseDao;

public class SampleEvaluationTestService extends BaseDao {
	public SampleEvaluationTestService(talk t) {
		this.clazz = SampleEvaluationTestPO.class;
		this.t = t;
	}
}
