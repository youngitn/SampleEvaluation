package oa.SampleEvaluation.dao;

import jcx.db.talk;
import oa.SampleEvaluation.dto.SampleEvaluationFlowc;
import oa.global.BaseDao;

public class SampleEvaluationFlowcService extends BaseDao {
	public SampleEvaluationFlowcService(talk t) {
		this.clazz = SampleEvaluationFlowc.class;
		this.t = t;
	}
}
