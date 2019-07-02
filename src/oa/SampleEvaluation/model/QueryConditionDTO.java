package oa.SampleEvaluation.model;

import java.sql.SQLException;

import oa.global.DtoUtil;
import oa.global.Model;
import oa.global.annotation.xmaker;

/**
 * 各屬性對應表單查詢頁面中,查詢條件欄位<br>
 * 
 * xmaker(<br>
 * name = "表單欄位名稱", <br>
 * adapDbFieldName = "資料表欄位名稱",<br>
 * isDateStart/isDateEnd = 該屬性是否對應日期起/迄日,<br>
 * isFlowStatus = 該屬性是否對應簽核狀態查詢欄位<br>
 * 
 * ).
 *
 * @author u52116
 */
public class QueryConditionDTO extends Model {

	/** The q pno. */
	@xmaker(name = "Q_PNO", mappingDbFieldName = "PNO")
	private String Q_PNO;

	/** The q empid. */
	@xmaker(name = "Q_EMPID", mappingDbFieldName = "APPLICANT")
	private String Q_EMPID;

	/** The q sdate. */
	@xmaker(name = "Q_SDATE", mappingDbFieldName = "APP_DATE", isDateStart = true)
	private String Q_SDATE;

	/** The q edate. */
	@xmaker(name = "Q_EDATE", mappingDbFieldName = "APP_DATE", isDateEnd = true)
	private String Q_EDATE;

	/** The q urgency. */
	@xmaker(name = "Q_URGENCY", mappingDbFieldName = "URGENCY")
	private String Q_URGENCY;

	/** The q sap code. */
	@xmaker(name = "Q_SAP_CODE", mappingDbFieldName = "SAP_CODE")
	private String Q_SAP_CODE;

	/** The q material. */
	@xmaker(name = "Q_MATERIAL", mappingDbFieldName = "MATERIAL")
	private String Q_MATERIAL;

	/** The q mfr. */
	@xmaker(name = "Q_MFR", mappingDbFieldName = "MFR")
	private String Q_MFR;

	/** The q status. */
	@xmaker(name = "Q_STATUS", isFlowStatus = true)
	private String Q_STATUS;

	/** The q qr no. */
	@xmaker(name = "Q_QR_NO", mappingDbFieldName = "QR_NO")
	private String Q_QR_NO;

	/** The q receipt unit. */
	@xmaker(name = "Q_RECEIPT_UNIT", mappingDbFieldName = "RECEIPT_UNIT")
	private String Q_RECEIPT_UNIT;

	@xmaker(name = "Q_APP_TYPE", mappingDbFieldName = "APP_TYPE")
	private String Q_APP_TYPE;

	@xmaker(name = "Q_M_CODE", mappingDbFieldName = "M_CODE")
	private String Q_M_CODE;

	@xmaker(name = "Q_SUPPLIER", mappingDbFieldName = "SUPPLIER")
	private String Q_SUPPLIER;

	// 沒有給adapDbFieldName屬性
	// ,並不會在getSelectConditionByDtoWithXmakerAdapDbFieldName()中併入查詢條件
	/** The q dep no. */
	// 需另外手動給where條件:詳見Query.qDepNoInSqlWhere
	@xmaker(name = "Q_DEP_NO")
	private String Q_DEP_NO;

	/**
	 * Gets the Q_PNO.
	 *
	 * @return [String]
	 */
	public String getQ_PNO() {
		return Q_PNO;
	}

	/**
	 * Sets the Q_PNO.
	 *
	 * @param q_PNO void
	 */
	public void setQ_PNO(String q_PNO) {
		Q_PNO = q_PNO;
	}

	/**
	 * Gets the Q_EMPID.
	 *
	 * @return [String]
	 */
	public String getQ_EMPID() {
		return Q_EMPID;
	}

	/**
	 * Sets the Q_EMPID.
	 *
	 * @param q_EMPID void
	 */
	public void setQ_EMPID(String q_EMPID) {
		Q_EMPID = q_EMPID;
	}

	/**
	 * Gets the Q_SDATE.
	 *
	 * @return [String]
	 */
	public String getQ_SDATE() {
		return Q_SDATE;
	}

	/**
	 * Sets the Q_SDATE.
	 *
	 * @param q_SDATE void
	 */
	public void setQ_SDATE(String q_SDATE) {
		Q_SDATE = q_SDATE;
	}

	/**
	 * Gets the Q_EDATE.
	 *
	 * @return [String]
	 */
	public String getQ_EDATE() {
		return Q_EDATE;
	}

	/**
	 * Sets the Q_EDATE.
	 *
	 * @param q_EDATE void
	 */
	public void setQ_EDATE(String q_EDATE) {
		Q_EDATE = q_EDATE;
	}

