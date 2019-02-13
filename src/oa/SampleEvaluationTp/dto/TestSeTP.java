package oa.SampleEvaluationTp.dto;

import oa.SampleEvaluation.common.global.dbTable;
import oa.SampleEvaluation.common.global.xmaker;
import oa.SampleEvaluation.dto.SampleEvaluation;

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
