package oa.SampleEvaluationTp.service;

import jcx.db.talk;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcHisPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationTpFlowcHisService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationTpFlowcHisService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation tp flowc his service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationTpFlowcHisService(talk t) {
		this.clazz = SampleEvaluationTpFlowcHisPO.class;
		this.t = t;
	}
}
