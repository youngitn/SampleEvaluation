package oa.SampleEvaluationCheck.service;

import jcx.db.talk;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationCheckFlowcService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationCheckFlowcService extends BaseDao {

	/**
	 * Instantiates a new sample evaluation check flowc service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationCheckFlowcService(talk t) {
		this.clazz = SampleEvaluationCheckFlowcPO.class;
		this.t = t;
	}

}
