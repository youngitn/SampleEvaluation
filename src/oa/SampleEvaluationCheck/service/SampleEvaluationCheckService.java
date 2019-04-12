package oa.SampleEvaluationCheck.service;

import jcx.db.talk;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationCheckService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationCheckService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation check service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationCheckService(talk t) {
		this.clazz = SampleEvaluationCheckPO.class;
		this.t = t;
	}
}