	/**
	 * Gets the Q_URGENCY.
	 *
	 * @return [String]
	 */
	public String getQ_URGENCY() {
		return Q_URGENCY;
	}

	/**
	 * Sets the Q_URGENCY.
	 *
	 * @param q_URGENCY void
	 */
	public void setQ_URGENCY(String q_URGENCY) {
		Q_URGENCY = q_URGENCY;
	}

	/**
	 * Gets the Q_SAP_CODE.
	 *
	 * @return [String]
	 */
	public String getQ_SAP_CODE() {
		return Q_SAP_CODE;
	}

	/**
	 * Sets the Q_SAP_CODE.
	 *
	 * @param q_SAP_CODE void
	 */
	public void setQ_SAP_CODE(String q_SAP_CODE) {
		Q_SAP_CODE = q_SAP_CODE;
	}

	/**
	 * Gets the Q_MATERIAL.
	 *
	 * @return [String]
	 */
	public String getQ_MATERIAL() {
		return Q_MATERIAL;
	}

	/**
	 * Sets the Q_MATERIAL.
	 *
	 * @param q_MATERIAL void
	 */
	public void setQ_MATERIAL(String q_MATERIAL) {
		Q_MATERIAL = q_MATERIAL;
	}

	/**
	 * Gets the Q_MFR.
	 *
	 * @return [String]
	 */
	public String getQ_MFR() {
		return Q_MFR;
	}

	/**
	 * Sets the Q_MFR.
	 *
	 * @param q_MFR void
	 */
	public void setQ_MFR(String q_MFR) {
		Q_MFR = q_MFR;
	}

	/**
	 * Gets the Q_STATUS.
	 *
	 * @return [String]
	 */
	public String getQ_STATUS() {
		return Q_STATUS;
	}

	/**
	 * Sets the Q_STATUS.
	 *
	 * @param q_STATUS void
	 */
	public void setQ_STATUS(String q_STATUS) {
		Q_STATUS = q_STATUS;
	}

	/**
	 * Gets the Q_QR_NO.
	 *
	 * @return [String]
	 */
	public String getQ_QR_NO() {
		return Q_QR_NO;
	}

	/**
	 * Sets the Q_QR_NO.
	 *
	 * @param q_QR_NO void
	 */
	public void setQ_QR_NO(String q_QR_NO) {
		Q_QR_NO = q_QR_NO;
	}

	/**
	 * Gets the Q_RECEIPT_UNIT.
	 *
	 * @return [String]
	 */
	public String getQ_RECEIPT_UNIT() {
		return Q_RECEIPT_UNIT;
	}

	/**
	 * Sets the Q_RECEIPT_UNIT.
	 *
	 * @param q_RECEIPT_UNIT void
	 */
	public void setQ_RECEIPT_UNIT(String q_RECEIPT_UNIT) {
		Q_RECEIPT_UNIT = q_RECEIPT_UNIT;
	}

	/**
	 * Gets the Q_DEP_NO.
	 *
	 * @return [String]
	 */
	public String getQ_DEP_NO() {
		return Q_DEP_NO;
	}

	/**
	 * Sets the Q_DEP_NO.
	 *
	 * @param q_DEP_NO void
	 */
	public void setQ_DEP_NO(String q_DEP_NO) {
		Q_DEP_NO = q_DEP_NO;
	}

	public String getQ_APP_TYPE() {
		return Q_APP_TYPE;
	}

	public void setQ_APP_TYPE(String q_APP_TYPE) {
		Q_APP_TYPE = q_APP_TYPE;
	}

	public String getQ_M_CODE() {
		return Q_M_CODE;
	}

	public void setQ_M_CODE(String q_M_CODE) {
		Q_M_CODE = q_M_CODE;
	}

	public String getQ_SUPPLIER() {
		return Q_SUPPLIER;
	}

	public void setQ_SUPPLIER(String q_SUPPLIER) {
		Q_SUPPLIER = q_SUPPLIER;
	}

	/**
	 * To sql.
	 *
	 * @return [String]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public String toSql() throws SQLException, Exception {
		return DtoUtil.queryConditionDtoConvertToSqlWhereString(this) + " " + qDepNoInSqlWhere(this.getQ_DEP_NO());
	}

	/**
	 * 加入部門代號至SQL查詢條件字串.
	 *
	 * @param depno [String]
	 * @return [String]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public String qDepNoInSqlWhere(String depno) throws SQLException, Exception {
		if (!"".equals(depno)) {

			return " AND APPLICANT IN (SELECT EMPID FROM USER_INOFFICE_INFO_VIEW WHERE DEPT_NO = '" + depno + "' )";
		}

		return "";
	}

}
