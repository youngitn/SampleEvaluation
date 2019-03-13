package oa.SampleEvaluation.service;

import jcx.db.talk;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.BaseDao;

public class SampleEvaluationService extends BaseDao {
	public SampleEvaluationService(talk t) {
		this.clazz = SampleEvaluationPO.class;
		this.t = t;
	}
}
