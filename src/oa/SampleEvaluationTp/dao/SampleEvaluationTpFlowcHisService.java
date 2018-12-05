package oa.SampleEvaluationTp.dao;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowcHis;

public class SampleEvaluationTpFlowcHisService extends BaseDao {
	public SampleEvaluationTpFlowcHisService(talk t) {
		this.clazz = SampleEvaluationTpFlowcHis.class;
		this.t = t;
	}
}
