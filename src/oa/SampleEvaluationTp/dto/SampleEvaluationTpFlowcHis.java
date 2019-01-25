package oa.SampleEvaluationTp.dto;

import java.io.Serializable;

import oa.SampleEvaluation.common.Flowc;
import oa.SampleEvaluation.common.global.dbTable;
import oa.SampleEvaluation.common.global.xmaker;

/**
 * his的pk默認一個
 * 
 * @author u52116
 *
 */
@dbTable(name = "SAMPLE_EVALUATION_TP_FLOWC_HIS", pkName = "OWN_PNO")
public class SampleEvaluationTpFlowcHis implements  Flowc,Serializable {
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
