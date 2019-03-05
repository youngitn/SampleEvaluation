package oa.SampleEvaluationCheck.dto;

import java.io.Serializable;

import oa.SampleEvaluation.i.Flowc;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

@dbTable(name = "SAMPLE_EVALUATION_CHECK_FLOWC_HIS", pkName = "OWN_PNO")
public class SampleEvaluationCheckFlowcHis implements  Flowc,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@xmaker(name = "OWN_PNO")
	private String ownPno;
	@xmaker(name = "F_INP_STAT")
	private String fInpStat;
	@xmaker(name = "F_INP_ID")
	private String fInpId;
	@xmaker(name = "F_INP_TIME")
	private String fInpTime;
	@xmaker(name = "F_INP_INFO")
	private String fInpInfo;

	public String getOwnPno() {
		return ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
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
