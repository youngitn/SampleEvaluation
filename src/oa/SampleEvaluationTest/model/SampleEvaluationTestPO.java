package oa.SampleEvaluationTest.model;

import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * ¦PSampleEvaluation.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
@dbTable(name = "SAMPLE_EVALUATION_TEST", pkName = "OWN_PNO")
public class SampleEvaluationTestPO extends SampleEvaluationPO {
	
	/** ownPno. */
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	/**
	 * Builds the own pno.
	 *
	 * @param pno [String]
	 * @return  [String]
	 */
	protected String buildOwnPno(String pno) {

		return pno + "TEST";
	}

	/**
	 * Gets the OwnPno.
	 *
	 * @return [String]
	 */
	public String getOwnPno() {
		if (this.ownPno.length() == 0) {
			return buildOwnPno(this.getPno());
		}
		return this.ownPno;
	}

	/**
	 * Sets the OwnPno.
	 *
	 * @param ownPno  void
	 */
	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

}