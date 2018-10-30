package oa.SampleEvaluation.dto;

import java.io.Serializable;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.dto.SampleEvaluation;

/**
 * SampleEvaluation
 * 
 */
public abstract class SampleEvaluationSubBaseDto extends SampleEvaluation implements Serializable {
	private static final long serialVersionUID = 42L;

	/**
	 * ownPno
	 */
	protected String ownPno;

	private String docCtrler;

	public SampleEvaluationSubBaseDto(String[] strings) {
		super(strings);
		this.ownPno = strings[28];
	}

	public SampleEvaluationSubBaseDto() {

	}

	public SampleEvaluationSubBaseDto(BaseService service) {
		setAllValue(service);
	}

	public String getOwnPno() {
		return ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

	public void setDocCtrler(String docCtrler) {
		this.docCtrler = docCtrler;
	}

	public String getDocCtrler() {
		return docCtrler;
	}

	public void setAllValue(BaseService service) {
		this.setAppType(service.getValue("APP_TYPE"));
		this.setUrgency(service.getValue("URGENCY"));
		this.setMaterial(service.getValue("MATERIAL"));
		this.setSapCode(service.getValue("SAP_CODE"));
		this.setAbCode(service.getValue("AB_CODE"));
		this.setMfgLotNo(service.getValue("MFG_LOT_NO"));
		this.setQty(service.getValue("QTY"));
		this.setPack(service.getValue("PACK"));
		this.setUnit(service.getValue("UNIT"));
		this.setMfr(service.getValue("MFR"));
		this.setSupplier(service.getValue("SUPPLIER"));
		this.setProvideCoa(service.getValue("PROVIDE_COA"));
		this.setProvideSpec(service.getValue("PROVIDE_SPEC"));
		this.setProvideTestMethod(service.getValue("PROVIDE_TEST_METHOD"));
		this.setProvideSds(service.getValue("PROVIDE_SDS"));
		this.setProvideOthers(service.getValue("PROVIDE_OTHERS"));
		this.setNote(service.getValue("NOTE"));
		this.setApplicant(service.getValue("APPLICANT"));
		this.setAppDate(service.getValue("APP_DATE"));
		this.setReceiptUnit(service.getValue("RECEIPT_UNIT"));
		this.setProjectCode(service.getValue("PROJECT_CODE"));
		this.setProjectLeader(service.getValue("PROJECT_LEADER"));
		this.setNotifyNoCheck(service.getValue("NOTIFY_NO_CHECK"));
		this.setNotifyNoTrialProd(service.getValue("NOTIFY_NO_TRIAL_PROD"));
		this.setQrNo(service.getValue("QR_NO"));
		this.setIsCheck(service.getValue("IS_CHECK"));
		this.setIsTrialProduction(service.getValue("IS_TRIAL_PRODUCTION"));
		this.setLabExe(service.getValue("LAB_EXE").trim());
		this.setAssessor(service.getValue("ASSESSOR").trim());
		this.setDesignee(service.getValue("DESIGNEE").trim());
		this.setDocCtrler(service.getValue("DOC_CTRLER"));
		this.setDocCtrler(service.getValue("EVALUATION_RESULT"));
		this.setPno(service.getValue("PNO"));
		// 子流程 ID = 表單單號+TP
		String ownPno = buildOwnPno(service.getValue("PNO"));
		// 為子流程主檔填入ID
		this.setOwnPno(ownPno);

	}

	protected abstract String buildOwnPno(String pno);

}