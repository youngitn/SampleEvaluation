package oa.SampleEvaluation.model;

import oa.global.Model;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * SampleEvaluation.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
@dbTable(name = "SAMPLE_EVALUATION", pkName = "PNO")
public class SampleEvaluationPO extends Model {

	// declare a new annotation

	/** The pno. */
	@xmaker(name = "PNO")
	protected String pno;

	/** The app type. */
	@xmaker(name = "APP_TYPE")
	protected String appType;

	/** The urgency. */
	@xmaker(name = "URGENCY")
	protected String urgency;

	/** The material. */
	@xmaker(name = "MATERIAL")
	protected String material;

	/** The sap code. */
	@xmaker(name = "SAP_CODE")
	protected String sapCode;

	/** The ab code. */
	@xmaker(name = "AB_CODE")
	protected String abCode;

	/** The mfg lot no. */
	@xmaker(name = "MFG_LOT_NO")
	protected String mfgLotNo;

	/** The qty. */
	@xmaker(name = "QTY")
	protected String qty = "0";

	/** The pack. */
	@xmaker(name = "PACK")
	protected String pack;

	/** The unit. */
	@xmaker(name = "UNIT")
	protected String unit;

	/** The mfr. */
	@xmaker(name = "MFR")
	protected String mfr;

	/** The supplier. */
	@xmaker(name = "SUPPLIER")
	protected String supplier;

	/** The provide coa. */
	@xmaker(name = "PROVIDE_COA")
	protected String provideCoa;

	/** The provide spec. */
	@xmaker(name = "PROVIDE_SPEC")
	protected String provideSpec;

	/** The provide test method. */
	@xmaker(name = "PROVIDE_TEST_METHOD")
	protected String provideTestMethod;

	/** The provide sds. */
	@xmaker(name = "PROVIDE_SDS")
	protected String provideSds;

	/** The provide others. */
	@xmaker(name = "PROVIDE_OTHERS")
	protected String provideOthers;

	/** The note. */
	@xmaker(name = "NOTE")
	protected String note;

	/** The applicant. */
	@xmaker(name = "APPLICANT")
	protected String applicant;

	/** The app date. */
	@xmaker(name = "APP_DATE")
	protected String appDate;

	/** The receipt unit. */
	@xmaker(name = "RECEIPT_UNIT")
	protected String receiptUnit;

	/** The project code. */
	@xmaker(name = "PROJECT_CODE")
	protected String projectCode;

	/** The project leader. */
	@xmaker(name = "PROJECT_LEADER")
	protected String projectLeader;

	/** The notify no check. */
	@xmaker(name = "NOTIFY_NO_CHECK")
	protected String notifyNoCheck;

	/** The notify no trial prod. */
	@xmaker(name = "NOTIFY_NO_TRIAL_PROD")
	protected String notifyNoTrialProd;

	/** The qr no. */
	@xmaker(name = "QR_NO")
	protected String qrNo;

	/** The is check. */
	@xmaker(name = "IS_CHECK")
	protected String isCheck;

	/** The is trial production. */
	@xmaker(name = "IS_TRIAL_PRODUCTION")
	protected String isTrialProduction;

	/** The assessor. */
	@xmaker(name = "ASSESSOR")
	protected String assessor;

	/** The designee. */
	@xmaker(name = "DESIGNEE")
	protected String designee;

	/** The lab exe. */
	@xmaker(name = "LAB_EXE")
	protected String labExe;

	/** The doc ctrler. */
	@xmaker(name = "DOC_CTRLER")
	protected String docCtrler;

	/** The evaluation result. */
	@xmaker(name = "EVALUATION_RESULT")
	protected String evaluationResult;

	/** The file spec. */
	@xmaker(name = "FILE_SPEC")
	protected String fileSpec;

	/** The file coa. */
	@xmaker(name = "FILE_COA")
	protected String fileCoa;

	/** The file test method. */
	@xmaker(name = "FILE_TEST_METHOD")
	protected String fileTestMethod;

	/** The file others. */
	@xmaker(name = "FILE_OTHERS")
	protected String fileOthers;

	/** The file sds. */
	@xmaker(name = "FILE_SDS")
	protected String fileSds;

	/** The file spec note. */
	@xmaker(name = "FILE_SPEC_NOTE")
	protected String fileSpecNote;

