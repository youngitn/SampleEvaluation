package oa.SampleEvaluationTest.service;

import jcx.db.talk;
import oa.SampleEvaluationTest.model.SampleEvaluationTestFlowcHisPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationTestFlowcHisService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationTestFlowcHisService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation test flowc his service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationTestFlowcHisService(talk t) {
		this.clazz = SampleEvaluationTestFlowcHisPO.class;
		this.t = t;
	}
}
