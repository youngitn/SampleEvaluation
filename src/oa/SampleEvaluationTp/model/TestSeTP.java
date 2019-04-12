package oa.SampleEvaluationTp.model;

import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * The Class TestSeTP.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
@dbTable(name = "SAMPLE_EVALUATION_TP", pkName = "OWN_PNO")
public class TestSeTP extends SampleEvaluationPO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The own pno. */
	@xmaker(name = "OWN_PNO")
	protected String ownPno;

	/**
	 * Gets the OwnPno.
	 *
	 * @return [String]
	 */
	public String getOwnPno() {
		return ownPno;
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
