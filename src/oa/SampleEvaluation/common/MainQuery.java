package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.BaseMainQuery;
import oa.SampleEvaluation.common.global.CommonDataObj;
import oa.SampleEvaluation.common.global.UserData;
import oa.SampleEvaluation.dao.SampleEvaluationFlowcService;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationFlowc;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowc;

public class MainQuery extends BaseMainQuery {

	public MainQuery(CommonDataObj cdo) {
		super(cdo);

	}

	public String[][] testtest() throws Throwable {
		talk t = cdo.getTalk();
		String condition = " where 1=1 " + getQueryRightSqlForService() + getAdvancedConditionForService();
		System.out.println("condition==>" + condition);
		BaseDao dao = new SampleEvaluationService(t);
		ArrayList<SampleEvaluation> s = (ArrayList<SampleEvaluation>) dao.findByCondition(condition);
		String[][] array = new String[s.size()][8];
		System.out.println(s.size());
		int i = 0;
		for (SampleEvaluation sampleEvaluation : s) {
			// array[i][0] = sampleEvaluation.getPno();
			SampleEvaluationFlowc sampleEvaluationFlow = (SampleEvaluationFlowc) new SampleEvaluationFlowcService(t)
					.findById(sampleEvaluation.getPno());
			SampleEvaluationTpFlowc sampleEvaluationTpFlow = null;
			SampleEvaluationCheckFlowc sampleEvaluationCheckFlow = null;
			if (sampleEvaluation.getIsCheck().equals("")) {
				sampleEvaluationCheckFlow = (SampleEvaluationCheckFlowc) new SampleEvaluationCheckFlowcService(t)
						.findById(sampleEvaluation.getPno() + "CHECK");
			}
			if (sampleEvaluation.getIsTrialProduction().equals("")) {
				sampleEvaluationTpFlow = (SampleEvaluationTpFlowc) new SampleEvaluationTpFlowcService(t)
						.findById(sampleEvaluation.getPno() + "TP");
			}

			String tp = sampleEvaluationTpFlow != null ? sampleEvaluationTpFlow.getfInpStat() : "無";
			String ck = sampleEvaluationCheckFlow != null ? sampleEvaluationCheckFlow.getfInpStat() : "無";
			String type = sampleEvaluation.getAppType();
			String u = sampleEvaluation.getUrgency();
			String appDate = sampleEvaluation.getAppDate();
			System.out.println(sampleEvaluation.getPno() + " " + type + " " + u + " " + appDate + " "
					+ sampleEvaluationFlow.getfInpStat() + " " + tp + " " + ck + " " + "明細" + "簽核紀錄");
			array[i][0] = sampleEvaluation.getPno();
			UserData queryUser = new UserData(sampleEvaluation.getApplicant(), innerTalk);
			array[i][1] = queryUser.getEmpid() + " " + queryUser.getHecname() + " " + queryUser.getDepName();
			array[i][2] = type;
			array[i][3] = u;
			array[i][4] = appDate;
			array[i][5] = sampleEvaluationFlow.getfInpStat() + " 請驗:" + tp + " 試製:" + ck;
			array[i][6] = "明細";
			array[i][7] = "簽核紀錄";
			i++;
		}
		// String[][] aaa = t.queryFromPool("select * from sample_Evaluation");
		System.out.println();
		for (String[] strings : array) {
			System.out.println(strings[0]);

		}
//		try {
		// setTableData("QUERY_LIST", array);
		return array;
//		} catch (NullPointerException e) {
//			System.out.println("");
//		}

//
	}

