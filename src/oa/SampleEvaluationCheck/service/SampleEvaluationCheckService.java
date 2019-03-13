package oa.SampleEvaluationCheck.service;

import jcx.db.talk;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.global.BaseDao;

public class SampleEvaluationCheckService extends BaseDao {
	public SampleEvaluationCheckService(talk t) {
		this.clazz = SampleEvaluationCheckPO.class;
		this.t = t;
	}
}
