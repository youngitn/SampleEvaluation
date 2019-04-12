package oa.SampleEvaluation.model;

import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * 查詢結果欄位定義
 * 
 * 須對應emaker表單查詢結果顯示表格.
 *
 * @author u52116
 * @dbTable(name = "資料表名稱", pkName = "PK名稱")
 * @xmaker(name = "資料表欄位名稱",isText = 是否將NAME值作為字串直接顯示)
 */
@dbTable(name = "SAMPLE_EVALUATION", pkName = "PNO")
public class QueryResultDTO {

	/** The urgency. */
	@xmaker(name = "URGENCY")
	private String urgency;

	/** The pno. */

	@xmaker(name = "PNO")
	private String pno;

	/** 原物料名稱. */
	@xmaker(name = "MATERIAL")
	private String material;

	/** SAP_CODE. */
	@xmaker(name = "SAP_CODE")
	private String sapCode;

	/** SUPPLIER */
	@xmaker(name = "SUPPLIER")
	private String supplier;
	
	/**製造商*/
	@xmaker(name = "MFR")
	private String mfr;
	
	/** The text. */
	@xmaker(name = "簽核狀態", isFlowStatus = true)
	private String text;

	/** The detail. */
	@xmaker(name = "明細", isText = true)
	private String detail;

	/** The number of overdue days. */
	@xmaker(name = "逾期天數", isText = true)
	private String numberOfOverdueDays;
	
	/** The applicant. */
	@xmaker(name = "APPLICANT")
	private String applicant;
	
	/** The app date. */
	@xmaker(name = "APP_DATE")
	private String appDate;
	
	
	/** The app type. */
	@xmaker(name = "APP_TYPE")
	private String appType;


	/** The flow his. */
	@xmaker(name = "簽核紀錄", isText = true)
	private String flowHis;

	/** The urgencyType. */
	@xmaker(name = "URGENCY_TYPE")
	private String urgencyType;
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
	 * @param pno void
	 */
	public void setPno(String pno) {
		this.pno = pno;
	}

	/**
	 * Gets the AppDate.
	 *
	 * @return [String]
	 */
	public String getAppDate() {
		return appDate;
	}

	/**
	 * Sets the AppDate.
	 *
	 * @param appDate void
	 */
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

}
