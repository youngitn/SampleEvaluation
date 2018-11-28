package oa.SampleEvaluation.common.global;

public class FinalString {
	private FinalString() {
		throw new IllegalStateException("Utility class");
	}

	public static final String QUERY_BILL_ID_FIELD_NAME = "Q_PNO";// �d�߳渹���W��
	public static final String QUERY_EMPID_FIELD_NAME = "Q_EMPID";// �d�ߥӽФH���W��
	public static final String QUERY_REQ_SDATE_FIELD_NAME = "Q_SDATE";// �d�߰_�����W��
	public static final String QUERY_REQ_EDATE_FIELD_NAME = "Q_EDATE";// �d�ߨ������W��
	public static final String QUERY_STATUS_FIELD_NAME = "Q_STATUS";// �d��ñ�֪��A���W��
	public static final String QUERY_DEP_NO_FIELD_NAME = "Q_DEP_NO";// �d�߳�츹�X���W��
	public static final String QUERY_EMP_NAME_FIELD_NAME = "Q_EMP_NAME";
	public static final String QUERY_DEP_NAME_FIELD_NAME = "Q_DEP_NAME";
	public static final String QUERY_URGENCY_FIELD_NAME = "Q_URGENCY";
	public static final String QUERY_SAP_CODE_FIELD_NAME = "Q_SAP_CODE";
	public static final String QUERY_MATERIAL_FIELD_NAME = "Q_MATERIAL";
	public static final String QUERY_MFR_FIELD_NAME = "Q_MFR";

	// ���W��o���------>
	public static final String TABLE_FIELD_FOR_TALK = "PNO,app_type,urgency,material,sap_code,ab_code,mfg_lot_no,qty,pack,unit,mfr,supplier,provide_coa,provide_spec,provide_test_method,provide_sds,provide_others,note,applicant,app_date,receipt_unit,project_code,project_leader,notify_no_check,notify_no_trial_prod,qr_no,is_check,is_trial_production,lab_exe,assessor,designee,doc_ctrler ,evaluation_result,FILE_SPEC ,FILE_COA,FILE_TEST_METHOD,FILE_OTHERS,FILE_SDS,FILE_1,FILE_2,FILE_3,FILE_4,FILE_5,FILE_6,FILE_7,FILE_8,FILE_9,FILE_10,FILE_EVALUATION_RESULT,APP_REASON";
	public static final String TABLE_FIELD_MAP_VLUE_FOR_TALK = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	public static final String TABLE_FIELD_WITH_MAP_VLUE_FOR_TALK = "app_type=?,urgency=?,material=?,sap_code=?,ab_code=?,mfg_lot_no=?,qty=?,pack=?,unit=?,mfr=?,supplier=?,provide_coa=?,provide_spec=?,provide_test_method=?,provide_sds=?,provide_others=?,note=?,applicant=?,app_date=?,receipt_unit=?,project_code=?,project_leader=?,notify_no_check=?,notify_no_trial_prod=?,qr_no=?,is_check=?,is_trial_production=?,lab_exe=?,assessor=?,designee=?,doc_ctrler=?,evaluation_result=?,FILE_SPEC=? ,FILE_COA=?,FILE_TEST_METHOD=?,FILE_OTHERS=?,FILE_SDS=?,FILE_1=?,FILE_2=?,FILE_3=?,FILE_4=?,FILE_5=?,FILE_6=?,FILE_7=?,FILE_8=?,FILE_9=?,FILE_10=?,FILE_EVALUATION_RESULT=?,APP_REASON=?";
	// ���W��o���<------

	public static final String SUB_TABLE_FIELD_WITH_MAP_VLUE_FOR_TALK = "pno=?,"
			+ FinalString.TABLE_FIELD_WITH_MAP_VLUE_FOR_TALK;
	public static final String SUB_TABLE_FIELD_MAP_VLUE_FOR_TALK = "?," + FinalString.TABLE_FIELD_MAP_VLUE_FOR_TALK;
	public static final String SUB_TABLE_FIELD_FOR_TALK = "own_pno," + FinalString.TABLE_FIELD_FOR_TALK;
}
