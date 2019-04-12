package oa.SampleEvaluationTest.service;

import jcx.db.talk;
import oa.SampleEvaluationTest.model.SampleEvaluationTestPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationTestService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationTestService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation test service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationTestService(talk t) {
		this.clazz = SampleEvaluationTestPO.class;
		this.t = t;
	}
}
