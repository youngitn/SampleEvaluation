package oa.SampleEvaluation.service;

import jcx.db.talk;
import oa.SampleEvaluation.model.SampleEvaluationFlowcPO;
import oa.global.BaseDao;

public class SampleEvaluationFlowcService extends BaseDao {
	public SampleEvaluationFlowcService(talk t) {
		this.clazz = SampleEvaluationFlowcPO.class;
		this.t = t;
	}
}
