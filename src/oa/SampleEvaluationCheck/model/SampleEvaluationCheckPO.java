package oa.SampleEvaluationCheck.model;

import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * SampleEvaluation.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
@dbTable(name = "SAMPLE_EVALUATION_CHECK", pkName = "OWN_PNO")
public class SampleEvaluationCheckPO extends SampleEvaluationPO {
	
	/** ownPno. */
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	/**
	 * Instantiates a new sample evaluation check PO.
	 */
	public SampleEvaluationCheckPO() {

	}

	/**
	 * Builds the own pno.
	 *
	 * @param pno [String]
	 * @return  [String]
	 */
	protected String buildOwnPno(String pno) {

		return pno + "CHECK";
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