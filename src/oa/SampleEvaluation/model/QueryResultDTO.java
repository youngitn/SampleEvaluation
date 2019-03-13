package oa.SampleEvaluation.model;

import java.io.Serializable;

import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * �d�ߵ��G���w�q
 * 
 * ������emaker���d�ߵ��G��ܪ��.
 * 
 * @dbTable(name = "��ƪ�W��", pkName = "PK�W��")
 * @xmaker(name = "��ƪ����W��",isText = �O�_�NNAME�ȧ@���r�ꪽ�����)
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
