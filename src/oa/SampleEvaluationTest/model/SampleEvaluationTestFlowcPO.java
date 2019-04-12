package oa.SampleEvaluationTest.model;

import java.io.Serializable;

import oa.SampleEvaluation.i.Flowc;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * The Class SampleEvaluationTestFlowcPO.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
@dbTable(name = "SAMPLE_EVALUATION_TEST_FLOWC", pkName = "OWN_PNO")
public class SampleEvaluationTestFlowcPO implements Flowc, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The own pno. */
	@xmaker(name = "OWN_PNO")
	private String ownPno;
	
	/** The inp stat. */
	@xmaker(name = "F_INP_STAT")
	private String fInpStat;
	
	/** The inp id. */
	@xmaker(name = "F_INP_ID")
	private String fInpId;
	
	/** The inp time. */
	@xmaker(name = "F_INP_TIME")
	private String fInpTime;
	
	/** The inp info. */
	@xmaker(name = "F_INP_INFO")
	private String fInpInfo;

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#getOwnPno()
	 */
	public String getOwnPno() {
		return ownPno;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#setOwnPno(java.lang.String)
	 */
	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#getfInpStat()
	 */
	public String getfInpStat() {
		return fInpStat;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#setfInpStat(java.lang.String)
	 */
	public void setfInpStat(String fInpStat) {
		this.fInpStat = fInpStat;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#getfInpId()
	 */
	public String getfInpId() {
		return fInpId;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#setfInpId(java.lang.String)
	 */
	public void setfInpId(String fInpId) {
		this.fInpId = fInpId;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#getfInpTime()
	 */
	public String getfInpTime() {
		return fInpTime;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#setfInpTime(java.lang.String)
	 */
	public void setfInpTime(String fInpTime) {
		this.fInpTime = fInpTime;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#getfInpInfo()
	 */
	public String getfInpInfo() {
		return fInpInfo;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.i.Flowc#setfInpInfo(java.lang.String)
	 */
	public void setfInpInfo(String fInpInfo) {
		this.fInpInfo = fInpInfo;
	}
}
