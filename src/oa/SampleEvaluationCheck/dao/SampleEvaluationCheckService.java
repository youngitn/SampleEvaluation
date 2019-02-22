package oa.SampleEvaluationCheck.dao;

import jcx.db.talk;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.global.BaseDao;

public class SampleEvaluationCheckService extends BaseDao {
	public SampleEvaluationCheckService(talk t) {
		this.clazz = SampleEvaluationCheck.class;
		this.t = t;
	}
}
