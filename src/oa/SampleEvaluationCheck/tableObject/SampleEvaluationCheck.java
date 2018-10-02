package oa.SampleEvaluationCheck.tableObject;

import java.io.Serializable;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.tableObject.SampleEvaluation;

/**
 * SampleEvaluation
 * 
 */
public class SampleEvaluationCheck extends SampleEvaluation implements Serializable {
	private static final long serialVersionUID = 42L;

	/**
	 * ownPno
	 */
	private String ownPno;

	private BaseService service;

	private String docCtrler;

	public SampleEvaluationCheck(String[] strings) {
		super(strings);
		this.ownPno = strings[28];
	}

	public SampleEvaluationCheck() {

	}

	public SampleEvaluationCheck(BaseService service) {
		this.service = service;
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

	public SampleEvaluationCheck setAllValue(SampleEvaluationCheck s) {
		s.setAppType(service.getValue("APP_TYPE"));
		s.setUrgency(service.getValue("URGENCY"));
		s.setMaterial(service.getValue("MATERIAL"));
		s.setSapCode(service.getValue("SAP_CODE"));
		s.setAbCode(service.getValue("AB_CODE"));
		s.setMfgLotNo(service.getValue("MFG_LOT_NO"));
		s.setQty(service.getValue("QTY"));
		s.setPack(service.getValue("PACK"));
		s.setUnit(service.getValue("UNIT"));
		s.setMfr(service.getValue("MFR"));
		s.setSupplier(service.getValue("SUPPLIER"));
		s.setProvideCoa(service.getValue("PROVIDE_COA"));
		s.setProvideSpec(service.getValue("PROVIDE_SPEC"));
		s.setProvideTestMethod(service.getValue("PROVIDE_TEST_METHOD"));
		s.setProvideSds(service.getValue("PROVIDE_SDS"));
		s.setProvideOthers(service.getValue("PROVIDE_OTHERS"));
		s.setNote(service.getValue("NOTE"));
		s.setApplicant(service.getValue("APPLICANT"));
		s.setAppDate(service.getValue("APP_DATE"));
		s.setReceiptUnit(service.getValue("RECEIPT_UNIT"));
		s.setProjectCode(service.getValue("PROJECT_CODE"));
		s.setProjectLeader(service.getValue("PROJECT_LEADER"));
		s.setNotifyNoCheck(service.getValue("NOTIFY_NO_CHECK"));
		s.setNotifyNoTrialProd(service.getValue("NOTIFY_NO_TRIAL_PROD"));
		s.setQrNo(service.getValue("QR_NO"));
		s.setIsCheck(service.getValue("IS_CHECK"));
		s.setIsTrialProduction(service.getValue("IS_TRIAL_PRODUCTION"));
		s.setLabExe(service.getValue("LAB_EXE").trim());
		s.setAssessor(service.getValue("ASSESSOR").trim());
		s.setDesignee(service.getValue("DESIGNEE").trim());
		s.setDocCtrler(service.getValue("DOC_CTRLER"));
		s.setPno(service.getValue("PNO"));
		// 子流程 ID = 表單單號+CHECK
		String ownPno = service.getValue("PNO") + "CHECK";
		// 為子流程主檔填入ID
		s.setOwnPno(ownPno);
		return s;
	}

}