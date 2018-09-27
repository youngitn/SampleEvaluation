package oa.SampleEvaluation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.DoQuery;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.tableObject.SampleEvaluation;
import oa.SampleEvaluation.common.UIHidderString;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class Controller extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// �����J��B�z
		// �U�������J�B�z�����O����@
		// importJquery();
		FormInitUtil init = new FormInitUtil(this);
		setValue("NOW_INIT", getName());
		buildSysFormObj();

		String actionObjName = getActionName(getName());

		//
		// ���s�ʧ@�B�z�i�J�I
		switch (ButtonActionByName.valueOf(actionObjName.trim())) {
		case QUERY_PAGE_INIT:// �i�J�d�ߵe��
			addScript(UIHidderString.hideDmakerAddButton());
			init.doQueryPageProcess();
			break;

		case ADD_PAGE_INIT:// �i�J�s�W�e��
			addScript(UIHidderString.hideDmakerAddButton());
			init.doAddPageProcess();
			break;

		case PENING_PAGE_INIT:// �i�J�ݳB�z�e��

			init.doPendingPageProcess();
			break;

		case DETAIL_PAGE_INIT:// �i�J���ӵe��
			init.doDetailPageProcess();
			break;
		case FLOW_PAGE_INIT:// �i�J�y�{ñ�ֵe��
			init.doPendingPageProcess();
			// ñ�֧�����ܥD����
			String pno = getValue("PNO").trim();
			if (pno.length() <= 0) {
				changeForm(getFunctionName());
			} else {
				String sql = "select f_inp_info from " + getTableName() + "_flowc where PNO = '" + pno + "'";
				String[][] ret = getTalk().queryFromPool(sql);
				if (ret.length > 0) {
					String memo = ret[0][0];
					if (memo.startsWith("[�hñ]")) {
						addScript("callRejectWarning();");
					}
				}
			}
			// �p�G�a�X����� �ջs�ﶵ������ �N��ܵ����H��
			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")) {
				setVisible("ASSESSOR", true);
			}
			if (getState().trim().equals("�ժ�")) {
				setEditable("IS_CHECK", true);
				setEditable("IS_TRIAL_PRODUCTION", true);

				setEditable("ASSESSOR", true);
				setEditable("LAB_EXE", true);

			}
			if (getState().trim().equals("�ջs�渹��g")) {
				setEditable("NOTIFY_NO_TRIAL_PROD", true);
			}
			if (getState().trim().equals("���z���D�ޤ���")) {
				setEditable("DESIGNEE", true);
			}
			if (getState().trim().equals("�ݳB�z")) {
				// �����B�z�g�bUI���I���϶�ADD_BACKGROUND��
				// �]���o�䪺�޿� �b�ݳB�z���d�Y����
			}

			break;
		case QUERY_CLICK:
			setCommonObiQueryData();
			doQuery();
			break;

		case SAVE_CLICK:
			save();
			break;

		case SHOW_DETAIL_CLICK:// �o�Ӱʧ@������� �ݩ���J�e�����o�O���s�o��
			UIHide();
			detailPage();
			init.doDetailPageProcess();
			break;

		default:

			break;
		}
		return null;

	}

	public void setCommonObiQueryData() {
		cdo.setTableApplicantFieldName("APPLICANT");
		cdo.setTableAppDateFieldName("APP_DATE");
		cdo.setQueryFieldNameBillId("QUERY_PNO");
		cdo.setQueryFieldNameStartAppDate("QUERY_REQ_SDATE");
		cdo.setQueryFieldNameEndAppDate("QUERY_REQ_EDATE");
		cdo.setQueryFieldNameFlowStatus("r_status");

		cdo.setQueryFieldValueEmpid(getValue("QUERY_EMP_ID"));
		cdo.setQueryFieldValueBillId(getValue("QUERY_PNO"));
		cdo.setQueryFieldValueStartAppDate(getValue("QUERY_REQ_SDATE"));
		cdo.setQueryFieldValueEndAppDate(getValue("QUERY_REQ_EDATE"));
		cdo.setQueryFieldValueFlowStatus(getValue("r_status"));
		cdo.setQueryFieldValueSubFlowStatus(getValue("r_sub_status"));
		cdo.setFunctionName(getFunctionName());
	}

	public void doQuery() throws Throwable {
		// TODO ����ɥX�{����NULL
		// �ӽФH�u�� table���W��=APPLICANT DoQuery.getAdvancedCondition()<--�ΨӲ�SQL��
		
		String[][] list = DoQuery.getQueryResult(cdo);
		if (list.length != 0) {
			ArrayList<String> flist = new ArrayList<String>();
			flist.add("PNO");
			flist.add("APPLICANT");
			flist.add("APP_TYPE");
			flist.add("URGENCY");
			flist.add("APP_DATE");
			flist.add("'ñ�֪��A'");
			flist.add("'����'");
			flist.add("'ñ�֬���'");

			setTableData("QUERY_LIST", getQueryResultAfterProcess(list, flist));
		} else {
			setTableData("QUERY_LIST", list);
			message("�d�L����");
		}

	}

	public void save() throws Throwable {
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
					// Ĳ�oDmaker���ت��s�W�s�Ӱe�X���
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	public void detailPage() throws SQLException, Exception {

		// ��J�D�ɸ��
		String key = getValue("QUERY_LIST.PNO");

		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(key);
		String[][] allColumns = cdo.getTableAllColumn();
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], ret[0][i]);
			}
		} else {
			message("�o�Ϳ��~�A�䤣�즹����ơI");
		}
	}

	public void buildSysFormObj() throws SQLException, Exception {

		if (this.cdo == null || getTalk() == null) {
			this.cdo = new CommonDataObj(getUser(), getTalk(), "SAMPLE_EVALUATION", "PNO", "APPLICANT");
		}

	}

	public String getActionName(String Name) {

		Name = Name.toUpperCase();
		if ("[FORM INIT] ".equals(Name) || "[FORM INIT] QUERYPAGE".equals(Name)) {
			return "QUERY_PAGE_INIT";
		} else if ("[FORM INIT] ADDPAGE".equals(Name)) {
			return "ADD_PAGE_INIT";
		} else if (Name.startsWith("[FORM INIT] [FLOW]")) {

			if ("[FORM INIT] [FLOW].�ݳB�z".equals(Name)) {
				return "PENING_PAGE_INIT";
			} else {
				return "FLOW_PAGE_INIT";
			}

		} else if (Name.startsWith("[FORM INIT] DETAILPAGE")) {
			return "DETAIL_PAGE_INIT";
		} else if (Name.startsWith("[FORM INIT] SIGNFLOWHISTORY")) {

			return "SIGN_FLOW_HISTORY_PAGE_INIT";
		}
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	public void UIHide() {
		// ����Dmaker���h�s�W���s
		addScript(UIHidderString.hideDmakerAddButton());

		// ���äW�����
		addScript(UIHidderString.hideDmakerFlowPanel());
		// ���øԲӸԲӦC��\��
		addScript(UIHidderString.hideFlowButtonDetail());

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

	public String arrayToString(String[] a) {
		return Arrays.toString(a).replace("[", "").replace("]", "");

	}

	public String getCheckFlowStatus(String ownPno) {
		String sql = "SELECT F_INP_STAT FROM SAMPLE_EVALUATION_CHECK_FLOWC WHERE OWN_PNO='" + ownPno + "'";
		String[][] ret = null;
		try {
			ret = getTalk().queryFromPool(sql);
			if (ret.length == 0) {
				// �p�G�S������y�{��������w����
				return "���i��";
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
		// ��XUUID�Pñ�֪��A��Index
		int uuid_index = -1;// UUID��Index
		int sign_flow_status_index = -1;// ñ�֪��A��Index
		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
			if (viewFieldOfResultList.get(i).toUpperCase().equals(cdo.getTablePKName())) {
				uuid_index = i;
			} else if (viewFieldOfResultList.get(i).contains("'ñ�֪��A'")) {
				sign_flow_status_index = i;
			}
		}

		for (int i = 0; i < queryResults.length; i++) {
			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();
			String mainFlowStatus = queryResults[i][sign_flow_status_index].trim();
			// �l�y�{�M�D�y�{������ �b�d�ߵ��G��檺ñ�֪��A�~���"�w�ͮ�"
			if ((mainFlowStatus.equals("����") || mainFlowStatus.equals("�k��")) && checkFlowStatus.equals("����")) {
				queryResults[i][sign_flow_status_index] = "<font color=blue>(�w�ͮ�)</font>";
			} else {
				// �l�ΥD�y�{���@���٥����ת�����޿�

				// �D�y�{ñ�֤H���o
				Vector<String> people = getApprovablePeople(getFunctionName(),
						"a." + viewFieldOfResultList.get(uuid_index) + "='" + queryResults[i][0] + "'");
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
				people = null;
				// �l�y�{ñ�֤H���o
				people = getApprovablePeople(getFunctionName() + "_����y�{",
						"a." + "OWN_" + viewFieldOfResultList.get(uuid_index) + "='" + queryResults[i][0] + "CHECK'");
				StringBuffer subsb = new StringBuffer();
				if (people != null) {
					if (people.size() != 0) {
						subsb.append("-(");
						for (int j = 0; j < people.size(); j++) {
							if (j != 0)
								subsb.append(",");
							String id1 = (String) people.elementAt(j);
							String name1 = getName(id1);
							subsb.append(name1 + "-" + id1);
						}
						subsb.append(")");
					}
				}

				queryResults[i][sign_flow_status_index] = "<font color=red>ñ�֤�</font>�i�D�y�{:" + mainFlowStatus
						+ sb.toString() + "�j" + "�i����y�{:" + checkFlowStatus + subsb.toString() + "�j";
			}
		}

		return queryResults;
	}

}
