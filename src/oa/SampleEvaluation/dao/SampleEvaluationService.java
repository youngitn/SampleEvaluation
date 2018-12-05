package oa.SampleEvaluation.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.dto.SampleEvaluation;

public class SampleEvaluationService extends BaseDao {
	public SampleEvaluationService(talk t) {
		this.clazz = SampleEvaluation.class;
		this.t = t;
	}
}
