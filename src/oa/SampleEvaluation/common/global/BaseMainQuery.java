package oa.SampleEvaluation.common.global;

import java.sql.SQLException;
import java.util.List;

import com.ysp.service.BaseService;
import com.ysp.util.LogUtil;

import jcx.db.talk;
import oa.SampleEvaluation.controller.HprocImpl;

public abstract class BaseMainQuery {
	protected CommonDataObj cdo;
	/**
	 * <h1>service<br>
	 * �|�Ψ�jcx�Y���O����k��,�i����new�@�ӹ�@���������O <br>
	 * EX:SampleEvaluationActionController extends hproc <-- <br>
	 * ���ϥ� getValue()�����o�~����T����k <br>
	 * �Ȩϥ�getFlowHis()����k <br>
	 * getName()���ݭn�t�θ�T���]�@�ˤ��ϥ�
	 */
	protected BaseService service;
	protected String tablePKName;
	protected String tableName;
	protected String tableApplicantFieldName;
	protected String tableAppDateFieldName;
	protected talk innerTalk;
	protected TableFieldSpec tableFieldSpec;
	protected UserData userData;

	public BaseMainQuery(CommonDataObj cdo) {

		this.cdo = cdo;

		this.service = new BaseService(new HprocImpl());
		tableFieldSpec = cdo.getTableFieldSpec();
		tablePKName = tableFieldSpec.pkName;
		tableName = tableFieldSpec.name;
		tableApplicantFieldName = tableFieldSpec.applicantFieldName;
		tableAppDateFieldName = tableFieldSpec.appDateFieldName;
		innerTalk = cdo.getTalk();
		try {
			userData = new UserData(cdo.getLoginUserId(), innerTalk);
		} catch (SQLException e) {
			LogUtil.getLog(getClass()).error(e);
		} catch (Exception e) {
			LogUtil.getLog(getClass()).error(e);
		}

	}

	// ���o�d���v��SQL����
	//public abstract String getQueryRightSql() throws Exception;

	/**
	 * 
	 * 
	 * @return
	 */
	//public abstract String getAdvancedCondition();

	/**
	 * @param queryFlowStatus
	 * @param advanced_sql
	 */
	protected String statusCheck(String queryFlowStatus, String tableCode) {
		StringBuilder advanced_sql = new StringBuilder("");

		if ("�w����".equals(queryFlowStatus))
			advanced_sql.append("and " + tableCode + ".F_INP_STAT = '����' ");
		if ("ñ�֤�".equals(queryFlowStatus))
			advanced_sql.append("and " + tableCode + ".F_INP_STAT not in ('����','����') ");
		if ("�ݳB�z".equals(queryFlowStatus))
			advanced_sql.append("and " + tableCode + ".F_INP_STAT = '�ݳB�z' ");

		return advanced_sql.toString();
	}

	//public abstract String getSqlQueryStr() throws Exception;

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

//	public String[][] getQueryResult() throws Throwable {
//		String sql = "";
//		sql = getSqlQueryStr();
//		String[][] list = cdo.getTalk().queryFromPool(sql);
//		if (list.length > 0) {
//			return getQueryResultAfterProcess(list, cdo.getQuerySpec().getQueryResultView());
//		}
//		return list;
//
//	}

	/**
	 * @param queryResults          �d�ߵ��G String[][]
	 * @param viewFieldOfResultList �d�ߵ��G������ArrayList
	 * @param c                     Controller
	 * @return
	 * @throws Throwable
	 */
//	protected abstract String[][] getQueryResultAfterProcess(String[][] queryResults,
//			List<String> viewFieldOfResultList) throws Throwable;

	/**
	 * ���o�ثeñ�����d�W�ٻPñ�֤H����Ʀr�� EX:"-(���d�W��-ñ�֤H1,ñ�֤H2..)"
	 * 
	 * @param pkName  ��ƪ�pk���W��
	 * @param pkValue ��ƪ�pk��
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	protected String getCurrentFlowGateAndApprover(String pkName, String pkValue) throws SQLException, Exception {
		List<String> people = service.getApprovablePeople(cdo.getFunctionName(), "a." + pkName + "='" + pkValue + "'");
		StringBuffer sb = new StringBuffer();

		if (!people.isEmpty()) {
			sb.append("-(");
			for (int j = 0; j < people.size(); j++) {
				if (j != 0)
					sb.append(",");
				String id1 = (String) people.get(j);
				UserData u = new UserData(id1, innerTalk);
				String name1 = u.getHecname();
				sb.append(name1 + "-" + id1);
			}
			sb.append(")");
		}

		return sb.toString();
	}

	// �T�w�d���v��
	protected boolean isAdmin() {
		return (userData.getDepNo().equals("1001") || userData.getEmpid().equals("52116")
				|| userData.getDepNo().equals("21"));

	}

}
