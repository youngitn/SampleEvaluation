package oa.SampleEvaluationTest.service;

import jcx.db.talk;
import oa.SampleEvaluationTest.model.SampleEvaluationTestFlowcPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationTestFlowcService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationTestFlowcService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation test flowc service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationTestFlowcService(talk t) {
		this.clazz = SampleEvaluationTestFlowcPO.class;
		this.t = t;
	}
}
