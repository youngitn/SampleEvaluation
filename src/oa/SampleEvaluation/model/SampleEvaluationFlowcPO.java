package oa.SampleEvaluation.model;

import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * The Class SampleEvaluationFlowcPO.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
@dbTable(name = "SAMPLE_EVALUATION_FLOWC", pkName = "PNO")
public class SampleEvaluationFlowcPO  {

	/** The pno. */
	@xmaker(name = "PNO")
	private String pno;
	
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

	/**
	 * Gets the Pno.
	 *
	 * @return [String]
	 */
	public String getPno() {
		return pno;
	}

	/**
	 * Sets the Pno.
	 *
	 * @param pno  void
	 */
	public void setPno(String pno) {
		this.pno = pno;
	}

	/**
	 * Gets the fInpStat.
	 *
	 * @return [String]
	 */
	public String getfInpStat() {
		return fInpStat;
	}

	/**
	 * Sets the fInpStat.
	 *
	 * @param fInpStat  void
	 */
	public void setfInpStat(String fInpStat) {
		this.fInpStat = fInpStat;
	}

	/**
	 * Gets the fInpId.
	 *
	 * @return [String]
	 */
	public String getfInpId() {
		return fInpId;
	}

	/**
	 * Sets the fInpId.
	 *
	 * @param fInpId  void
	 */
	public void setfInpId(String fInpId) {
		this.fInpId = fInpId;
	}

	/**
	 * Gets the fInpTime.
	 *
	 * @return [String]
	 */
	public String getfInpTime() {
		return fInpTime;
	}

	/**
	 * Sets the fInpTime.
	 *
	 * @param fInpTime  void
	 */
	public void setfInpTime(String fInpTime) {
		this.fInpTime = fInpTime;
	}

	/**
	 * Gets the fInpInfo.
	 *
	 * @return [String]
	 */
	public String getfInpInfo() {
		return fInpInfo;
	}

	/**
	 * Sets the fInpInfo.
	 *
	 * @param fInpInfo  void
	 */
	public void setfInpInfo(String fInpInfo) {
		this.fInpInfo = fInpInfo;
	}
}
