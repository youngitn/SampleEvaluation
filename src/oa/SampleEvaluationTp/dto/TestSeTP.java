package oa.SampleEvaluationTp.dto;

import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.global.dbTable;
import oa.global.xmaker;

@dbTable(name = "SAMPLE_EVALUATION_TP", pkName = "OWN_PNO")
public class TestSeTP extends SampleEvaluation {
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
