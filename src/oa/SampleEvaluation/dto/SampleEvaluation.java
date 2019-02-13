package oa.SampleEvaluation.dto;

import java.io.Serializable;

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

	protected static final long serialVersionUID = 42L;
	@xmaker(name = "PNO")
	protected String pno;
	@xmaker(name = "APP_TYPE")
	protected String appType;
	@xmaker(name = "URGENCY")
	protected String urgency;
	@xmaker(name = "MATERIAL")
	protected String material;
	@xmaker(name = "SAP_CODE")
	protected String sapCode;
	@xmaker(name = "AB_CODE")
	protected String abCode;
	@xmaker(name = "MFG_LOT_NO")
	protected String mfgLotNo;
	@xmaker(name = "QTY")
	protected String qty = "0";
	@xmaker(name = "PACK")
	protected String pack;
	@xmaker(name = "UNIT")
	protected String unit;
	@xmaker(name = "MFR")
	protected String mfr;
	@xmaker(name = "SUPPLIER")
	protected String supplier;
	@xmaker(name = "PROVIDE_COA")
	protected String provideCoa;
	@xmaker(name = "PROVIDE_SPEC")
	protected String provideSpec;
	@xmaker(name = "PROVIDE_TEST_METHOD")
	protected String provideTestMethod;
	@xmaker(name = "PROVIDE_SDS")
	protected String provideSds;
	@xmaker(name = "PROVIDE_OTHERS")
	protected String provideOthers;
	@xmaker(name = "NOTE")
	protected String note;
	@xmaker(name = "APPLICANT")
	protected String applicant;
	@xmaker(name = "APP_DATE")
	protected String appDate;
	@xmaker(name = "RECEIPT_UNIT")
	protected String receiptUnit;
	@xmaker(name = "PROJECT_CODE")
	protected String projectCode;
	@xmaker(name = "PROJECT_LEADER")
	protected String projectLeader;
	@xmaker(name = "NOTIFY_NO_CHECK")
	protected String notifyNoCheck;
	@xmaker(name = "NOTIFY_NO_TRIAL_PROD")
	protected String notifyNoTrialProd;
	@xmaker(name = "QR_NO")
	protected String qrNo;
	@xmaker(name = "IS_CHECK")
	protected String isCheck;
	@xmaker(name = "IS_TRIAL_PRODUCTION")
	protected String isTrialProduction;
	@xmaker(name = "ASSESSOR")
	protected String assessor;
	@xmaker(name = "DESIGNEE")
	protected String designee;
	@xmaker(name = "LAB_EXE")
	protected String labExe;
	@xmaker(name = "DOC_CTRLER")
	protected String docCtrler;
	@xmaker(name = "EVALUATION_RESULT")
	protected String evaluationResult;
	@xmaker(name = "FILE_SPEC")
	protected String fileSpec;
	@xmaker(name = "FILE_COA")
	protected String fileCoa;
	@xmaker(name = "FILE_TEST_METHOD")
	protected String fileTestMethod;
	@xmaker(name = "FILE_OTHERS")
	protected String fileOthers;
	@xmaker(name = "FILE_SDS")
	protected String fileSds;
	@xmaker(name = "FILE_SPEC_NOTE")
	protected String fileSpecNote;
	@xmaker(name = "FILE_COA_NOTE")
	protected String fileCoaNote;
	@xmaker(name = "FILE_TEST_METHOD_NOTE")
	protected String fileTestMethodNote;
	@xmaker(name = "FILE_OTHERS_NOTE")
	protected String fileOthersNote;
	@xmaker(name = "FILE_SDS_NOTE")
	protected String fileSdsNote;
	@xmaker(name = "FILE_1")
	protected String file1;
	@xmaker(name = "FILE_2")
	protected String file2;
	@xmaker(name = "FILE_3")
	protected String file3;
	@xmaker(name = "FILE_4")
	protected String file4;
	@xmaker(name = "FILE_5")
	protected String file5;
	@xmaker(name = "FILE_6")
	protected String file6;
	@xmaker(name = "FILE_7")
	protected String file7;
	@xmaker(name = "FILE_8")
	protected String file8;
	@xmaker(name = "FILE_9")
	protected String file9;
	@xmaker(name = "FILE_10")
	protected String file10;
	@xmaker(name = "FILE_1_NOTE")
	protected String file1Note;
	@xmaker(name = "FILE_2_NOTE")
	protected String file2Note;

	@xmaker(name = "FILE_3_NOTE")
	protected String file3Note;
	@xmaker(name = "FILE_4_NOTE")
	protected String file4Note;
	@xmaker(name = "FILE_5_NOTE")
	protected String file5Note;
	@xmaker(name = "FILE_6_NOTE")
	protected String file6Note;
	@xmaker(name = "FILE_7_NOTE")
	protected String file7Note;
	@xmaker(name = "FILE_8_NOTE")
	protected String file8Note;
	@xmaker(name = "FILE_9_NOTE")
	protected String file9Note;
	@xmaker(name = "FILE_10_NOTE")
	protected String file10Note;
	@xmaker(name = "DOC_CTRLER_CHECK")
	protected String docCtrlerCheck;
	@xmaker(name = "DOC_CTRLER_TP")
	protected String docCtrlerTp;
	@xmaker(name = "QC_BOSS")
	protected String qcBoss;
	@xmaker(name = "COORDINATOR")
	protected String coordinator;
	@xmaker(name = "FILE_EVALUATION_RESULT")
	protected String fileEvaluationResult;
	@xmaker(name = "IS_TEST")
	protected String isTest;
	@xmaker(name = "APP_REASON")
	protected String appReason;

	public String getFileSpecNote() {
		return fileSpecNote;
	}

	public void setFileSpecNote(String fileSpecNote) {
		this.fileSpecNote = fileSpecNote;
	}

	public String getFileCoaNote() {
		return fileCoaNote;
	}

	public void setFileCoaNote(String fileCoaNote) {
		this.fileCoaNote = fileCoaNote;
	}

	public String getFileTestMethodNote() {
		return fileTestMethodNote;
	}

	public void setFileTestMethodNote(String fileTestMethodNote) {
		this.fileTestMethodNote = fileTestMethodNote;
	}

	public String getFileOthersNote() {
		return fileOthersNote;
	}

	public void setFileOthersNote(String fileOthersNote) {
		this.fileOthersNote = fileOthersNote;
	}

	public String getFileSdsNote() {
		return fileSdsNote;
	}

	public void setFileSdsNote(String fileSdsNote) {
		this.fileSdsNote = fileSdsNote;
	}

	public String getFile1Note() {
		return file1Note;
	}

	public void setFile1Note(String file1Note) {
		this.file1Note = file1Note;
	}

	public String getFile2Note() {
		return file2Note;
	}

	public void setFile2Note(String file2Note) {
		this.file2Note = file2Note;
	}

	public String getFile3Note() {
		return file3Note;
	}

	public void setFile3Note(String file3Note) {
		this.file3Note = file3Note;
	}

	public String getFile4Note() {
		return file4Note;
	}

	public void setFile4Note(String file4Note) {
		this.file4Note = file4Note;
	}

	public String getFile5Note() {
		return file5Note;
	}

	public void setFile5Note(String file5Note) {
		this.file5Note = file5Note;
	}

	public String getFile6Note() {
		return file6Note;
	}

	public void setFile6Note(String file6Note) {
		this.file6Note = file6Note;
	}

	public String getFile7Note() {
		return file7Note;
	}

	public void setFile7Note(String file7Note) {
		this.file7Note = file7Note;
	}

	public String getFile8Note() {
		return file8Note;
	}

	public void setFile8Note(String file8Note) {
		this.file8Note = file8Note;
	}

	public String getFile9Note() {
		return file9Note;
	}

	public void setFile9Note(String file9Note) {
		this.file9Note = file9Note;
	}

	public String getFile10Note() {
		return file10Note;
	}

	public void setFile10Note(String file10Note) {
		this.file10Note = file10Note;
	}

	public String getDocCtrlerCheck() {
		return docCtrlerCheck;
	}

	public void setDocCtrlerCheck(String docCtrlerCheck) {
		this.docCtrlerCheck = docCtrlerCheck;
	}

	public String getDocCtrlerTp() {
		return docCtrlerTp;
	}

	public void setDocCtrlerTp(String docCtrlerTp) {
		this.docCtrlerTp = docCtrlerTp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SampleEvaluation() {

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

	public String getQcBoss() {
		return qcBoss;
	}

	public void setQcBoss(String qcBoss) {
		this.qcBoss = qcBoss;
	}

	public String getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}

	public String getIsTest() {
		return isTest;
	}

	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}

}