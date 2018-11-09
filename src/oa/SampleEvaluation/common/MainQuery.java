package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ysp.util.LogUtil;

import oa.SampleEvaluation.common.global.BaseMainQuery;
import oa.SampleEvaluation.common.global.CommonDataObj;
import oa.SampleEvaluation.common.global.UserData;

public class MainQuery extends BaseMainQuery {

	public MainQuery(CommonDataObj cdo) {
		super(cdo);

	}

	// ���o�d���v��SQL����
	public String getQueryRightSql() throws SQLException, Exception {
		String sql = "";
		String loginUserId = cdo.getLoginUserId();
		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();

		boolean isAdmin = isAdmin();
		String queryEmpDepNo = "";
		boolean isSameDepNoInPurch = false;

		// �P�_�n�J�Τ�P�ҭn�d�ߪ��_��H�O�_�P���
		// �ت��O�����ʤ@��or�G�� �P�Ҷ��i���d����
		if (qc.getQueryEmpid() != null && !qc.getQueryEmpid().equals("")) {
			UserData queryUser = new UserData(qc.getQueryEmpid(), innerTalk);
			queryEmpDepNo = queryUser.getDepNo();
			isSameDepNoInPurch = isSameDepNoInPurch(queryEmpDepNo);
		} else if (!qc.getQueryDepNo().equals("")) {
			isSameDepNoInPurch = true;
		}

		// �p�G���O�t�κ޲z���s�դH���έn�d�߱o�_��H�D�P�ҳ��A���[�J�d���v��
		if (!isAdmin && !isSameDepNoInPurch) {

			sql += " and (";
			// �ӽФH���ۤv����
			sql += "(" + tableApplicantFieldName + " in (select handle_user from hr_condition where empid = '"
					+ loginUserId + "')) ";
			// �ۤvñ�ֹL����l
			sql += "or (a." + tablePKName + " in (select distinct " + tablePKName + " from " + tableName
					+ "_FLOWC_HIS where F_INP_ID = '" + loginUserId + "')) ";

			sql += ") ";

		}
		return sql;
	}

	private boolean isSameDepNoInPurch(String queryEmpDepNo) {
		return userData.getDepNo().equals(queryEmpDepNo);
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String getAdvancedCondition() {
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

		StringBuilder advancedSql = new StringBuilder();

		if (!"".equals(empid) && !"admin".equals(empid))
			advancedSql.append("and " + tableApplicantFieldName + " = '" + empid + "' ");
		if (!"".equals(sdate))
			advancedSql.append("and " + tableAppDateFieldName + " >= '" + sdate + "' ");
		if (!"".equals(edate))
			advancedSql.append("and " + tableAppDateFieldName + " <= '" + edate + "' ");

		// status
		advancedSql.append(statusCheck(queryFlowStatus, "b"));
		advancedSql.append(statusCheck(queryFlowStatusCheck, "c"));
		advancedSql.append(statusCheck(queryFlowStatusTp, "d"));
		if (!"".equals(queryId))
			advancedSql.append("and a." + tablePKName + " like '%" + queryId + "%' ");

		advancedSql.append(" and a." + tablePKName + " =  b." + tablePKName);
		if (!"".equals(queryFlowStatusCheck)) {
			advancedSql.append(" and a." + tablePKName + "+'CHECK' =  c.OWN_" + tablePKName);
		}
		if (!"".equals(queryFlowStatusTp)) {
			advancedSql.append(" and a." + tablePKName + "+'TP' =  d.OWN_" + tablePKName);
		}
		if (!"".equals(queryDepNo)) {
			advancedSql.append(" and (APPLICANT in (select empid from hruser where dept_no = '" + queryDepNo + "'))");
		}
		return advancedSql.toString();
	}

	public String getSqlQueryStr() throws SQLException, Exception {
		StringBuilder strSql = new StringBuilder();
		List<String> resultFieldList = cdo.getQuerySpec().getQueryResultView();
		// �渹

		strSql.append("select DISTINCT a.");
		for (String fname : resultFieldList) {
			if (fname.trim().equalsIgnoreCase(tableApplicantFieldName)) {
				strSql.append(getEmpInfoSqlQueryStr(tablePKName, tableApplicantFieldName));

			} else if (fname.trim().equalsIgnoreCase("'ñ�֪��A'")) {
				strSql.append(getFlowStateSqlQueryStr(tablePKName, tableName));

			} else {
				strSql.append(fname);
			}

			strSql.append(",");
		}
		String str = strSql.toString();
		String subFlowcTableNameInSqlStr = "," + tableName + "_CHECK_FLOWC c ," + tableName + "_TP_FLOWC d ";
		SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec) cdo.getQuerySpec();
//		if (qc.queryStatusCheck.equals("")) {
//			subFlowcTableNameInSqlStr = "";
//		}
//		if (qc.queryStatusCheck.equals("")) {
//			subFlowcTableNameInSqlStr = "";
//		}
		str = str.substring(0, str.length() - 1);
		str += " from  " + tableName + " a," + tableName + "_FLOWC b " + subFlowcTableNameInSqlStr + " where 1=1 ";

		// �D�n�d�����+�d�߱���+�v������
		return str + getAdvancedCondition() + getQueryRightSql();
	}

