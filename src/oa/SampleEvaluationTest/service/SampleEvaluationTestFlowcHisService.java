package oa.SampleEvaluationTest.service;

import jcx.db.talk;
import oa.SampleEvaluationTest.model.SampleEvaluationTestFlowcHisPO;
import oa.global.BaseDao;

public class SampleEvaluationTestFlowcHisService extends BaseDao {
	public SampleEvaluationTestFlowcHisService(talk t) {
		this.clazz = SampleEvaluationTestFlowcHisPO.class;
		this.t = t;
	}
}
