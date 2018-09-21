package oa.SampleEvaluation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import oa.SampleEvaluation.common.SampleEvaluation;
import oa.SampleEvaluation.common.UIHidderString;
import oa.SampleEvaluation.common.UIObj;
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
		case QUERY_PAGE_INIT:
			UIHide();
			init.doQueryPageProcess();
			break;

		case ADD_PAGE_INIT:
			UIHide();
			init.doAddPageProcess();
			break;

		case PENING_PAGE_INIT:// �o�Ӱʧ@������� �ݩ���J�e�����o�O���s�o��

			init.doPendingPageProcess();
			break;

		case DETAIL_PAGE_INIT:
			init.doDetailPageProcess();
			break;
		case FLOW_PAGE_INIT:
			init.doPendingPageProcess();
			break;
		case QUERY_CLICK:

			UIHide();
			/**
			 * 
			 */
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

	public void doQuery() throws Throwable {
		// TODO ����ɥX�{����NULL
		// �ӽФH�u�� table���W��=APPLICANT DoQuery.getAdvancedCondition()<--�ΨӲ�SQL��
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
		cdo.setFunctionName(getFunctionName());
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
			message("�d�L����");
		}

	}

	public void save() throws Throwable {
		Map<String, Map<String, String>> fieldMap = new HashMap<String, Map<String, String>>();

		fieldMap.put("APPLICANT", AddUtil.addValidateField(getValue("APPLICANT").trim(), "�ӽФH"));
		fieldMap.put("APP_TYPE", AddUtil.addValidateField(getValue("APP_TYPE").trim(), "�ӽ�����"));
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

	public Hashtable getFormFieldKeyValueHashtableWithCaption() {
		Hashtable<String, UIObj> ret = new Hashtable<String, UIObj>();
		Hashtable h = getAllcLabels();
		Set<String> keys = h.keySet();
		// message( ((Hashtable)h.get("TO_ADD_PAGE")).get("caption")+"");
		for (String key : keys) {
			// System.out.println("Value of "+key+" is: "+hm.get(key));
			// ret.put(key, new UIObj(name, caption, type, value));
		}
		return null;
	}

	public String arrayToString(String[] a) {
		return Arrays.toString(a).replace("[", "").replace("]", "");

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
			if (queryResults[i][sign_flow_status_index].trim().equals("����")
					|| queryResults[i][sign_flow_status_index].trim().equals("�k��")) {
				queryResults[i][sign_flow_status_index] = "<font color=blue>(�w�ͮ�)</font>";
			} else {
				Vector people = getApprovablePeople(getFunctionName(),
						"a." + viewFieldOfResultList.get(uuid_index) + "='" + queryResults[i][0] + "'");
				StringBuffer sb = new StringBuffer();
				if (people != null) {
					if (people.size() != 0) {
						sb.append("(");
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
				queryResults[i][sign_flow_status_index] = "<font color=red>ñ�֤�</font>�i"
						+ queryResults[i][sign_flow_status_index].trim() + "-" + sb.toString() + "�j";
			}
		}

		return queryResults;
	}

}
