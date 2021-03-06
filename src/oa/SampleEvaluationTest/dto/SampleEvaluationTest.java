package oa.SampleEvaluationTest.dto;

import oa.SampleEvaluation.common.global.dbTable;
import oa.SampleEvaluation.common.global.xmaker;
import oa.SampleEvaluation.dto.SampleEvaluation;

/**
 * ¦PSampleEvaluation
 * 
 */
@dbTable(name = "SAMPLE_EVALUATION_TEST", pkName = "OWN_PNO")
public class SampleEvaluationTest extends SampleEvaluation {
	private static final long serialVersionUID = 42L;
	/**
	 * ownPno
	 */
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	protected String buildOwnPno(String pno) {

		return pno + "TEST";
	}

	public String getOwnPno() {
		return ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

}