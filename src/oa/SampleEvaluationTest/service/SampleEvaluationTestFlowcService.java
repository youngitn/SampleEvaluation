package oa.SampleEvaluationTest.service;

import jcx.db.talk;
import oa.SampleEvaluationTest.model.SampleEvaluationTestFlowcPO;
import oa.global.BaseDao;

public class SampleEvaluationTestFlowcService extends BaseDao {
	public SampleEvaluationTestFlowcService(talk t) {
		this.clazz = SampleEvaluationTestFlowcPO.class;
		this.t = t;
	}
}
