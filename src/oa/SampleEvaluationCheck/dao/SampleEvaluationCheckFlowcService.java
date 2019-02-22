package oa.SampleEvaluationCheck.dao;

import jcx.db.talk;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.global.BaseDao;

public class SampleEvaluationCheckFlowcService extends BaseDao {

	public SampleEvaluationCheckFlowcService(talk t) {
		this.clazz = SampleEvaluationCheckFlowc.class;
		this.t = t;
	}

}
