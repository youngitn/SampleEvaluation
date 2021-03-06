package oa.SampleEvaluationCheck.dto;

import oa.SampleEvaluation.common.global.dbTable;
import oa.SampleEvaluation.common.global.xmaker;
import oa.SampleEvaluation.dto.SampleEvaluation;

/**
 * SampleEvaluation
 * 
 */
@dbTable(name = "SAMPLE_EVALUATION_CHECK", pkName = "OWN_PNO")
public class SampleEvaluationCheck extends SampleEvaluation {
	private static final long serialVersionUID = 42L;
	/**
	 * ownPno
	 */
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	public SampleEvaluationCheck() {

	}

	protected String buildOwnPno(String pno) {

		return pno + "CHECK";
	}

	public String getOwnPno() {
		return ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

}