	/** The file coa note. */
	@xmaker(name = "FILE_COA_NOTE")
	protected String fileCoaNote;

	/** The file test method note. */
	@xmaker(name = "FILE_TEST_METHOD_NOTE")
	protected String fileTestMethodNote;

	/** The file others note. */
	@xmaker(name = "FILE_OTHERS_NOTE")
	protected String fileOthersNote;

	/** The file sds note. */
	@xmaker(name = "FILE_SDS_NOTE")
	protected String fileSdsNote;

	/** The file 1. */
	@xmaker(name = "FILE_1")
	protected String file1;

	/** The file 2. */
	@xmaker(name = "FILE_2")
	protected String file2;

	/** The file 3. */
	@xmaker(name = "FILE_3")
	protected String file3;

	/** The file 4. */
	@xmaker(name = "FILE_4")
	protected String file4;

	/** The file 5. */
	@xmaker(name = "FILE_5")
	protected String file5;

	/** The file 6. */
	@xmaker(name = "FILE_6")
	protected String file6;

	/** The file 7. */
	@xmaker(name = "FILE_7")
	protected String file7;

	/** The file 8. */
	@xmaker(name = "FILE_8")
	protected String file8;

	/** The file 9. */
	@xmaker(name = "FILE_9")
	protected String file9;

	/** The file 10. */
	@xmaker(name = "FILE_10")
	protected String file10;

	/** The file 1 note. */
	@xmaker(name = "FILE_1_NOTE")
	protected String file1Note;

	/** The file 2 note. */
	@xmaker(name = "FILE_2_NOTE")
	protected String file2Note;

	/** The file 3 note. */
	@xmaker(name = "FILE_3_NOTE")
	protected String file3Note;

	/** The file 4 note. */
	@xmaker(name = "FILE_4_NOTE")
	protected String file4Note;

	/** The file 5 note. */
	@xmaker(name = "FILE_5_NOTE")
	protected String file5Note;

	/** The file 6 note. */
	@xmaker(name = "FILE_6_NOTE")
	protected String file6Note;

	/** The file 7 note. */
	@xmaker(name = "FILE_7_NOTE")
	protected String file7Note;

	/** The file 8 note. */
	@xmaker(name = "FILE_8_NOTE")
	protected String file8Note;

	/** The file 9 note. */
	@xmaker(name = "FILE_9_NOTE")
	protected String file9Note;

	/** The file 10 note. */
	@xmaker(name = "FILE_10_NOTE")
	protected String file10Note;

	/** The doc ctrler check. */
	@xmaker(name = "DOC_CTRLER_CHECK")
	protected String docCtrlerCheck;

	/** The doc ctrler tp. */
	@xmaker(name = "DOC_CTRLER_TP")
	protected String docCtrlerTp;

	/** The qc boss. */
	@xmaker(name = "QC_BOSS")
	protected String qcBoss;

	/** The coordinator. */
	@xmaker(name = "COORDINATOR")
	protected String coordinator;

	/** The file evaluation result. */
	@xmaker(name = "FILE_EVALUATION_RESULT")
	protected String fileEvaluationResult;

	/** The is test. */
	@xmaker(name = "IS_TEST")
	protected String isTest;

	/** The app reason. */
	@xmaker(name = "APP_REASON")
	protected String appReason;

	/** The mfr address. */
	@xmaker(name = "MFR_ADDRESS")
	protected String mfrAddress;

	/** The urgencyt type. */
	@xmaker(name = "URGENCY_TYPE")
	protected String urgencytType;

	/** The mfr country. */
	@xmaker(name = "MFR_COUNTRY")
	protected String mfrCountry;

	@xmaker(name = "CHECK_DATE")
	protected String checkDate;

	/** The is else. */
	@xmaker(name = "IS_ELSE")
	protected String isElse;

	/**
	 * Gets the FileSpecNote.
	 *
	 * @return [String]
	 */
	public String getFileSpecNote() {
		return fileSpecNote;
	}

	/**
	 * Sets the FileSpecNote.
	 *
	 * @param fileSpecNote void
	 */
	public void setFileSpecNote(String fileSpecNote) {
		this.fileSpecNote = fileSpecNote;
	}

	/**
	 * Gets the FileCoaNote.
	 *
	 * @return [String]
	 */
	public String getFileCoaNote() {
		return fileCoaNote;
	}

