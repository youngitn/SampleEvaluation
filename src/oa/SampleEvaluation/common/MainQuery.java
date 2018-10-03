package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.SampleEvaluationActionController;

public class MainQuery {
	CommonDataObj cdo;
	BaseService service;
	String tablePKName;
	String tableName;
	String tableApplicantFieldName;
	String tableAppDateFieldName;
	talk innerTalk;

	public MainQuery(CommonDataObj cdo) {

		this.cdo = cdo;
		this.service = new BaseService(new SampleEvaluationActionController());
		tablePKName = cdo.getTablePKName();
		tableName = cdo.getTableName();
		tableApplicantFieldName = cdo.getTableApplicantFieldName();
		tableAppDateFieldName = cdo.getTableAppDateFieldName();
		innerTalk = cdo.getTalk();
	}

	// ���o�d���v��SQL����
	private String getQueryRightSql() throws SQLException, Exception {
		String sql = "";
		boolean isadmin = isAdmin(cdo.getLoginUserId());
		// �p�G���O�t�κ޲z���s�դH���A���[�J�d���v��
		if (!isadmin) {
			sql += " and (";
			// �ӽФH���ۤv����
			sql += "(" + cdo.getTableApplicantFieldName() + " in (select handle_user from hr_condition where empid = '"
					+ cdo.getLoginUserId() + "')) ";
			// �ۤvñ�ֹL����l
			sql += "or (a." + tablePKName + " in (select distinct " + tablePKName + " from " + tableName
					+ "_FLOWC_HIS where F_INP_ID = '" + cdo.getLoginUserId() + "')) ";

			sql += ") ";
		}
		return sql;
	}

	/**
	 * TODO �w�p�T�w���W�٥H²��
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

		StringBuilder advanced_sql = new StringBuilder();
		if (!"".equals(empid))
			advanced_sql.append("and " + tableApplicantFieldName + " = '" + empid + "' ");
		if (!"".equals(sdate))
			advanced_sql.append("and " + tableAppDateFieldName + " >= '" + sdate + "' ");
		if (!"".equals(edate))
			advanced_sql.append("and " + tableAppDateFieldName + " <= '" + edate + "' ");

		// status
		if ("�w����".equals(queryFlowStatus))
			advanced_sql.append("and b.F_INP_STAT = '����' ");
		if ("ñ�֤�".equals(queryFlowStatus))
			advanced_sql.append("and b.F_INP_STAT not in ('����','����') ");
		if ("�ݳB�z".equals(queryFlowStatus))
			advanced_sql.append("and b.F_INP_STAT = '�ݳB�z' ");
		if ("�w����".equals(queryFlowStatusCheck))
			advanced_sql.append("and c.F_INP_STAT = '����' ");
		if ("ñ�֤�".equals(queryFlowStatusCheck))
			advanced_sql.append("and c.F_INP_STAT not in ('����','����') ");
		if ("�ݳB�z".equals(queryFlowStatusCheck))
			advanced_sql.append("and c.F_INP_STAT = '�ݳB�z' ");

		if (!"".equals(queryId))
			advanced_sql.append("and a." + tablePKName + " like '%" + queryId + "%' ");

		advanced_sql.append(" and a." + tablePKName + " =  b." + tablePKName);
		if (!"".equals(queryFlowStatusCheck)) {
			advanced_sql.append(" and a." + tablePKName + "+'CHECK' =  c.OWN_" + tablePKName);
		}

		return advanced_sql.toString();
	}

	public String getSqlQueryStr() throws SQLException, Exception {
		StringBuilder strSql = new StringBuilder();
		// String keyName = cdo.getTablePKName();
		// String targetEmpidFieldName = cdo.getTableApplicantFieldName().trim();// TODO
		// ��²��
		ArrayList<String> resultFieldList = cdo.getQuerySpec().getQueryResultView();
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
		str = str.substring(0, str.length() - 1);
		str += " from  " + tableName + " a," + tableName + "_FLOWC b, " + tableName + "_CHECK_FLOWC c " + " where 1=1 ";

		// �D�n�d�����+�d�߱���+�v������
		return str + getAdvancedCondition() + getQueryRightSql();
	}

	/**
	 * �ЫإD�d��SQL�r�� ���u��Ƴ��� �Nhruser�Mhruser_dept_bas �@��t�d�� ���o���u�򥻸��
	 * (�m�W-�u��-�����W��)<--�N��]���P�@��� �L�k��W�ϥ�
	 * 
	 * @param key
	 * @param targetEmpidFieldName
	 * @return
	 */
	public String getEmpInfoSqlQueryStr(String key, String targetEmpidFieldName) {

		// ���u�򥻸�� �m�W-�u��-�����W��
		return "(select (hecname+'-'+empid) from hruser where empid=a." + targetEmpidFieldName + ")+'-'+"
				+ "(select dep_name from hruser_dept_bas where dep_no in (select dept_no from hruser where empid=a."
				+ targetEmpidFieldName + "))";
	}

