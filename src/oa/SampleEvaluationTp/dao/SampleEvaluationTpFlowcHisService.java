package oa.SampleEvaluationTp.dao;

import jcx.db.talk;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowcHis;
import oa.global.BaseDao;

public class SampleEvaluationTpFlowcHisService extends BaseDao {
	public SampleEvaluationTpFlowcHisService(talk t) {
		this.clazz = SampleEvaluationTpFlowcHis.class;
		this.t = t;
	}
}
