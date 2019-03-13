package oa.SampleEvaluationCheck.service;

import jcx.db.talk;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcHisPO;
import oa.global.BaseDao;

public class SampleEvaluationCheckFlowcHisService extends BaseDao {

	public SampleEvaluationCheckFlowcHisService(talk t) {
		this.clazz = SampleEvaluationCheckFlowcHisPO.class;
		this.t = t;
	}

}
