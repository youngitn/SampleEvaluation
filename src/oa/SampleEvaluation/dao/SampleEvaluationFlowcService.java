package oa.SampleEvaluation.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.dto.SampleEvaluationFlowc;

public class SampleEvaluationFlowcService extends BaseDao {
	public SampleEvaluationFlowcService(talk t) {
		this.clazz = SampleEvaluationFlowc.class;
		this.t = t;
	}
}
