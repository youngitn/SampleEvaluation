package oa.SampleEvaluationTp.service;

import jcx.db.talk;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationTpService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationTpService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation tp service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationTpService(talk t) {
		this.clazz = SampleEvaluationTpPO.class;
		this.t = t;
	}
}