	/**
	 * 
	 * 
	 * @param queryResults          �d�ߵ��G String[][]
	 * @param viewFieldOfResultList �d�ߵ��G������ArrayList
	 * @param c                     Controller
	 * @return
	 * @throws Throwable
	 */
	@Override
	protected String[][] getQueryResultAfterProcess(String[][] queryResults, List<String> viewFieldOfResultList)
			throws Throwable {

		int index = getStatusIndex(viewFieldOfResultList);// ñ�֪��A��Index

		for (int i = 0; i < queryResults.length; i++) {
			// ���o�l�y�{�ثeñ�֪��A
			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();
			// ���o�l�y�{�ثeñ�֪��A
			String tpFlowStatus = getCheckFlowStatus(queryResults[i][0] + "TP").trim();

			// ���o�D�y�{�ثeñ�֪��A
			String mainFlowStatus = queryResults[i][index].trim();

			// �p�G�l�y�{�M�D�y�{������ �b�d�ߵ��G��檺ñ�֪��A�~���"�w�ͮ�"
			queryResults[i][index] = getSignFlowStatus(queryResults[i][0], mainFlowStatus, checkFlowStatus,
					tpFlowStatus);
		}

		return queryResults;
	}

	private String getSignFlowStatus(String id, String mainFlowStatus, String checkFlowStatus, String tpFlowStatus)
			throws Exception {
//		if ((mainFlowStatus.equals("����") || mainFlowStatus.equals("�k��"))
//				&& (checkFlowStatus.equals("����") || checkFlowStatus.equals("�L"))
//				&& (tpFlowStatus.equals("����") || tpFlowStatus.equals("�L"))) {
//			return "<font color=blue>(�w�ͮ�)</font>�i�D�y�{:" + mainFlowStatus + "�j" + "�i����y�{:" + checkFlowStatus + "�j"
//					+ "�i����y�{:" + tpFlowStatus + "�j";
//		}
//		// �p�G�l�y�{�M�D�y�{���@�襼���� �h�i��[�u�B�z
//		else {
//			return "<font color=red>ñ�֤�</font>" + "�i�D�y�{:" + mainFlowStatus
//					+ getCurrentFlowGateAndApprover(cdo.getTablePKName(), id) + "�j" + "�i����y�{:" + checkFlowStatus
//					+ getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "CHECK") + "�j" + "�i�ջs�y�{:"
//					+ tpFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "TP") + "�j";
//		}

		return "�i�D�y�{:" + mainFlowStatus + getCurrentFlowGateAndApprover(cdo.getTablePKName(), id) + "�j" + "�i����y�{:"
				+ checkFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "CHECK") + "�j"
				+ "�i�ջs�y�{:" + tpFlowStatus + getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "TP")
				+ "�j";
	}

	private int getStatusIndex(List<String> viewFieldOfResultList) {
		int index = -1;
		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
			if (viewFieldOfResultList.get(i).contains("'ñ�֪��A'")) {
				index = i;
			}
		}
		return index;
	}

	public String getCheckFlowStatus(String ownPno) throws Throwable {
		String type = "TP";
		if (ownPno.contains("CHECK")) {
			type = "CHECK";
		}
		String sql = "SELECT F_INP_STAT FROM SAMPLE_EVALUATION_" + type + "_FLOWC WHERE OWN_PNO='" + ownPno + "'";
		String[][] ret = null;

		ret = innerTalk.queryFromPool(sql);
		if (ret == null || ret.length == 0) {
			// �p�G�S������y�{��������w����
			return "�L";
		} else {
			return ret[0][0];
		}

	}

}
