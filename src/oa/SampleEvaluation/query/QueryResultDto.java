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

	@xmaker(name = "¹O´Á¤Ñ¼Æ", isText = true)
	private String numberOfOverdueDays;

	@xmaker(name = "Ã±®Öª¬ºA", isFlowStatus = true)
	private String text;

	@xmaker(name = "©ú²Ó", isText = true)
	private String detail;

	@xmaker(name = "Ã±®Ö¬ö¿ý", isText = true)
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
