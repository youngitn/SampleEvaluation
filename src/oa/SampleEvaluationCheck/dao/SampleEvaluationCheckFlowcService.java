package oa.SampleEvaluationCheck.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;

public class SampleEvaluationCheckFlowcService extends BaseDao {

	public SampleEvaluationCheckFlowcService(talk t) {
		this.clazz = SampleEvaluationCheckFlowc.class;
		this.t = t;
	}

}
