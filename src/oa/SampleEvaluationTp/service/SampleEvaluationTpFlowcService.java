package oa.SampleEvaluationTp.service;

import jcx.db.talk;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcPO;
import oa.global.BaseDao;

public class SampleEvaluationTpFlowcService extends BaseDao {
	public SampleEvaluationTpFlowcService(talk t) {
		this.clazz = SampleEvaluationTpFlowcPO.class;
		this.t = t;
	}
}
