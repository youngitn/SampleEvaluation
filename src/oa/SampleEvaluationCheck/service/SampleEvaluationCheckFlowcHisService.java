package oa.SampleEvaluationCheck.service;

import jcx.db.talk;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcHisPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationCheckFlowcHisService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationCheckFlowcHisService extends BaseDao {

	/**
	 * Instantiates a new sample evaluation check flowc his service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationCheckFlowcHisService(talk t) {
		this.clazz = SampleEvaluationCheckFlowcHisPO.class;
		this.t = t;
	}

}
