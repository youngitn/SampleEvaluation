package oa.SampleEvaluation.common.global;

public class FinalString {
	private FinalString() {
		throw new IllegalStateException("Utility class");
	}

	public static final String QUERY_BILL_ID_FIELD_NAME = "Q_PNO";// 查詢單號欄位名稱
	public static final String QUERY_EMPID_FIELD_NAME = "Q_EMPID";// 查詢申請人欄位名稱
	public static final String QUERY_REQ_SDATE_FIELD_NAME = "Q_SDATE";// 查詢起日欄位名稱
	public static final String QUERY_REQ_EDATE_FIELD_NAME = "Q_EDATE";// 查詢迄日欄位名稱
	public static final String QUERY_STATUS_FIELD_NAME = "Q_STATUS";// 查詢簽核狀態欄位名稱
	public static final String QUERY_DEP_NO_FIELD_NAME = "Q_DEP_NO";// 查詢單位號碼欄位名稱
	public static final String QUERY_EMP_NAME_FIELD_NAME = "Q_EMP_NAME";
	public static final String QUERY_DEP_NAME_FIELD_NAME = "Q_DEP_NAME";

}