	/**
	 * Sets the FileCoaNote.
	 *
	 * @param fileCoaNote void
	 */
	public void setFileCoaNote(String fileCoaNote) {
		this.fileCoaNote = fileCoaNote;
	}

	/**
	 * Gets the FileTestMethodNote.
	 *
	 * @return [String]
	 */
	public String getFileTestMethodNote() {
		return fileTestMethodNote;
	}

	/**
	 * Sets the FileTestMethodNote.
	 *
	 * @param fileTestMethodNote void
	 */
	public void setFileTestMethodNote(String fileTestMethodNote) {
		this.fileTestMethodNote = fileTestMethodNote;
	}

	/**
	 * Gets the FileOthersNote.
	 *
	 * @return [String]
	 */
	public String getFileOthersNote() {
		return fileOthersNote;
	}

	/**
	 * Sets the FileOthersNote.
	 *
	 * @param fileOthersNote void
	 */
	public void setFileOthersNote(String fileOthersNote) {
		this.fileOthersNote = fileOthersNote;
	}

	/**
	 * Gets the FileSdsNote.
	 *
	 * @return [String]
	 */
	public String getFileSdsNote() {
		return fileSdsNote;
	}

	/**
	 * Sets the FileSdsNote.
	 *
	 * @param fileSdsNote void
	 */
	public void setFileSdsNote(String fileSdsNote) {
		this.fileSdsNote = fileSdsNote;
	}

	/**
	 * Gets the File1Note.
	 *
	 * @return [String]
	 */
	public String getFile1Note() {
		return file1Note;
	}

	/**
	 * Sets the File1Note.
	 *
	 * @param file1Note void
	 */
	public void setFile1Note(String file1Note) {
		this.file1Note = file1Note;
	}

	/**
	 * Gets the File2Note.
	 *
	 * @return [String]
	 */
	public String getFile2Note() {
		return file2Note;
	}

	/**
	 * Sets the File2Note.
	 *
	 * @param file2Note void
	 */
	public void setFile2Note(String file2Note) {
		this.file2Note = file2Note;
	}

	/**
	 * Gets the File3Note.
	 *
	 * @return [String]
	 */
	public String getFile3Note() {
		return file3Note;
	}

	/**
	 * Sets the File3Note.
	 *
	 * @param file3Note void
	 */
	public void setFile3Note(String file3Note) {
		this.file3Note = file3Note;
	}

	/**
	 * Gets the File4Note.
	 *
	 * @return [String]
	 */
	public String getFile4Note() {
		return file4Note;
	}

	/**
	 * Sets the File4Note.
	 *
	 * @param file4Note void
	 */
	public void setFile4Note(String file4Note) {
		this.file4Note = file4Note;
	}

	/**
	 * Gets the File5Note.
	 *
	 * @return [String]
	 */
	public String getFile5Note() {
		return file5Note;
	}

	/**
	 * Sets the File5Note.
	 *
	 * @param file5Note void
	 */
	public void setFile5Note(String file5Note) {
		this.file5Note = file5Note;
	}

	/**
	 * Gets the File6Note.
	 *
	 * @return [String]
	 */
	public String getFile6Note() {
		return file6Note;
	}

	/**
	 * Sets the File6Note.
	 *
	 * @param file6Note void
	 */
	public void setFile6Note(String file6Note) {
		this.file6Note = file6Note;
	}

	/**
	 * Gets the File7Note.
	 *
	 * @return [String]
	 */
	public String getFile7Note() {
		return file7Note;
	}

	/**
	 * Sets the File7Note.
	 *
	 * @param file7Note void
	 */
	public void setFile7Note(String file7Note) {
		this.file7Note = file7Note;
	}

	/**
	 * Gets the File8Note.
	 *
	 * @return [String]
	 */
	public String getFile8Note() {
		return file8Note;
	}

	/**
	 * Sets the File8Note.
	 *
	 * @param file8Note void
	 */
	public void setFile8Note(String file8Note) {
		this.file8Note = file8Note;
	}

	/**
	 * Gets the File9Note.
	 *
	 * @return [String]
	 */
	public String getFile9Note() {
		return file9Note;
	}

	/**
	 * Sets the File9Note.
	 *
	 * @param file9Note void
	 */
	public void setFile9Note(String file9Note) {
		this.file9Note = file9Note;
	}

