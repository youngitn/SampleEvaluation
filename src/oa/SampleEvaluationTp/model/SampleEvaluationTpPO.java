package oa.SampleEvaluationTp.model;

import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * ¦PSampleEvaluation
 * 
 */
@dbTable(name = "SAMPLE_EVALUATION_TP", pkName = "OWN_PNO")
public class SampleEvaluationTpPO extends SampleEvaluationPO {
	private static final long serialVersionUID = 42L;
	/**
	 * ownPno
	 */
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	public SampleEvaluationTpPO() {

	}

	protected String buildOwnPno(String pno) {

		return pno + "TP";
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