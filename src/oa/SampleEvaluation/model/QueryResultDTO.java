package oa.SampleEvaluation.model;

import java.io.Serializable;

import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * 查詢結果欄位定義
 * 
 * 須對應emaker表單查詢結果顯示表格.
 * 
 * @dbTable(name = "資料表名稱", pkName = "PK名稱")
 * @xmaker(name = "資料表欄位名稱",isText = 是否將NAME值作為字串直接顯示)
 * @author u52116
 *
 */
@dbTable(name = "SAMPLE_EVALUATION", pkName = "PNO")
public class QueryResultDTO {
	/**
	 * 
	 */

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

	@xmaker(name = "逾期天數", isText = true)
	private String numberOfOverdueDays;

	@xmaker(name = "簽核狀態", isFlowStatus = true)
	private String text;

	@xmaker(name = "明細", isText = true)
	private String detail;

	@xmaker(name = "簽核紀錄", isText = true)
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
