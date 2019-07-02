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

	/** 製造商 */
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

	//10
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

	/** The M_CODE. */
	@xmaker(name = "M_CODE")
	private String mCode;

	/**
	 * 預計完成日 受理單位 申請人單位 QR編號 簽核狀態 製造商國別 製造商批號 製造商廠址 數量 單位
	 */
	/** The urgencyType. */
	@xmaker(name = "預計完成日", isText = true)
	private String dl;
	//17
	/** 受理單位. */
	@xmaker(name = "RECEIPT_UNIT")
	private String receiptUnit;

	//18
	/** The urgencyType. */
	@xmaker(name = "申請人單位", isText = true)
	private String appDept;
	//19
	/** The urgencyType. */
	@xmaker(name = "QR_NO")
	private String qrNo;
	//20
	/** The text. */
	@xmaker(name = "簽核狀態",  isText = true)
	private String text2;
	//21
	/** The urgencyType. */
	@xmaker(name = "MFR_COUNTRY")
	private String mfrCountry;
	//22
	/** The urgencyType. */
	@xmaker(name = "MFG_LOT_NO")
	private String mfgLotNo;
	//23
	/** The urgencyType. */
	@xmaker(name = "MFR_ADDRESS")
	private String mfrAddress;
	//24
	/** The urgencyType. */
	@xmaker(name = "QTY")
	private String qty;
	//25
	/** The urgencyType. */
	@xmaker(name = "UNIT")
	private String unit;
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
