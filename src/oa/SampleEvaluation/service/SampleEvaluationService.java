package oa.SampleEvaluation.service;

import jcx.db.talk;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationService(talk t) {
		this.clazz = SampleEvaluationPO.class;
		this.t = t;
	}
}
