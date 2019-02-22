package oa.SampleEvaluationTest.dao;

import jcx.db.talk;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowcHis;
import oa.global.BaseDao;

public class SampleEvaluationTestFlowcHisService extends BaseDao {
	public SampleEvaluationTestFlowcHisService(talk t) {
		this.clazz = SampleEvaluationTestFlowcHis.class;
		this.t = t;
	}
}
