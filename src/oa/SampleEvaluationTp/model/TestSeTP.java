package oa.SampleEvaluationTp.model;

import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

@dbTable(name = "SAMPLE_EVALUATION_TP", pkName = "OWN_PNO")
public class TestSeTP extends SampleEvaluationPO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	public String getOwnPno() {
		return ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

}
