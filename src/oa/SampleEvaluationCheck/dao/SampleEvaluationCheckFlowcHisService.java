package oa.SampleEvaluationCheck.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowcHis;

public class SampleEvaluationCheckFlowcHisService extends BaseDao {

	public SampleEvaluationCheckFlowcHisService(talk t) {
		this.clazz = SampleEvaluationCheckFlowcHis.class;
		this.t = t;
	}

}
