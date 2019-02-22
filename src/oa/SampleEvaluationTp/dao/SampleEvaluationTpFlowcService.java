package oa.SampleEvaluationTp.dao;

import jcx.db.talk;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowc;
import oa.global.BaseDao;

public class SampleEvaluationTpFlowcService extends BaseDao {
	public SampleEvaluationTpFlowcService(talk t) {
		this.clazz = SampleEvaluationTpFlowc.class;
		this.t = t;
	}
}
