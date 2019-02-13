package oa.SampleEvaluation.query;

import oa.SampleEvaluation.common.global.xmaker;

public class QueryConditionDto {

	@xmaker(name = "Q_PNO", adapDbFieldName = "PNO")
	private String Q_PNO;
	@xmaker(name = "Q_EMPID", adapDbFieldName = "APPLICANT")
	private String Q_EMPID;
	@xmaker(name = "Q_SDATE", adapDbFieldName = "APP_DATE", isDateStart = true)
	private String Q_SDATE;
	@xmaker(name = "Q_EDATE", adapDbFieldName = "APP_DATE", isDateEnd = true)
	private String Q_EDATE;
	@xmaker(name = "Q_URGENCY", adapDbFieldName = "URGENCY")
	private String Q_URGENCY;
	@xmaker(name = "Q_SAP_CODE", adapDbFieldName = "SAP_CODE")
	private String Q_SAP_CODE;
	@xmaker(name = "Q_MATERIAL", adapDbFieldName = "MATERIAL")
	private String Q_MATERIAL;
	@xmaker(name = "Q_MFR", adapDbFieldName = "MFR")
	private String Q_MFR;
	@xmaker(name = "Q_STATUS", isFlowStatus = true)
	private String Q_STATUS;
	@xmaker(name = "Q_QR_NO", adapDbFieldName = "QR_NO")
	private String Q_QR_NO;
	@xmaker(name = "Q_RECEIPT_UNIT", adapDbFieldName = "RECEIPT_UNIT")
	private String Q_RECEIPT_UNIT;

	public String getQ_PNO() {
		return Q_PNO;
	}

	public void setQ_PNO(String q_PNO) {
		Q_PNO = q_PNO;
	}

	public String getQ_EMPID() {
		return Q_EMPID;
	}

	public void setQ_EMPID(String q_EMPID) {
		Q_EMPID = q_EMPID;
	}

	public String getQ_SDATE() {
		return Q_SDATE;
	}

	public void setQ_SDATE(String q_SDATE) {
		Q_SDATE = q_SDATE;
	}

	public String getQ_EDATE() {
		return Q_EDATE;
	}

	public void setQ_EDATE(String q_EDATE) {
		Q_EDATE = q_EDATE;
	}

	public String getQ_URGENCY() {
		return Q_URGENCY;
	}

	public void setQ_URGENCY(String q_URGENCY) {
		Q_URGENCY = q_URGENCY;
	}

	public String getQ_SAP_CODE() {
		return Q_SAP_CODE;
	}

	public void setQ_SAP_CODE(String q_SAP_CODE) {
		Q_SAP_CODE = q_SAP_CODE;
	}

	public String getQ_MATERIAL() {
		return Q_MATERIAL;
	}

	public void setQ_MATERIAL(String q_MATERIAL) {
		Q_MATERIAL = q_MATERIAL;
	}

	public String getQ_MFR() {
		return Q_MFR;
	}

	public void setQ_MFR(String q_MFR) {
		Q_MFR = q_MFR;
	}

	public String getQ_STATUS() {
		return Q_STATUS;
	}

	public void setQ_STATUS(String q_STATUS) {
		Q_STATUS = q_STATUS;
	}

	public String getQ_QR_NO() {
		return Q_QR_NO;
	}

	public void setQ_QR_NO(String q_QR_NO) {
		Q_QR_NO = q_QR_NO;
	}

	public String getQ_RECEIPT_UNIT() {
		return Q_RECEIPT_UNIT;
	}

	public void setQ_RECEIPT_UNIT(String q_RECEIPT_UNIT) {
		Q_RECEIPT_UNIT = q_RECEIPT_UNIT;
	}

}
