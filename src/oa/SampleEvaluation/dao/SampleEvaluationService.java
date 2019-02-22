package oa.SampleEvaluation.dao;

import jcx.db.talk;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.global.BaseDao;

public class SampleEvaluationService extends BaseDao {
	public SampleEvaluationService(talk t) {
		this.clazz = SampleEvaluation.class;
		this.t = t;
	}
}
