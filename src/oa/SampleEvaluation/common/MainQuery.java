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

			String tp = sampleEvaluationTpFlow != null ? sampleEvaluationTpFlow.getfInpStat() : "�L";
			String ck = sampleEvaluationCheckFlow != null ? sampleEvaluationCheckFlow.getfInpStat() : "�L";
			String type = sampleEvaluation.getAppType();
			String u = sampleEvaluation.getUrgency();
			String appDate = sampleEvaluation.getAppDate();
			System.out.println(sampleEvaluation.getPno() + " " + type + " " + u + " " + appDate + " "
					+ sampleEvaluationFlow.getfInpStat() + " " + tp + " " + ck + " " + "����" + "ñ�֬���");
			array[i][0] = sampleEvaluation.getPno();
			UserData queryUser = new UserData(sampleEvaluation.getApplicant(), innerTalk);
			array[i][1] = queryUser.getEmpid() + " " + queryUser.getHecname() + " " + queryUser.getDepName();
			array[i][2] = type;
			array[i][3] = u;
			array[i][4] = appDate;
			array[i][5] = sampleEvaluationFlow.getfInpStat() + " ����:" + tp + " �ջs:" + ck;
			array[i][6] = "����";
			array[i][7] = "ñ�֬���";
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

	// ���o�d���v��SQL����
	public String getQueryRightSqlForService() throws SQLException, Exception {
		String sql = "";
		String loginUserId = cdo.getLoginUserId();
		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();

		String queryEmpDepNo = "";
		boolean isSameDepNoInPurch = false;

		// �P�_�n�J�Τ�P�ҭn�d�ߪ��_��H�O�_�P���
		// �ت��O�����ʤ@��or�G�� �P�Ҷ��i���d����
		if (qc.getQueryEmpid() != null && !qc.getQueryEmpid().equals("")) {
			UserData queryUser = new UserData(qc.getQueryEmpid(), innerTalk);
			queryEmpDepNo = queryUser.getDepNo();
			isSameDepNoInPurch = isSameDepNoInPurch(queryEmpDepNo);
		} else if (!qc.getQueryDepNo().equals("")) {
			isSameDepNoInPurch = isSameDepNoInPurch(qc.getQueryDepNo());
		}
		System.out.println("isAdmin==>" + isAdmin());
		System.out.println("isSameDepNoInPurch==>" + isSameDepNoInPurch);
		// �p�G���O�t�κ޲z���s�դH���έn�d�߱o�_��H�D�P�ҳ��A���[�J�d���v��
		if (!isAdmin() && !isSameDepNoInPurch) {

			sql += " and (";
			// �ӽФH���ۤv����
			sql += "(" + tableApplicantFieldName + " in (select handle_user from hr_condition where empid = '"
					+ loginUserId + "')) ";
			// �ۤvñ�ֹL����l
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
		// �]�m�d�����
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
	// ���o�d���v��SQL����
//	public String getQueryRightSql() throws SQLException, Exception {
//		String sql = "";
//		String loginUserId = cdo.getLoginUserId();
//		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();
//
//		boolean isAdmin = isAdmin();
//		String queryEmpDepNo = "";
//		boolean isSameDepNoInPurch = false;
//
//		// �P�_�n�J�Τ�P�ҭn�d�ߪ��_��H�O�_�P���
//		// �ت��O�����ʤ@��or�G�� �P�Ҷ��i���d����
//		if (qc.getQueryEmpid() != null && !qc.getQueryEmpid().equals("")) {
//			UserData queryUser = new UserData(qc.getQueryEmpid(), innerTalk);
//			queryEmpDepNo = queryUser.getDepNo();
//			isSameDepNoInPurch = isSameDepNoInPurch(queryEmpDepNo);
//		} else if (!qc.getQueryDepNo().equals("")) {
//			isSameDepNoInPurch = true;
//		}
//
//		// �p�G���O�t�κ޲z���s�դH���έn�d�߱o�_��H�D�P�ҳ��A���[�J�d���v��
//		if (!isAdmin && !isSameDepNoInPurch) {
//
//			sql += " and (";
//			// �ӽФH���ۤv����
//			sql += "(" + tableApplicantFieldName + " in (select handle_user from hr_condition where empid = '"
//					+ loginUserId + "')) ";
//			// �ۤvñ�ֹL����l
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
//		// �]�m�d�����
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
//		// �渹
//
//		strSql.append("select DISTINCT a.");
//		for (String fname : resultFieldList) {
//			if (fname.trim().equalsIgnoreCase(tableApplicantFieldName)) {
//				strSql.append(getEmpInfoSqlQueryStr(tablePKName, tableApplicantFieldName));
//
//			} else if (fname.trim().equalsIgnoreCase("'ñ�֪��A'")) {
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
//		// �D�n�d�����+�d�߱���+�v������
//		return str + getAdvancedCondition() + getQueryRightSql();
//	}

	/**
	 * 
	 * 
	 * @param queryResults          �d�ߵ��G String[][]
	 * @param viewFieldOfResultList �d�ߵ��G������ArrayList
	 * @param c                     Controller
	 * @return
	 * @throws Throwable
	 */
//	@Override
//	protected String[][] getQueryResultAfterProcess(String[][] queryResults, List<String> viewFieldOfResultList)
//			throws Throwable {
//
//		int index = getStatusIndex(viewFieldOfResultList);// ñ�֪��A��Index
//
//		for (int i = 0; i < queryResults.length; i++) {
//			// ���o�l�y�{�ثeñ�֪��A
//			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();
//			// ���o�l�y�{�ثeñ�֪��A
//			String tpFlowStatus = getCheckFlowStatus(queryResults[i][0] + "TP").trim();
//
//			// ���o�D�y�{�ثeñ�֪��A
//			String mainFlowStatus = queryResults[i][index].trim();
//
//			// �p�G�l�y�{�M�D�y�{������ �b�d�ߵ��G��檺ñ�֪��A�~���"�w�ͮ�"
//			queryResults[i][index] = getSignFlowStatus(queryResults[i][0], mainFlowStatus, checkFlowStatus,
//					tpFlowStatus);
//		}
//
//		return queryResults;
//	}

//	private String getSignFlowStatus(String id, String mainFlowStatus, String checkFlowStatus, String tpFlowStatus)
//			throws Exception {
////		if ((mainFlowStatus.equals("����") || mainFlowStatus.equals("�k��"))
////				&& (checkFlowStatus.equals("����") || checkFlowStatus.equals("�L"))
////				&& (tpFlowStatus.equals("����") || tpFlowStatus.equals("�L"))) {
////			return "<font color=blue>(�w�ͮ�)</font>�i�D�y�{:" + mainFlowStatus + "�j" + "�i����y�{:" + checkFlowStatus + "�j"
////					+ "�i����y�{:" + tpFlowStatus + "�j";
////		}
////		// �p�G�l�y�{�M�D�y�{���@�襼���� �h�i��[�u�B�z
////		else {
////			return "<font color=red>ñ�֤�</font>" + "�i�D�y�{:" + mainFlowStatus
////					+ getCurrentFlowGateAndApprover(cdo.getTablePKName(), id) + "�j" + "�i����y�{:" + checkFlowStatus
////					+ getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "CHECK") + "�j" + "�i�ջs�y�{:"
////					+ tpFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "TP") + "�j";
////		}
//
//		return "�i�D�y�{:" + mainFlowStatus + getCurrentFlowGateAndApprover(cdo.getTablePKName(), id) + "�j" + "�i����y�{:"
//				+ checkFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "CHECK") + "�j"
//				+ "�i�ջs�y�{:" + tpFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "TP")
//				+ "�j";
//	}

//	private int getStatusIndex(List<String> viewFieldOfResultList) {
//		int index = -1;
//		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
//			if (viewFieldOfResultList.get(i).contains("'ñ�֪��A'")) {
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
//			// �p�G�S������y�{��������w����
//			return "�L";
//		} else {
//			return ret[0][0];
//		}
//
//	}

}