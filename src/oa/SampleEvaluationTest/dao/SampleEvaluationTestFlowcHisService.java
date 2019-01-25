package oa.SampleEvaluationTest.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowcHis;

public class SampleEvaluationTestFlowcHisService extends BaseDao {
	public SampleEvaluationTestFlowcHisService(talk t) {
		this.clazz = SampleEvaluationTestFlowcHis.class;
		this.t = t;
	}
}