	/**
	 * Gets the File10Note.
	 *
	 * @return [String]
	 */
	public String getFile10Note() {
		return file10Note;
	}

	/**
	 * Sets the File10Note.
	 *
	 * @param file10Note void
	 */
	public void setFile10Note(String file10Note) {
		this.file10Note = file10Note;
	}

	/**
	 * Gets the DocCtrlerCheck.
	 *
	 * @return [String]
	 */
	public String getDocCtrlerCheck() {
		return docCtrlerCheck;
	}

	/**
	 * Sets the DocCtrlerCheck.
	 *
	 * @param docCtrlerCheck void
	 */
	public void setDocCtrlerCheck(String docCtrlerCheck) {
		this.docCtrlerCheck = docCtrlerCheck;
	}

	/**
	 * Gets the DocCtrlerTp.
	 *
	 * @return [String]
	 */
	public String getDocCtrlerTp() {
		return docCtrlerTp;
	}

	/**
	 * Sets the DocCtrlerTp.
	 *
	 * @param docCtrlerTp void
	 */
	public void setDocCtrlerTp(String docCtrlerTp) {
		this.docCtrlerTp = docCtrlerTp;
	}

	/**
	 * Instantiates a new sample evaluation PO.
	 */
	public SampleEvaluationPO() {

	}

	/**
	 * Sets the DocCtrler.
	 *
	 * @param value void
	 */
	public void setDocCtrler(String value) {
		this.docCtrler = value;
	}

	/**
	 * Gets the DocCtrler.
	 *
	 * @return [String]
	 */
	public String getDocCtrler() {
		return docCtrler;
	}

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
	 * Gets the AppType.
	 *
	 * @return [String]
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * Sets the AppType.
	 *
	 * @param appType void
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * Gets the Urgency.
	 *
	 * @return [String]
	 */
	public String getUrgency() {
		return urgency;
	}

	/**
	 * Sets the Urgency.
	 *
	 * @param urgency void
	 */
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	/**
	 * Gets the Material.
	 *
	 * @return [String]
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * Sets the Material.
	 *
	 * @param material void
	 */
	public void setMaterial(String material) {
		this.material = material;
	}

	/**
	 * Gets the SapCode.
	 *
	 * @return [String]
	 */
	public String getSapCode() {
		return sapCode;
	}

