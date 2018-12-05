package oa.SampleEvaluation.dto;

import java.io.Serializable;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.common.global.dbTable;
import oa.SampleEvaluation.common.global.xmaker;

/**
 * SampleEvaluation
 * 
 * 
 */
@dbTable(name = "SAMPLE_EVALUATION", pkName = "PNO")
public class SampleEvaluation implements Serializable {
	// declare a new annotation

	private static final long serialVersionUID = 42L;
	@xmaker(name = "PNO")
	private String pno;
	@xmaker(name = "APP_TYPE")
	private String appType;
	@xmaker(name = "URGENCY")
	private String urgency;
	@xmaker(name = "MATERIAL")
	private String material;
	@xmaker(name = "SAP_CODE")
	private String sapCode;
	@xmaker(name = "AB_CODE")
	private String abCode;
	@xmaker(name = "MFG_LOT_NO")
	private String mfgLotNo;
	@xmaker(name = "QTY")
	private String qty = "0";
	@xmaker(name = "PACK")
	private String pack;
	@xmaker(name = "UNIT")
	private String unit;
	@xmaker(name = "MFR")
	private String mfr;
	@xmaker(name = "SUPPLIER")
	private String supplier;
	@xmaker(name = "PROVIDE_COA")
	private String provideCoa;
	@xmaker(name = "PROVIDE_SPEC")
	private String provideSpec;
	@xmaker(name = "PROVIDE_TEST_METHOD")
	private String provideTestMethod;
	@xmaker(name = "PROVIDE_SDS")
	private String provideSds;
	@xmaker(name = "PROVIDE_OTHERS")
	private String provideOthers;
	@xmaker(name = "NOTE")
	private String note;
	@xmaker(name = "APPLICANT")
	private String applicant;
	@xmaker(name = "APP_DATE")
	private String appDate;
	@xmaker(name = "RECEIPT_UNIT")
	private String receiptUnit;
	@xmaker(name = "PROJECT_CODE")
	private String projectCode;
	@xmaker(name = "PROJECT_LEADER")
	private String projectLeader;
	@xmaker(name = "NOTIFY_NO_CHECK")
	private String notifyNoCheck;
	@xmaker(name = "NOTIFY_NO_TRIAL_PROD")
	private String notifyNoTrialProd;
	@xmaker(name = "QR_NO")
	private String qrNo;
	@xmaker(name = "IS_CHECK")
	private String isCheck;
	@xmaker(name = "IS_TRIAL_PRODUCTION")
	private String isTrialProduction;
	@xmaker(name = "ASSESSOR")
	private String assessor;
	@xmaker(name = "DESIGNEE")
	private String designee;
	@xmaker(name = "LAB_EXE")
	private String labExe;
	@xmaker(name = "DOC_CTRLER")
	private String docCtrler;
	@xmaker(name = "EVALUATION_RESULT")
	private String evaluationResult;
	@xmaker(name = "FILE_SPEC")
	private String fileSpec;
	@xmaker(name = "FILE_COA")
	private String fileCoa;
	@xmaker(name = "FILE_TEST_METHOD")
	private String fileTestMethod;
	@xmaker(name = "FILE_OTHERS")
	private String fileOthers;
	@xmaker(name = "FILE_SDS")
	private String fileSds;
	@xmaker(name = "FILE_1")
	private String file1;
	@xmaker(name = "FILE_2")
	private String file2;
	@xmaker(name = "FILE_3")
	private String file3;
	@xmaker(name = "FILE_4")
	private String file4;
	@xmaker(name = "FILE_5")
	private String file5;
	@xmaker(name = "FILE_6")
	private String file6;
	@xmaker(name = "FILE_7")
	private String file7;
	@xmaker(name = "FILE_8")
	private String file8;
	@xmaker(name = "FILE_9")
	private String file9;
	@xmaker(name = "FILE_10")
	private String file10;
	@xmaker(name = "FILE_EVALUATION_RESULT")
	private String fileEvaluationResult;

	@xmaker(name = "APP_REASON")
	private String appReason;

	// "file_spec=?
	// ,file_coa=?,file_test_method=?,file_others=?,file_sds=?,file_1=?,file_2=?,file_3=?,file_4=?,file_5=?,file_6=?,file_7=?,file_8=?,file_9=?,file_10=?"

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
		this.evaluationResult = strings[32];
		this.fileSpec = strings[33];
		this.fileCoa = strings[34];
		this.fileTestMethod = strings[35];
		this.fileOthers = strings[36];
		this.fileSds = strings[37];
		this.file1 = strings[38];
		this.file2 = strings[39];
		this.file3 = strings[40];
		this.file4 = strings[41];
		this.file5 = strings[42];
		this.file6 = strings[43];
		this.file7 = strings[44];
		this.file8 = strings[45];
		this.file9 = strings[46];
		this.file10 = strings[47];
		this.fileEvaluationResult = strings[48];
		this.appReason = strings[49];
	}


	public void setDocCtrler(String value) {
		this.docCtrler = value;
	}

	public String getDocCtrler() {
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

	public String getEvaluationResult() {
		return evaluationResult;
	}

	public void setEvaluationResult(String evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	public String getFileSpec() {
		return fileSpec;
	}

	public void setFileSpec(String fileSpec) {
		this.fileSpec = fileSpec;
	}

	public String getFileCoa() {
		return fileCoa;
	}

	public void setFileCoa(String fileCoa) {
		this.fileCoa = fileCoa;
	}

	public String getFileTestMethod() {
		return fileTestMethod;
	}

	public void setFileTestMethod(String fileTestMethod) {
		this.fileTestMethod = fileTestMethod;
	}

	public String getFileOthers() {
		return fileOthers;
	}

	public void setFileOthers(String fileOthers) {
		this.fileOthers = fileOthers;
	}

	public String getFileSds() {
		return fileSds;
	}

	public void setFileSds(String fileSds) {
		this.fileSds = fileSds;
	}

	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}

	public String getFile2() {
		return file2;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	public String getFile3() {
		return file3;
	}

	public void setFile3(String file3) {
		this.file3 = file3;
	}

	public String getFile4() {
		return file4;
	}

	public void setFile4(String file4) {
		this.file4 = file4;
	}

	public String getFile5() {
		return file5;
	}

	public void setFile5(String file5) {
		this.file5 = file5;
	}

	public String getFile6() {
		return file6;
	}

	public void setFile6(String file6) {
		this.file6 = file6;
	}

	public String getFile7() {
		return file7;
	}

	public void setFile7(String file7) {
		this.file7 = file7;
	}

	public String getFile8() {
		return file8;
	}

	public void setFile8(String file8) {
		this.file8 = file8;
	}

	public String getFile9() {
		return file9;
	}

	public void setFile9(String file9) {
		this.file9 = file9;
	}

	public String getFile10() {
		return file10;
	}

	public void setFile10(String file10) {
		this.file10 = file10;
	}

	public String getFileEvaluationResult() {
		return fileEvaluationResult;
	}

	public void setFileEvaluationResult(String fileEvaluationResult) {
		this.fileEvaluationResult = fileEvaluationResult;
	}

	public String getAppReason() {
		return appReason;
	}

	public void setAppReason(String appReason) {
		this.appReason = appReason;
	}

}