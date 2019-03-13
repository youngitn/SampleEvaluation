package oa.SampleEvaluationCheck.service;

import jcx.db.talk;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcPO;
import oa.global.BaseDao;

public class SampleEvaluationCheckFlowcService extends BaseDao {

	public SampleEvaluationCheckFlowcService(talk t) {
		this.clazz = SampleEvaluationCheckFlowcPO.class;
		this.t = t;
	}

}
