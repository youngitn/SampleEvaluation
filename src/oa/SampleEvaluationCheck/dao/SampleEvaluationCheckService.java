package oa.SampleEvaluationCheck.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;

public class SampleEvaluationCheckService extends BaseDao {
	public SampleEvaluationCheckService(talk t) {
		this.clazz = SampleEvaluationCheck.class;
		this.t = t;
	}
}
