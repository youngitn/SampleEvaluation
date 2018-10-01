package oa.SampleEvaluation;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import oa.SampleEvaluation.enums.*;
import com.ysp.service.BaseService;
import com.ysp.service.MailService;

import jcx.jform.hproc;
import jcx.util.convert;
import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.MainQuery;
import oa.SampleEvaluation.common.UIHidderString;
import oa.SampleEvaluation.common.UserData;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import com.ysp.field.Mail;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	public String functionName = "�˫~�����ӽЧ@�~";
	public boolean isTest = false;

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// importJquery();
		this.cdo = new CommonDataObj(getUser(), getTalk(), "SAMPLE_EVALUATION", "PNO", "APPLICANT");
		String actionObjName = getActionName(getName());

		// ���s�ʧ@�B�z�i�J�I
		switch (Actions.valueOf(actionObjName.trim())) {

		case QUERY_CLICK:
			setCommonObiQueryData();
			MainQuery mquery = new MainQuery(cdo);
			String[][] list = mquery.getQueryResult();
			if (list.length > 0) {
				list = getQueryResultAfterProcess(list, cdo.getQueryResultShowTableFieldList());
			} else {
				message("�d�L����");
			}
			setTableData("QUERY_LIST", list);

			break;

		case SAVE_CLICK:
			doSave();

			break;

		case SHOW_DETAIL_CLICK:// �o�Ӱʧ@������� �ݩ���J�e�����o�O���s�o��

			detailPage();
			break;

		default:

			break;
		}
		return null;

	}

	// CommonObi
	public void setCommonObiQueryData() {
		cdo.setTableApplicantFieldName("APPLICANT");
		cdo.setTableAppDateFieldName("APP_DATE");

		cdo.setQueryFieldNameEmpid("QUERY_EMP_ID");
		cdo.setQueryFieldNameBillId("QUERY_PNO");
		cdo.setQueryFieldNameStartAppDate("QUERY_REQ_SDATE");
		cdo.setQueryFieldNameEndAppDate("QUERY_REQ_EDATE");
		cdo.setQueryFieldNameFlowStatus("r_status");

		cdo.setQueryFieldValueEmpid(getValue("QUERY_EMP_ID"));
		cdo.setQueryFieldValueBillId(getValue("QUERY_PNO"));
		cdo.setQueryFieldValueStartAppDate(getValue("QUERY_REQ_SDATE"));
		cdo.setQueryFieldValueEndAppDate(getValue("QUERY_REQ_EDATE"));
		cdo.setQueryFieldValueFlowStatus(getValue("r_status"));

		cdo.setFunctionName(getFunctionName());

		ArrayList<String> flist = new ArrayList<String>();
		flist.add("PNO");
		flist.add("APPLICANT");
		flist.add("APP_TYPE");
		flist.add("URGENCY");
		flist.add("APP_DATE");
		flist.add("'ñ�֪��A'");
		flist.add("'����'");
		flist.add("'ñ�֬���'");

		cdo.setQueryResultShowTableFieldList(flist);
	}

	public void doSave() throws Throwable {
		Map<String, Map<String, String>> fieldMap = new HashMap<String, Map<String, String>>();

		fieldMap.put("APPLICANT", AddUtil.addValidateField(getValue("APPLICANT").trim(), "�ӽФH"));
		fieldMap.put("APP_TYPE", AddUtil.addValidateField(getValue("APP_TYPE").trim(), "�ӽ�����"));
		fieldMap.put("APPLICANT", AddUtil.addValidateField(getValue("RECEIPT_UNIT").trim(), "���z���"));

		ArrayList<String> ret = AddUtil.emptyCheck(fieldMap);
		if (ret != null && ret.size() > 0) {
			message("�H�U���п�ܩο�J:" + ret);
		} else {
			int result = showConfirmDialog("�T�w�e�X���H", "���ɴ���", 0);
			if (result != 1) {
				// ���ͳ渹
				String uuid = new AddUtil().getUUID(cdo, getTalk());

				// ���طs�W�\�� �ݱN��ƶ�i�h���~��
				setValue(cdo.getTablePKName(), uuid);
				// confirm = true ����O�_�u���e�X
				if (confirm) {
					// TODO �H���ֳB�z�@�U
					// sendMail();
					// Ĳ�oDmaker���ت��s�W�s�Ӱe�X���
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	/**
	 * �ثe���K�~��k
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */

	public void detailPage() throws SQLException, Exception {

		// ��J�D�ɸ��
		String key = getValue("QUERY_LIST.PNO");

		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(key);
		String[][] allColumns = cdo.getTableAllColumn();
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], ret[0][i]);
			}
			FormInitUtil init = new FormInitUtil(this);
			init.doDetailPageProcess();
			addScript(UIHidderString.hideDmakerAddButton());
		} else {
			message("�o�Ϳ��~�A�䤣�즹����ơI");
		}
	}

	public String getActionName(String Name) {

		Name = Name.toUpperCase();
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	/**
	 * ���J���ɶ��t���D �L�k������addScript�ϥ�Jquery ��ı�Ϊk �bUI�Ԧr�űN�ޥμ��ҶK�W��, ��k�g�b���s��,�p��onLoad�ƥ�,
	 * �@�˼g�b�r�������Ū����N�۰ʰ���.
	 */
	public void importJquery() {
		addScript("var script = document.createElement('script');"
				+ "script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';"
				+ "script.type = 'text/javascript';" + "document.getElementsByTagName('head')[0].appendChild(script);");

	}

	public String getCheckFlowStatus(String ownPno) {
		String sql = "SELECT F_INP_STAT FROM SAMPLE_EVALUATION_CHECK_FLOWC WHERE OWN_PNO='" + ownPno + "'";
		String[][] ret = null;
		try {
			ret = getTalk().queryFromPool(sql);
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

	/**
	 * ��ñ�֪��A����ܥ[�u
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
				queryResults[i][sign_flow_status_index] = "<font color=blue>(�w�ͮ�)</font>";
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
	 * ���o�ثeñ�����d�W�ٻPñ�֤H����Ʀr�� EX:"-(���d�W��-ñ�֤H1,ñ�֤H2..)"
	 * 
	 * @param pkName  ��ƪ�pk���W��
	 * @param pkValue ��ƪ�pk��
	 * @return
	 */
	public String getCurrentFlowGateAndApprover(String pkName, String pkValue) {
		Vector<String> people = getApprovablePeople(getFunctionName(), "a." + pkName + "='" + pkValue + "'");
		StringBuffer sb = new StringBuffer();
		if (people != null) {
			if (people.size() != 0) {
				sb.append("-(");
				for (int j = 0; j < people.size(); j++) {
					if (j != 0)
						sb.append(",");
					String id1 = (String) people.elementAt(j);
					String name1 = getName(id1);
					sb.append(name1 + "-" + id1);
				}
				sb.append(")");
			}
		}

		return sb.toString();
	}

}