	/**
	 * Sets the SapCode.
	 *
	 * @param sapCode void
	 */
	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}

	/**
	 * Gets the AbCode.
	 *
	 * @return [String]
	 */
	public String getAbCode() {
		return abCode;
	}

	/**
	 * Sets the AbCode.
	 *
	 * @param abCode void
	 */
	public void setAbCode(String abCode) {
		this.abCode = abCode;
	}

	/**
	 * Gets the MfgLotNo.
	 *
	 * @return [String]
	 */
	public String getMfgLotNo() {
		return mfgLotNo;
	}

	/**
	 * Sets the MfgLotNo.
	 *
	 * @param mfgLotNo void
	 */
	public void setMfgLotNo(String mfgLotNo) {
		this.mfgLotNo = mfgLotNo;
	}

	/**
	 * Gets the Qty.
	 *
	 * @return [String]
	 */
	public String getQty() {
		return qty;
	}

	/**
	 * Sets the Qty.
	 *
	 * @param qty void
	 */
	public void setQty(String qty) {
		this.qty = qty;
	}

	/**
	 * Gets the Pack.
	 *
	 * @return [String]
	 */
	public String getPack() {
		return pack;
	}

	/**
	 * Sets the Pack.
	 *
	 * @param pack void
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}

	/**
	 * Gets the Unit.
	 *
	 * @return [String]
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the Unit.
	 *
	 * @param unit void
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Gets the Mfr.
	 *
	 * @return [String]
	 */
	public String getMfr() {
		return mfr;
	}

	/**
	 * Sets the Mfr.
	 *
	 * @param mfr void
	 */
	public void setMfr(String mfr) {
		this.mfr = mfr;
	}

	/**
	 * Gets the Supplier.
	 *
	 * @return [String]
	 */
	public String getSupplier() {
		return supplier;
	}

	/**
	 * Sets the Supplier.
	 *
	 * @param supplier void
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	/**
	 * Gets the ProvideCoa.
	 *
	 * @return [String]
	 */
	public String getProvideCoa() {
		return provideCoa;
	}

	/**
	 * Sets the ProvideCoa.
	 *
	 * @param provideCoa void
	 */
	public void setProvideCoa(String provideCoa) {
		this.provideCoa = provideCoa;
	}

	/**
	 * Gets the ProvideSpec.
	 *
	 * @return [String]
	 */
	public String getProvideSpec() {
		return provideSpec;
	}

	/**
	 * Sets the ProvideSpec.
	 *
	 * @param provideSpec void
	 */
	public void setProvideSpec(String provideSpec) {
		this.provideSpec = provideSpec;
	}

	/**
	 * Gets the ProvideTestMethod.
	 *
	 * @return [String]
	 */
	public String getProvideTestMethod() {
		return provideTestMethod;
	}

	/**
	 * Sets the ProvideTestMethod.
	 *
	 * @param provideTestMethod void
	 */
	public void setProvideTestMethod(String provideTestMethod) {
		this.provideTestMethod = provideTestMethod;
	}

	/**
	 * Gets the ProvideSds.
	 *
	 * @return [String]
	 */
	public String getProvideSds() {
		return provideSds;
	}

	/**
	 * Sets the ProvideSds.
	 *
	 * @param provideSds void
	 */
	public void setProvideSds(String provideSds) {
		this.provideSds = provideSds;
	}

	/**
	 * Gets the ProvideOthers.
	 *
	 * @return [String]
	 */
	public String getProvideOthers() {
		return provideOthers;
	}

	/**
	 * Sets the ProvideOthers.
	 *
	 * @param provideOthers void
	 */
	public void setProvideOthers(String provideOthers) {
		this.provideOthers = provideOthers;
	}

	/**
	 * Gets the Note.
	 *
	 * @return [String]
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the Note.
	 *
	 * @param note void
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Gets the Applicant.
	 *
	 * @return [String]
	 */
	public String getApplicant() {
		return applicant;
	}

	/**
	 * Sets the Applicant.
	 *
	 * @param applicant void
	 */
	public void setApplicant(String applicant) {
		this.applicant = applicant;
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

	/**
	 * Gets the ReceiptUnit.
	 *
	 * @return [String]
	 */
	public String getReceiptUnit() {
		return receiptUnit;
	}

	/**
	 * Sets the ReceiptUnit.
	 *
	 * @param receiptUnit void
	 */
	public void setReceiptUnit(String receiptUnit) {
		this.receiptUnit = receiptUnit;
	}

	/**
	 * Gets the ProjectCode.
	 *
	 * @return [String]
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * Sets the ProjectCode.
	 *
	 * @param projectCode void
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * Gets the ProjectLeader.
	 *
	 * @return [String]
	 */
	public String getProjectLeader() {
		return projectLeader;
	}

	/**
	 * Sets the ProjectLeader.
	 *
	 * @param projectLeader void
	 */
	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	/**
	 * Gets the NotifyNoCheck.
	 *
	 * @return [String]
	 */
	public String getNotifyNoCheck() {
		return notifyNoCheck;
	}

	/**
	 * Sets the NotifyNoCheck.
	 *
	 * @param notifyNoCheck void
	 */
	public void setNotifyNoCheck(String notifyNoCheck) {
		this.notifyNoCheck = notifyNoCheck;
	}

	/**
	 * Gets the NotifyNoTrialProd.
	 *
	 * @return [String]
	 */
	public String getNotifyNoTrialProd() {
		return notifyNoTrialProd;
	}

	/**
	 * Sets the NotifyNoTrialProd.
	 *
	 * @param notifyNoTrialProd void
	 */
	public void setNotifyNoTrialProd(String notifyNoTrialProd) {
		this.notifyNoTrialProd = notifyNoTrialProd;
	}

	/**
	 * Gets the QrNo.
	 *
	 * @return [String]
	 */
	public String getQrNo() {
		return qrNo;
	}

	/**
	 * Sets the QrNo.
	 *
	 * @param qrNo void
	 */
	public void setQrNo(String qrNo) {
		this.qrNo = qrNo;
	}

	/**
	 * Gets the IsCheck.
	 *
	 * @return [String]
	 */
	public String getIsCheck() {
		return isCheck;
	}

	/**
	 * Sets the IsCheck.
	 *
	 * @param isCheck void
	 */
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	/**
	 * Gets the IsTrialProduction.
	 *
	 * @return [String]
	 */
	public String getIsTrialProduction() {
		return isTrialProduction;
	}

	/**
	 * Sets the IsTrialProduction.
	 *
	 * @param isTrialProduction void
	 */
	public void setIsTrialProduction(String isTrialProduction) {
		this.isTrialProduction = isTrialProduction;
	}

	/**
	 * Gets the Designee.
	 *
	 * @return [String]
	 */
	public String getDesignee() {
		return designee;
	}

	/**
	 * Sets the Designee.
	 *
	 * @param designee void
	 */
	public void setDesignee(String designee) {
		this.designee = designee;
	}

	/**
	 * Gets the Assessor.
	 *
	 * @return [String]
	 */
	public String getAssessor() {
		return assessor;
	}

	/**
	 * Sets the Assessor.
	 *
	 * @param assessor void
	 */
	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	/**
	 * Gets the LabExe.
	 *
	 * @return [String]
	 */
	public String getLabExe() {
		return labExe;
	}

	/**
	 * Sets the LabExe.
	 *
	 * @param labExe void
	 */
	public void setLabExe(String labExe) {
		this.labExe = labExe;
	}

	/**
	 * Gets the EvaluationResult.
	 *
	 * @return [String]
	 */
	public String getEvaluationResult() {
		return evaluationResult;
	}

	/**
	 * Sets the EvaluationResult.
	 *
	 * @param evaluationResult void
	 */
	public void setEvaluationResult(String evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	/**
	 * Gets the FileSpec.
	 *
	 * @return [String]
	 */
	public String getFileSpec() {
		return fileSpec;
	}

	/**
	 * Sets the FileSpec.
	 *
	 * @param fileSpec void
	 */
	public void setFileSpec(String fileSpec) {
		this.fileSpec = fileSpec;
	}

	/**
	 * Gets the FileCoa.
	 *
	 * @return [String]
	 */
	public String getFileCoa() {
		return fileCoa;
	}

	/**
	 * Sets the FileCoa.
	 *
	 * @param fileCoa void
	 */
	public void setFileCoa(String fileCoa) {
		this.fileCoa = fileCoa;
	}

	/**
	 * Gets the FileTestMethod.
	 *
	 * @return [String]
	 */
	public String getFileTestMethod() {
		return fileTestMethod;
	}

	/**
	 * Sets the FileTestMethod.
	 *
	 * @param fileTestMethod void
	 */
	public void setFileTestMethod(String fileTestMethod) {
		this.fileTestMethod = fileTestMethod;
	}

	/**
	 * Gets the FileOthers.
	 *
	 * @return [String]
	 */
	public String getFileOthers() {
		return fileOthers;
	}

	/**
	 * Sets the FileOthers.
	 *
	 * @param fileOthers void
	 */
	public void setFileOthers(String fileOthers) {
		this.fileOthers = fileOthers;
	}

	/**
	 * Gets the FileSds.
	 *
	 * @return [String]
	 */
	public String getFileSds() {
		return fileSds;
	}

	/**
	 * Sets the FileSds.
	 *
	 * @param fileSds void
	 */
	public void setFileSds(String fileSds) {
		this.fileSds = fileSds;
	}

	/**
	 * Gets the File1.
	 *
	 * @return [String]
	 */
	public String getFile1() {
		return file1;
	}

	/**
	 * Sets the File1.
	 *
	 * @param file1 void
	 */
	public void setFile1(String file1) {
		this.file1 = file1;
	}

	/**
	 * Gets the File2.
	 *
	 * @return [String]
	 */
	public String getFile2() {
		return file2;
	}

	/**
	 * Sets the File2.
	 *
	 * @param file2 void
	 */
	public void setFile2(String file2) {
		this.file2 = file2;
	}

	/**
	 * Gets the File3.
	 *
	 * @return [String]
	 */
	public String getFile3() {
		return file3;
	}

	/**
	 * Sets the File3.
	 *
	 * @param file3 void
	 */
	public void setFile3(String file3) {
		this.file3 = file3;
	}

	/**
	 * Gets the File4.
	 *
	 * @return [String]
	 */
	public String getFile4() {
		return file4;
	}

	/**
	 * Sets the File4.
	 *
	 * @param file4 void
	 */
	public void setFile4(String file4) {
		this.file4 = file4;
	}

	/**
	 * Gets the File5.
	 *
	 * @return [String]
	 */
	public String getFile5() {
		return file5;
	}

	/**
	 * Sets the File5.
	 *
	 * @param file5 void
	 */
	public void setFile5(String file5) {
		this.file5 = file5;
	}

	/**
	 * Gets the File6.
	 *
	 * @return [String]
	 */
	public String getFile6() {
		return file6;
	}

	/**
	 * Sets the File6.
	 *
	 * @param file6 void
	 */
	public void setFile6(String file6) {
		this.file6 = file6;
	}

	/**
	 * Gets the File7.
	 *
	 * @return [String]
	 */
	public String getFile7() {
		return file7;
	}

	/**
	 * Sets the File7.
	 *
	 * @param file7 void
	 */
	public void setFile7(String file7) {
		this.file7 = file7;
	}

	/**
	 * Gets the File8.
	 *
	 * @return [String]
	 */
	public String getFile8() {
		return file8;
	}

	/**
	 * Sets the File8.
	 *
	 * @param file8 void
	 */
	public void setFile8(String file8) {
		this.file8 = file8;
	}

	/**
	 * Gets the File9.
	 *
	 * @return [String]
	 */
	public String getFile9() {
		return file9;
	}

	/**
	 * Sets the File9.
	 *
	 * @param file9 void
	 */
	public void setFile9(String file9) {
		this.file9 = file9;
	}

	/**
	 * Gets the File10.
	 *
	 * @return [String]
	 */
	public String getFile10() {
		return file10;
	}

	/**
	 * Sets the File10.
	 *
	 * @param file10 void
	 */
	public void setFile10(String file10) {
		this.file10 = file10;
	}

	/**
	 * Gets the FileEvaluationResult.
	 *
	 * @return [String]
	 */
	public String getFileEvaluationResult() {
		return fileEvaluationResult;
	}

	/**
	 * Sets the FileEvaluationResult.
	 *
	 * @param fileEvaluationResult void
	 */
	public void setFileEvaluationResult(String fileEvaluationResult) {
		this.fileEvaluationResult = fileEvaluationResult;
	}

	/**
	 * Gets the AppReason.
	 *
	 * @return [String]
	 */
	public String getAppReason() {
		return appReason;
	}

	/**
	 * Sets the AppReason.
	 *
	 * @param appReason void
	 */
	public void setAppReason(String appReason) {
		this.appReason = appReason;
	}

	/**
	 * Gets the QcBoss.
	 *
	 * @return [String]
	 */
	public String getQcBoss() {
		return qcBoss;
	}

	/**
	 * Sets the QcBoss.
	 *
	 * @param qcBoss void
	 */
	public void setQcBoss(String qcBoss) {
		this.qcBoss = qcBoss;
	}

	/**
	 * Gets the Coordinator.
	 *
	 * @return [String]
	 */
	public String getCoordinator() {
		return coordinator;
	}

	/**
	 * Sets the Coordinator.
	 *
	 * @param coordinator void
	 */
	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}

	/**
	 * Gets the IsTest.
	 *
	 * @return [String]
	 */
	public String getIsTest() {
		return isTest;
	}

	/**
	 * Sets the IsTest.
	 *
	 * @param isTest void
	 */
	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}

	/**
	 * Gets the MfrAddress.
	 *
	 * @return [String]
	 */
	public String getMfrAddress() {
		return mfrAddress;
	}

	/**
	 * Sets the MfrAddress.
	 *
	 * @param mfrAddress void
	 */
	public void setMfrAddress(String mfrAddress) {
		this.mfrAddress = mfrAddress;
	}

	/**
	 * Gets the UrgencytType.
	 *
	 * @return [String]
	 */
	public String getUrgencytType() {
		return urgencytType;
	}

	/**
	 * Sets the UrgencytType.
	 *
	 * @param urgencytType void
	 */
	public void setUrgencytType(String urgencytType) {
		this.urgencytType = urgencytType;
	}

	/**
	 * Gets the MfrCountry.
	 *
	 * @return [String]
	 */
	public String getMfrCountry() {
		return mfrCountry;
	}

	/**
	 * Sets the MfrCountry.
	 *
	 * @param mfrCountry void
	 */
	public void setMfrCountry(String mfrCountry) {
		this.mfrCountry = mfrCountry;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getIsElse() {
		return isElse;
	}

	public void setIsElse(String isElse) {
		this.isElse = isElse;
	}

}