	// 取得查詢權限SQL條件
	public String getQueryRightSqlForService() throws SQLException, Exception {
		String sql = "";
		String loginUserId = cdo.getLoginUserId();
		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();

		String queryEmpDepNo = "";
		boolean isSameDepNoInPurch = false;

		// 判斷登入用戶與所要查詢的起單人是否同單位
		// 目的是讓採購一課or二課 同課間可互查紀錄
		if (qc.getQueryEmpid() != null && !qc.getQueryEmpid().equals("")) {
			UserData queryUser = new UserData(qc.getQueryEmpid(), innerTalk);
			queryEmpDepNo = queryUser.getDepNo();
			isSameDepNoInPurch = isSameDepNoInPurch(queryEmpDepNo);
		} else if (!qc.getQueryDepNo().equals("")) {
			isSameDepNoInPurch = isSameDepNoInPurch(qc.getQueryDepNo());
		}
		System.out.println("isAdmin==>" + isAdmin());
		System.out.println("isSameDepNoInPurch==>" + isSameDepNoInPurch);
		// 如果不是系統管理員群組人員或要查詢得起單人非同課單位，須加入查詢權限
		if (!isAdmin() && !isSameDepNoInPurch) {

			sql += " and (";
			// 申請人為自己部屬
			sql += "(" + tableApplicantFieldName + " in (select handle_user from hr_condition where empid = '"
					+ loginUserId + "')) ";
			// 自己簽核過的單子
			sql += "or (" + tablePKName + " in (select distinct " + tablePKName + " from " + tableName
					+ "_FLOWC_HIS where F_INP_ID = '" + loginUserId + "')) ";

			sql += ") ";

		}
		return sql;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String getAdvancedConditionForService() {
		// 設置查詢欄位
		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();
		String queryId = qc.getQueryBillId();
		String empid = qc.getQueryEmpid();
		String sdate = qc.getQueryReqSDate();
		String edate = qc.getQueryReqEDate();
		String queryFlowStatus = qc.getQueryStatus();
		String queryFlowStatusCheck = qc.getQueryStatusCheck();
		String queryFlowStatusTp = qc.getQueryStatusTp();
		String queryDepNo = qc.getQueryDepNo();
		String queryUrgency = qc.getQueryUrgency();
		String querySapCode = qc.getQuerySapCode();
		String queryMaterial = qc.getQueryMaterial();
		String queryMfr = qc.getQueryMfr();

		StringBuilder advancedSql = new StringBuilder();

		if (!"".equals(empid) && !"admin".equals(empid))
			advancedSql.append("and " + tableApplicantFieldName + " = '" + empid + "' ");
		if (!"".equals(sdate))
			advancedSql.append("and " + tableAppDateFieldName + " >= '" + sdate + "' ");
		if (!"".equals(edate))
			advancedSql.append("and " + tableAppDateFieldName + " <= '" + edate + "' ");

		if (!"".equals(queryUrgency))
			advancedSql.append("and Urgency = '" + queryUrgency + "' ");
		if (!"".equals(querySapCode))
			advancedSql.append("and Sap_Code like '%" + querySapCode + "%' ");
		if (!"".equals(queryMaterial))
			advancedSql.append("and Material like '%" + queryMaterial + "%' ");
		if (!"".equals(queryMfr))
			advancedSql.append("and Mfr like '%" + queryMfr + "%' ");

		// status
		advancedSql.append(statusCheck(queryFlowStatus, "b"));
		advancedSql.append(statusCheck(queryFlowStatusCheck, "c"));
		advancedSql.append(statusCheck(queryFlowStatusTp, "d"));
		if (!"".equals(queryId))
			advancedSql.append("and " + tablePKName + " like '%" + queryId + "%' ");

		// advancedSql.append(" and a." + tablePKName + " = b." + tablePKName);
//		if (!"".equals(queryFlowStatusCheck)) {
//			advancedSql.append(" and a." + tablePKName + "+'CHECK' =  c.OWN_" + tablePKName);
//		}
//		if (!"".equals(queryFlowStatusTp)) {
//			advancedSql.append(" and a." + tablePKName + "+'TP' =  d.OWN_" + tablePKName);
//		}
		if (!"".equals(queryDepNo)) {
			advancedSql.append(" and (APPLICANT in (select empid from hruser where dept_no = '" + queryDepNo + "'))");
		}
		return advancedSql.toString();
	}

	private boolean isSameDepNoInPurch(String queryEmpDepNo) {
		return userData.getDepNo().equals(queryEmpDepNo);
	}
	// 取得查詢權限SQL條件
//	public String getQueryRightSql() throws SQLException, Exception {
//		String sql = "";
//		String loginUserId = cdo.getLoginUserId();
//		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();
//
//		boolean isAdmin = isAdmin();
//		String queryEmpDepNo = "";
//		boolean isSameDepNoInPurch = false;
//
//		// 判斷登入用戶與所要查詢的起單人是否同單位
//		// 目的是讓採購一課or二課 同課間可互查紀錄
//		if (qc.getQueryEmpid() != null && !qc.getQueryEmpid().equals("")) {
//			UserData queryUser = new UserData(qc.getQueryEmpid(), innerTalk);
//			queryEmpDepNo = queryUser.getDepNo();
//			isSameDepNoInPurch = isSameDepNoInPurch(queryEmpDepNo);
//		} else if (!qc.getQueryDepNo().equals("")) {
//			isSameDepNoInPurch = true;
//		}
//
//		// 如果不是系統管理員群組人員或要查詢得起單人非同課單位，須加入查詢權限
//		if (!isAdmin && !isSameDepNoInPurch) {
//
//			sql += " and (";
//			// 申請人為自己部屬
//			sql += "(" + tableApplicantFieldName + " in (select handle_user from hr_condition where empid = '"
//					+ loginUserId + "')) ";
//			// 自己簽核過的單子
//			sql += "or (a." + tablePKName + " in (select distinct " + tablePKName + " from " + tableName
//					+ "_FLOWC_HIS where F_INP_ID = '" + loginUserId + "')) ";
//
//			sql += ") ";
//
//		}
//		return sql;
//	}
//

//	
//	public String getAdvancedCondition() {
//		// 設置查詢欄位
//		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();
//		String queryId = qc.getQueryBillId();
//		String empid = qc.getQueryEmpid();
//		String sdate = qc.getQueryReqSDate();
//		String edate = qc.getQueryReqEDate();
//		String queryFlowStatus = qc.getQueryStatus();
//		String queryFlowStatusCheck = qc.getQueryStatusCheck();
//		String queryFlowStatusTp = qc.getQueryStatusTp();
//		String queryDepNo = qc.getQueryDepNo();
//		String queryUrgency = qc.getQueryUrgency();
//		String querySapCode = qc.getQuerySapCode();
//		String queryMaterial = qc.getQueryMaterial();
//		String queryMfr = qc.getQueryMfr();
//
//		StringBuilder advancedSql = new StringBuilder();
//
//		if (!"".equals(empid) && !"admin".equals(empid))
//			advancedSql.append("and " + tableApplicantFieldName + " = '" + empid + "' ");
//		if (!"".equals(sdate))
//			advancedSql.append("and " + tableAppDateFieldName + " >= '" + sdate + "' ");
//		if (!"".equals(edate))
//			advancedSql.append("and " + tableAppDateFieldName + " <= '" + edate + "' ");
//
//		if (!"".equals(queryUrgency))
//			advancedSql.append("and Urgency = '" + queryUrgency + "' ");
//		if (!"".equals(querySapCode))
//			advancedSql.append("and Sap_Code like '%" + querySapCode + "%' ");
//		if (!"".equals(queryMaterial))
//			advancedSql.append("and Material like '%" + queryMaterial + "%' ");
//		if (!"".equals(queryMfr))
//			advancedSql.append("and Mfr like '%" + queryMfr + "%' ");
//
//		// status
//		advancedSql.append(statusCheck(queryFlowStatus, "b"));
//		advancedSql.append(statusCheck(queryFlowStatusCheck, "c"));
//		advancedSql.append(statusCheck(queryFlowStatusTp, "d"));
//		if (!"".equals(queryId))
//			advancedSql.append("and a." + tablePKName + " like '%" + queryId + "%' ");
//
//		advancedSql.append(" and a." + tablePKName + " =  b." + tablePKName);
//		if (!"".equals(queryFlowStatusCheck)) {
//			advancedSql.append(" and a." + tablePKName + "+'CHECK' =  c.OWN_" + tablePKName);
//		}
//		if (!"".equals(queryFlowStatusTp)) {
//			advancedSql.append(" and a." + tablePKName + "+'TP' =  d.OWN_" + tablePKName);
//		}
//		if (!"".equals(queryDepNo)) {
//			advancedSql.append(" and (APPLICANT in (select empid from hruser where dept_no = '" + queryDepNo + "'))");
//		}
//		return advancedSql.toString();
//	}

//	public String getSqlQueryStr() throws SQLException, Exception {
//		StringBuilder strSql = new StringBuilder();
//		List<String> resultFieldList = cdo.getQuerySpec().getQueryResultView();
//		// 單號
//
//		strSql.append("select DISTINCT a.");
//		for (String fname : resultFieldList) {
//			if (fname.trim().equalsIgnoreCase(tableApplicantFieldName)) {
//				strSql.append(getEmpInfoSqlQueryStr(tablePKName, tableApplicantFieldName));
//
//			} else if (fname.trim().equalsIgnoreCase("'簽核狀態'")) {
//				strSql.append(getFlowStateSqlQueryStr(tablePKName, tableName));
//
//			} else {
//				strSql.append(fname);
//			}
//
//			strSql.append(",");
//		}
//		String str = strSql.toString();
//		String subFlowcTableNameInSqlStr = "";
//
//		// subFlowcTableNameInSqlStr = "," + tableName + "_CHECK_FLOWC c ," + tableName
//		// + "_TP_FLOWC d ";
//		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();
////		if (qc.queryStatusCheck.equals("")) {
////			subFlowcTableNameInSqlStr = "";
////		}
////		if (qc.queryStatusCheck.equals("")) {
////			subFlowcTableNameInSqlStr = "";
////		}
//		str = str.substring(0, str.length() - 1);
//		str += " from  " + tableName + " a," + tableName + "_FLOWC b " + subFlowcTableNameInSqlStr + " where 1=1 ";
//
//		// 主要查詢欄位+查詢條件+權限控制
//		return str + getAdvancedCondition() + getQueryRightSql();
//	}

	/**
	 * 
	 * 
	 * @param queryResults          查詢結果 String[][]
	 * @param viewFieldOfResultList 查詢結果顯示欄位ArrayList
	 * @param c                     Controller
	 * @return
	 * @throws Throwable
	 */
//	@Override
//	protected String[][] getQueryResultAfterProcess(String[][] queryResults, List<String> viewFieldOfResultList)
//			throws Throwable {
//
//		int index = getStatusIndex(viewFieldOfResultList);// 簽核狀態的Index
//
//		for (int i = 0; i < queryResults.length; i++) {
//			// 取得子流程目前簽核狀態
//			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();
//			// 取得子流程目前簽核狀態
//			String tpFlowStatus = getCheckFlowStatus(queryResults[i][0] + "TP").trim();
//
//			// 取得主流程目前簽核狀態
//			String mainFlowStatus = queryResults[i][index].trim();
//
//			// 如果子流程和主流程都結案 在查詢結果表格的簽核狀態才顯示"已生效"
//			queryResults[i][index] = getSignFlowStatus(queryResults[i][0], mainFlowStatus, checkFlowStatus,
//					tpFlowStatus);
//		}
//
//		return queryResults;
//	}

//	private String getSignFlowStatus(String id, String mainFlowStatus, String checkFlowStatus, String tpFlowStatus)
//			throws Exception {
////		if ((mainFlowStatus.equals("結案") || mainFlowStatus.equals("歸檔"))
////				&& (checkFlowStatus.equals("結案") || checkFlowStatus.equals("無"))
////				&& (tpFlowStatus.equals("結案") || tpFlowStatus.equals("無"))) {
////			return "<font color=blue>(已生效)</font>【主流程:" + mainFlowStatus + "】" + "【請驗流程:" + checkFlowStatus + "】"
////					+ "【請驗流程:" + tpFlowStatus + "】";
////		}
////		// 如果子流程和主流程有一方未結案 則進行加工處理
////		else {
////			return "<font color=red>簽核中</font>" + "【主流程:" + mainFlowStatus
////					+ getCurrentFlowGateAndApprover(cdo.getTablePKName(), id) + "】" + "【請驗流程:" + checkFlowStatus
////					+ getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "CHECK") + "】" + "【試製流程:"
////					+ tpFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "TP") + "】";
////		}
//
//		return "【主流程:" + mainFlowStatus + getCurrentFlowGateAndApprover(cdo.getTablePKName(), id) + "】" + "【請驗流程:"
//				+ checkFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "CHECK") + "】"
//				+ "【試製流程:" + tpFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "TP")
//				+ "】";
//	}

//	private int getStatusIndex(List<String> viewFieldOfResultList) {
//		int index = -1;
//		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
//			if (viewFieldOfResultList.get(i).contains("'簽核狀態'")) {
//				index = i;
//			}
//		}
//		return index;
//	}

//	public String getCheckFlowStatus(String ownPno) throws Throwable {
//		String type = "TP";
//		if (ownPno.contains("CHECK")) {
//			type = "CHECK";
//		}
//		String sql = "SELECT F_INP_STAT FROM SAMPLE_EVALUATION_" + type + "_FLOWC WHERE OWN_PNO='" + ownPno + "'";
//		String[][] ret = null;
//
//		ret = innerTalk.queryFromPool(sql);
//		if (ret == null || ret.length == 0) {
//			// 如果沒有請驗流程視為請驗已結案
//			return "無";
//		} else {
//			return ret[0][0];
//		}
//
//	}

}