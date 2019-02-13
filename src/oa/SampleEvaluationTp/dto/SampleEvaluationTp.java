package oa.SampleEvaluationTp.dto;

import oa.SampleEvaluation.common.global.dbTable;
import oa.SampleEvaluation.common.global.xmaker;
import oa.SampleEvaluation.dto.SampleEvaluation;

/**
 * ¦PSampleEvaluation
 * 
 */
@dbTable(name = "SAMPLE_EVALUATION_TP", pkName = "OWN_PNO")
public class SampleEvaluationTp extends SampleEvaluation {
	private static final long serialVersionUID = 42L;
	/**
	 * ownPno
	 */
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	public SampleEvaluationTp() {

	}

	protected String buildOwnPno(String pno) {

		return pno + "TP";
	}

	public String getOwnPno() {
		return ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

}