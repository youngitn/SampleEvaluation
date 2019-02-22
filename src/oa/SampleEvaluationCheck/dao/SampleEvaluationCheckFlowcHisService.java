package oa.SampleEvaluationCheck.dao;

import jcx.db.talk;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowcHis;
import oa.global.BaseDao;

public class SampleEvaluationCheckFlowcHisService extends BaseDao {

	public SampleEvaluationCheckFlowcHisService(talk t) {
		this.clazz = SampleEvaluationCheckFlowcHis.class;
		this.t = t;
	}

}
