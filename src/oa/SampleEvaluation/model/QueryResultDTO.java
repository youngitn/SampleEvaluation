package oa.SampleEvaluation.model;

import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * �d�ߵ��G���w�q
 * 
 * ������emaker���d�ߵ��G��ܪ��.
 *
 * @author u52116
 * @dbTable(name = "��ƪ�W��", pkName = "PK�W��")
 * @xmaker(name = "��ƪ����W��",isText = �O�_�NNAME�ȧ@���r�ꪽ�����)
 */
@dbTable(name = "SAMPLE_EVALUATION", pkName = "PNO")
public class QueryResultDTO {

	/** The urgency. */
	@xmaker(name = "URGENCY")
	private String urgency;

	/** The pno. */

	@xmaker(name = "PNO")
	private String pno;

	/** �쪫�ƦW��. */
	@xmaker(name = "MATERIAL")
	private String material;

	/** SAP_CODE. */
	@xmaker(name = "SAP_CODE")
	private String sapCode;

	/** SUPPLIER */
	@xmaker(name = "SUPPLIER")
	private String supplier;
	
	/**�s�y��*/
	@xmaker(name = "MFR")
	private String mfr;
	
	/** The text. */
	@xmaker(name = "ñ�֪��A", isFlowStatus = true)
	private String text;

	/** The detail. */
	@xmaker(name = "����", isText = true)
	private String detail;

	/** The number of overdue days. */
	@xmaker(name = "�O���Ѽ�", isText = true)
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
	@xmaker(name = "ñ�֬���", isText = true)
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
