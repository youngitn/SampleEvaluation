package oa.SampleEvaluationTp.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class SampleEvaluationTpService extends BaseDao {
	public SampleEvaluationTpService(talk t) {
		this.clazz = SampleEvaluationTp.class;
		this.t = t;
	}
}
