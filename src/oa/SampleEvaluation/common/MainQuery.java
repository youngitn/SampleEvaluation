package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.ysp.service.BaseService;

public class MainQuery {
	CommonDataObj cdo;
	BaseService service;

	public MainQuery(CommonDataObj cdo) {

		this.cdo = cdo;
		service = cdo.getService();
	}

	/**
	 * TODO �w�p�T�w���W�٥H²��
	 * 
	 * @return
	 */
	public String getAdvancedCondition() {

		String tablePKName = cdo.getTablePKName();
		String queryId = service.getValue("QUERY_PNO");

		String empid = service.getValue("QUERY_EMP_ID");
		String sdate = service.getValue("QUERY_REQ_SDATE");
		String edate = service.getValue("QUERY_REQ_EDATE");
		String queryFlowStatus = service.getValue("r_status");

		StringBuilder advanced_sql = new StringBuilder();
		if (!"".equals(empid))
			advanced_sql.append("and " + cdo.getTableApplicantFieldName() + " = '" + empid + "' ");
		if (!"".equals(sdate))
			advanced_sql.append("and APP_DATE >= '" + sdate + "' ");
		if (!"".equals(edate))
			advanced_sql.append("and APP_DATE <= '" + edate + "' ");
		if ("�w����".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT = '����' ");
		if ("ñ�֤�".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT not in ('����','����') ");
		if ("�ݳB�z".equals(queryFlowStatus))
			advanced_sql.append("and F_INP_STAT = '�ݳB�z' ");

		if (!"".equals(queryId))
			advanced_sql.append("and a." + tablePKName + " like '%" + queryId + "%' ");

		advanced_sql.append(" and a." + tablePKName + " =  b." + tablePKName);
		return advanced_sql.toString();
	}

	public String getSqlQueryStr() {
		StringBuilder strSql = new StringBuilder();
		String keyName = cdo.getTablePKName();
		String targetEmpidFieldName = cdo.getTableApplicantFieldName().trim();// TODO ��²��
		ArrayList<String> resultFieldList = cdo.getQueryResultShowTableFieldList();
		// �渹

		strSql.append("select DISTINCT a.");
		for (String fname : resultFieldList) {
			if (fname.trim().equalsIgnoreCase(targetEmpidFieldName)) {
				strSql.append(getEmpInfoSqlQueryStr(keyName, targetEmpidFieldName));

			} else if (fname.trim().equalsIgnoreCase("'ñ�֪��A'")) {
				strSql.append(getFlowStateSqlQueryStr(keyName, cdo.getTableName()));

			} else {
				strSql.append(fname);
			}

			strSql.append(",");
		}
		String str = strSql.toString();
		str = str.substring(0, str.length() - 1);
		str += " from  " + cdo.getTableName() + " a," + cdo.getTableName() + "_FLOWC b " + "where 1=1";

		// �D�n�d�����

		return str;
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
		String sql = getAdvancedCondition();
		sql = getSqlQueryStr() + sql;
		String[][] list = cdo.getTalk().queryFromPool(sql);
		if (list.length > 0) {
			return getQueryResultAfterProcess(list, cdo.getQueryResultShowTableFieldList());
		}
		return null;

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
	public String[][] getQueryResultAfterProcess(String[][] queryResults, ArrayList<String> viewFieldOfResultList)
			throws Throwable {

		int sign_flow_status_index = -1;// ñ�֪��A��Index
		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
			if (viewFieldOfResultList.get(i).contains("'ñ�֪��A'")) {
				sign_flow_status_index = i;
			}
		}

		for (int i = 0; i < queryResults.length; i++) {
			// ���o�l�y�{�ثeñ�֪��A
			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();

			// ���o�D�y�{�ثeñ�֪��A
			String mainFlowStatus = queryResults[i][sign_flow_status_index].trim();

			// �p�G�l�y�{�M�D�y�{������ �b�d�ߵ��G��檺ñ�֪��A�~���"�w�ͮ�"
			if ((mainFlowStatus.equals("����") || mainFlowStatus.equals("�k��"))
					&& (checkFlowStatus.equals("����") || checkFlowStatus.equals("�L"))) {
				queryResults[i][sign_flow_status_index] = "<font color=blue>(�w�ͮ�)</font>�i�D�y�{:" + mainFlowStatus + "�j"
						+ "�i����y�{:" + checkFlowStatus + "�j";
			}
			// �p�G�l�y�{�M�D�y�{���@�襼���� �h�i��[�u�B�z
			else {
				queryResults[i][sign_flow_status_index] = "<font color=red>ñ�֤�</font>" + "�i�D�y�{:" + mainFlowStatus
						+ getCurrentFlowGateAndApprover(cdo.getTablePKName(), queryResults[i][0]) + "�j" + "�i����y�{:"
						+ checkFlowStatus
						+ getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), queryResults[i][0] + "CHECK")
						+ "�j";
			}
		}

		return queryResults;
	}

	/**
	 * ���o�ثeñ�����d�W�ٻPñ�֤H����Ʀr�� EX:"-(���d�W��-ñ�֤H1,ñ�֤H2..)" TODO ���ʦ�mainQuery
	 * 
	 * @param pkName  ��ƪ�pk���W��
	 * @param pkValue ��ƪ�pk��
	 * @return
	 */
	public String getCurrentFlowGateAndApprover(String pkName, String pkValue) {
		Vector<String> people = service.getApprovablePeople(service.getFunctionName(),
				"a." + pkName + "='" + pkValue + "'");
		StringBuffer sb = new StringBuffer();
		if (people != null) {
			if (people.size() != 0) {
				sb.append("-(");
				for (int j = 0; j < people.size(); j++) {
					if (j != 0)
						sb.append(",");
					String id1 = (String) people.elementAt(j);
					String name1 = service.getName(id1);
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
			ret = service.getTalk().queryFromPool(sql);
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

}
