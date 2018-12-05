package oa.SampleEvaluationTp.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowc;

public class SampleEvaluationTpFlowcService extends BaseDao {
	public SampleEvaluationTpFlowcService(talk t) {
		this.clazz = SampleEvaluationTpFlowc.class;
		this.t = t;
	}
}
