package oa.SampleEvaluationTp.dao;

import jcx.db.talk;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import oa.global.BaseDao;

public class SampleEvaluationTpService extends BaseDao {
	public SampleEvaluationTpService(talk t) {
		this.clazz = SampleEvaluationTp.class;
		this.t = t;
	}
}
