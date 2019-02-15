package oa.SampleEvaluation.query;

import oa.SampleEvaluation.common.global.dbTable;
import oa.SampleEvaluation.common.global.xmaker;

@dbTable(name = "SAMPLE_EVALUATION", pkName = "PNO")
public class QueryResultDto {
	@xmaker(name = "PNO")
	private String pno;

	@xmaker(name = "APPLICANT")
	private String applicant;

	@xmaker(name = "APP_TYPE")
	private String appType;

	@xmaker(name = "URGENCY")
	private String urgency;

	@xmaker(name = "APP_DATE")
	private String appDate;

	@xmaker(name = "�O���Ѽ�", isText = true)
	private String numberOfOverdueDays;

	@xmaker(name = "ñ�֪��A", isFlowStatus = true)
	private String text;

	@xmaker(name = "����", isText = true)
	private String detail;

	@xmaker(name = "ñ�֬���", isText = true)
	private String flowHis;

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

}
