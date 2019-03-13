package oa.SampleEvaluationCheck.model;

import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * SampleEvaluation
 * 
 */
@dbTable(name = "SAMPLE_EVALUATION_CHECK", pkName = "OWN_PNO")
public class SampleEvaluationCheckPO extends SampleEvaluationPO {
	private static final long serialVersionUID = 42L;
	/**
	 * ownPno
	 */
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	public SampleEvaluationCheckPO() {

	}

	protected String buildOwnPno(String pno) {

		return pno + "CHECK";
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