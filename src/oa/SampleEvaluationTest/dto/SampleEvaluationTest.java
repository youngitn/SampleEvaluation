package oa.SampleEvaluationTest.dto;

import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

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
		if (this.ownPno.length() == 0) {
			return buildOwnPno(this.getPno());
		}
		return this.ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

}