package oa.SampleEvaluationTp.service;

import jcx.db.talk;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcPO;
import oa.global.BaseDao;

/**
 * The Class SampleEvaluationTpFlowcService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class SampleEvaluationTpFlowcService extends BaseDao {
	
	/**
	 * Instantiates a new sample evaluation tp flowc service.
	 *
	 * @param t [talk]
	 */
	public SampleEvaluationTpFlowcService(talk t) {
		this.clazz = SampleEvaluationTpFlowcPO.class;
		this.t = t;
	}
}
