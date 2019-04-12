package oa.SampleEvaluation.service;

import jcx.db.talk;
import oa.SampleEvaluation.model.SampleEvaluationFlowcPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationFlowcService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationFlowcService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation flowc service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationFlowcService(talk t) {
		this.clazz = SampleEvaluationFlowcPO.class;
		this.t = t;
	}
}
