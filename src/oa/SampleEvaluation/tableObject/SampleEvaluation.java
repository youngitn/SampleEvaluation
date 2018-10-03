package oa.SampleEvaluation.tableObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ysp.service.BaseService;

/**
 * SampleEvaluation
 * 
 * 
 */
public class SampleEvaluation implements Serializable {
	private static final long serialVersionUID = 42L;

	/**
	 * pno
	 */
	private String pno;

	/**
	 * app_type
	 */
	private String appType;

	/**
	 * urgency
	 */
	private String urgency;

	/**
	 * material
	 */
	private String material;

	/**
	 * sap_code
	 */
	private String sapCode;

	/**
	 * ab_code
	 */
	private String abCode;

	/**
	 * mfg_lot_no
	 */
	private String mfgLotNo;

	/**
	 * qty
	 */
	private String qty;

	/**
	 * pack
	 */
	private String pack;

	/**
	 * unit
	 */
	private String unit;

	/**
	 * mfr
	 */
	private String mfr;

	/**
	 * supplier
	 */
	private String supplier;

	/**
	 * provide_coa
	 */
	private String provideCoa;

	/**
	 * provide_spec
	 */
	private String provideSpec;

	/**
	 * provide_test_method
	 */
	private String provideTestMethod;

	/**
	 * provide_sds
	 */
	private String provideSds;

	/**
	 * provide_others
	 */
	private String provideOthers;

	/**
	 * note
	 */
	private String note;

	/**
	 * applicant
	 */
	private String applicant;

	/**
	 * app_date
	 */
	private String appDate;

	/**
	 * receipt_unit
	 */
	private String receiptUnit;

	/**
	 * project_code
	 */
	private String projectCode;

	/**
	 * project_leader
	 */
	private String projectLeader;

	/**
	 * notify_no_check
	 */
	private String notifyNoCheck;

	/**
	 * notify_no_trial_prod
	 */
	private String notifyNoTrialProd;

	/**
	 * qr_no
	 */
	private String qrNo;

	/**
	 * is_check
	 */
	private String isCheck;

	/**
	 * is_trial_production
	 */
	private String isTrialProduction;

	private String assessor;

	private String designee;

	private String labExe;

	private String docCtrler;

	public SampleEvaluation() {

	}

	public SampleEvaluation(String[] strings) {
		super();

		this.pno = strings[0];
		this.appType = strings[1];
		this.urgency = strings[2];
		this.material = strings[3];
		this.sapCode = strings[4];
		this.abCode = strings[5];
		this.mfgLotNo = strings[6];
		this.qty = strings[7];
		this.pack = strings[8];
		this.unit = strings[9];
		this.mfr = strings[10];
		this.supplier = strings[11];
		this.provideCoa = strings[12];
		this.provideSpec = strings[13];
		this.provideTestMethod = strings[14];
		this.provideSds = strings[15];
		this.provideOthers = strings[16];
		this.note = strings[17];
		this.applicant = strings[18];
		this.appDate = strings[19];
		this.receiptUnit = strings[20];
		this.projectCode = strings[21];
		this.projectLeader = strings[22];
		this.notifyNoCheck = strings[23];
		this.notifyNoTrialProd = strings[24];
		this.qrNo = strings[25];
		this.isCheck = strings[26];
		this.isTrialProduction = strings[27];
		this.labExe = strings[28];
		this.assessor = strings[29];
		this.designee = strings[30];
		this.docCtrler = strings[31];
	}

	/**
	 * 取得表單欄位資料,並設置於SampleEvaluation物件中 <br>
	 * 因只有本方法會使用到service,所以使用時才導入service
	 * 
	 * @param s       SampleEvaluation<br>
	 * @param service BaseService<br>
	 * @return SampleEvaluation
	 */
	public SampleEvaluation setAllValue(SampleEvaluation s, BaseService service) {
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

		return s;
	}

	public void setDocCtrler(String value) {
		// TODO Auto-generated method stub
		this.docCtrler = value;
	}

	public String getDocCtrler() {
		// TODO Auto-generated method stub
		return docCtrler;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSapCode() {
		return sapCode;
	}

	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}

	public String getAbCode() {
		return abCode;
	}

	public void setAbCode(String abCode) {
		this.abCode = abCode;
	}

	public String getMfgLotNo() {
		return mfgLotNo;
	}

	public void setMfgLotNo(String mfgLotNo) {
		this.mfgLotNo = mfgLotNo;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMfr() {
		return mfr;
	}

	public void setMfr(String mfr) {
		this.mfr = mfr;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getProvideCoa() {
		return provideCoa;
	}

	public void setProvideCoa(String provideCoa) {
		this.provideCoa = provideCoa;
	}

	public String getProvideSpec() {
		return provideSpec;
	}

	public void setProvideSpec(String provideSpec) {
		this.provideSpec = provideSpec;
	}

	public String getProvideTestMethod() {
		return provideTestMethod;
	}

	public void setProvideTestMethod(String provideTestMethod) {
		this.provideTestMethod = provideTestMethod;
	}

	public String getProvideSds() {
		return provideSds;
	}

	public void setProvideSds(String provideSds) {
		this.provideSds = provideSds;
	}

	public String getProvideOthers() {
		return provideOthers;
	}

	public void setProvideOthers(String provideOthers) {
		this.provideOthers = provideOthers;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getReceiptUnit() {
		return receiptUnit;
	}

	public void setReceiptUnit(String receiptUnit) {
		this.receiptUnit = receiptUnit;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public String getNotifyNoCheck() {
		return notifyNoCheck;
	}

	public void setNotifyNoCheck(String notifyNoCheck) {
		this.notifyNoCheck = notifyNoCheck;
	}

	public String getNotifyNoTrialProd() {
		return notifyNoTrialProd;
	}

	public void setNotifyNoTrialProd(String notifyNoTrialProd) {
		this.notifyNoTrialProd = notifyNoTrialProd;
	}

	public String getQrNo() {
		return qrNo;
	}

	public void setQrNo(String qrNo) {
		this.qrNo = qrNo;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsTrialProduction() {
		return isTrialProduction;
	}

	public void setIsTrialProduction(String isTrialProduction) {
		this.isTrialProduction = isTrialProduction;
	}

	public String getDesignee() {
		return designee;
	}

	public void setDesignee(String designee) {
		this.designee = designee;
	}

	public String getAssessor() {
		return assessor;
	}

	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	public String getLabExe() {
		return labExe;
	}

	public void setLabExe(String labExe) {
		this.labExe = labExe;
	}
}