package oa.SampleEvaluationTp.service;

import jcx.db.talk;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcHisPO;
import oa.global.BaseDao;

public class SampleEvaluationTpFlowcHisService extends BaseDao {
	public SampleEvaluationTpFlowcHisService(talk t) {
		this.clazz = SampleEvaluationTpFlowcHisPO.class;
		this.t = t;
	}
}
