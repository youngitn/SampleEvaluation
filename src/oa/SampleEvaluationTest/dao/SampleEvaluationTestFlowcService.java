package oa.SampleEvaluationTest.dao;

import jcx.db.talk;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowc;
import oa.global.BaseDao;

public class SampleEvaluationTestFlowcService extends BaseDao {
	public SampleEvaluationTestFlowcService(talk t) {
		this.clazz = SampleEvaluationTestFlowc.class;
		this.t = t;
	}
}
