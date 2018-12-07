package oa.SampleEvaluation.dto;

import java.io.Serializable;

import oa.SampleEvaluation.common.global.dbTable;
import oa.SampleEvaluation.common.global.xmaker;

@dbTable(name = "SAMPLE_EVALUATION_FLOWC", pkName = "PNO")
public class SampleEvaluationFlowc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@xmaker(name = "PNO")
	private String pno;
	@xmaker(name = "F_INP_STAT")
	private String fInpStat;
	@xmaker(name = "F_INP_ID")
	private String fInpId;
	@xmaker(name = "F_INP_TIME")
	private String fInpTime;
	@xmaker(name = "F_INP_INFO")
	private String fInpInfo;

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getfInpStat() {
		return fInpStat;
	}

	public void setfInpStat(String fInpStat) {
		this.fInpStat = fInpStat;
	}

	public String getfInpId() {
		return fInpId;
	}

	public void setfInpId(String fInpId) {
		this.fInpId = fInpId;
	}

	public String getfInpTime() {
		return fInpTime;
	}

	public void setfInpTime(String fInpTime) {
		this.fInpTime = fInpTime;
	}

	public String getfInpInfo() {
		return fInpInfo;
	}

	public void setfInpInfo(String fInpInfo) {
		this.fInpInfo = fInpInfo;
	}
}
