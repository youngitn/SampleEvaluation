package oa.SampleEvaluationTp.service;

import jcx.db.talk;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.global.BaseDao;

public class SampleEvaluationTpService extends BaseDao {
	public SampleEvaluationTpService(talk t) {
		this.clazz = SampleEvaluationTpPO.class;
		this.t = t;
	}
}