	public String getFlowStateSqlQueryStr(String key, String tableName) {
		return "(select f_inp_stat from " + tableName + "_flowc where " + key + "=a." + key + ") ";
	}

	public String[][] getQueryResult() throws Throwable {
		String sql = "";
		sql = getSqlQueryStr();
		String[][] list = cdo.getTalk().queryFromPool(sql);
		if (list.length > 0) {
			return getQueryResultAfterProcess(list, cdo.getQuerySpec().getQueryResultView());
		}
		return list;

	}

	/**
	 * TODO ���ʦ�mainQuery ��ñ�֪��A����ܥ[�u
	 * 
	 * @param queryResults          �d�ߵ��G String[][]
	 * @param viewFieldOfResultList �d�ߵ��G������ArrayList
	 * @param c                     Controller
	 * @return
	 * @throws Throwable
	 */
	private String[][] getQueryResultAfterProcess(String[][] queryResults, ArrayList<String> viewFieldOfResultList)
			throws Throwable {

		int sign_flow_status_index = getStatusIndex(viewFieldOfResultList);// ñ�֪��A��Index

		for (int i = 0; i < queryResults.length; i++) {
			// ���o�l�y�{�ثeñ�֪��A
			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();

			// ���o�D�y�{�ثeñ�֪��A
			String mainFlowStatus = queryResults[i][sign_flow_status_index].trim();

			// �p�G�l�y�{�M�D�y�{������ �b�d�ߵ��G��檺ñ�֪��A�~���"�w�ͮ�"
			queryResults[i][sign_flow_status_index] = getSignFlowStatus(queryResults[i][0], mainFlowStatus,
					checkFlowStatus);
		}

		return queryResults;
	}

	private String getSignFlowStatus(String id, String mainFlowStatus, String checkFlowStatus)
			throws SQLException, Exception {
		if ((mainFlowStatus.equals("����") || mainFlowStatus.equals("�k��"))
				&& (checkFlowStatus.equals("����") || checkFlowStatus.equals("�L"))) {
			return "<font color=blue>(�w�ͮ�)</font>�i�D�y�{:" + mainFlowStatus + "�j" + "�i����y�{:" + checkFlowStatus + "�j";
		}
		// �p�G�l�y�{�M�D�y�{���@�襼���� �h�i��[�u�B�z
		else {
			return "<font color=red>ñ�֤�</font>" + "�i�D�y�{:" + mainFlowStatus
					+ getCurrentFlowGateAndApprover(cdo.getTablePKName(), id) + "�j" + "�i����y�{:" + checkFlowStatus
					+ getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), id + "CHECK") + "�j";
		}
	}

	private int getStatusIndex(ArrayList<String> viewFieldOfResultList) {
		int sign_flow_status_index = -1;
		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
			if (viewFieldOfResultList.get(i).contains("'ñ�֪��A'")) {
				sign_flow_status_index = i;
			}
		}
		return sign_flow_status_index;
	}

	/**
	 * ���o�ثeñ�����d�W�ٻPñ�֤H����Ʀr�� EX:"-(���d�W��-ñ�֤H1,ñ�֤H2..)" TODO ���ʦ�mainQuery
	 * 
	 * @param pkName  ��ƪ�pk���W��
	 * @param pkValue ��ƪ�pk��
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	private String getCurrentFlowGateAndApprover(String pkName, String pkValue) throws SQLException, Exception {
		Vector<String> people = service.getApprovablePeople(cdo.getFunctionName(),
				"a." + pkName + "='" + pkValue + "'");
		StringBuffer sb = new StringBuffer();
		if (people != null) {
			if (people.size() != 0) {
				sb.append("-(");
				for (int j = 0; j < people.size(); j++) {
					if (j != 0)
						sb.append(",");
					String id1 = (String) people.elementAt(j);
					UserData u = new UserData(id1, innerTalk);
					String name1 = u.getHecname();
					u = null;
					sb.append(name1 + "-" + id1);
				}
				sb.append(")");
			}
		}

		return sb.toString();
	}

	public String getCheckFlowStatus(String ownPno) {
		String sql = "SELECT F_INP_STAT FROM SAMPLE_EVALUATION_CHECK_FLOWC WHERE OWN_PNO='" + ownPno + "'";
		String[][] ret = null;
		try {
			ret = innerTalk.queryFromPool(sql);
			if (ret.length == 0) {
				// �p�G�S������y�{��������w����
				return "�L";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret[0][0];
	}

	// �T�w�d���v��
	private boolean isAdmin(String user) throws SQLException, Exception {
		String sql_hruser_dept = "select id from hruser_dept where dep_no = '1001' or ID='52116'";
		String[][] ret_hruser_dept = innerTalk.queryFromPool(sql_hruser_dept);
		if (ret_hruser_dept.length > 0) {
			for (int i = 0; i < ret_hruser_dept.length; i++) {
				if (ret_hruser_dept[i][0].equals(user)) {
					return true;
				}
			}
		}
		return false;
	}

}
