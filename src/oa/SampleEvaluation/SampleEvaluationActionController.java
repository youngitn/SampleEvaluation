package oa.SampleEvaluation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oa.SampleEvaluation.enums.*;
import com.ysp.service.BaseService;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.MainQuery;
import oa.SampleEvaluation.common.SampleEvaluationQuerySpec;
import oa.SampleEvaluation.common.UIHidderString;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	BaseService service;

	@Override
	public String action(String arg0) throws Throwable {

		// for enum switch
		String actionObjName = getActionName(getName());

		service = new BaseService(this);

		// ���ժ��� �d�b�~�NBaseService�̿�`�J
		cdo = buildCdo();

		// ���s�ʧ@�B�z�i�J�I
		switch (Actions.valueOf(actionObjName.trim())) {
		case QUERY_CLICK:
			// go
			MainQuery mquery = new MainQuery(cdo);

			String[][] list = mquery.getQueryResult();
			if (list == null || list.length <= 0) {
				message("�d�L����");
			}
			setTableData("QUERY_LIST", list);
			break;
		case SAVE_CLICK:
			// message(getValue(cdo.getTableApplicantFieldName()));
			doSave();
			break;
		case SHOW_DETAIL_CLICK:// �o�Ӱʧ@������� �ݩ���J�e�����o�O���s�o��
			String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(getValue("QUERY_LIST.PNO"));
			String[][] allColumns = cdo.getTableAllColumn();
			if (ret != null && ret.length > 0) {
				fillData(ret, allColumns);
			} else {
				message("�d�L���");
			}
			FormInitUtil init = new FormInitUtil(this);
			init.doDetailPageProcess();
			addScript(UIHidderString.hideDmakerAddButton());
			break;
		default:
			break;
		}
		return null;

	}

	public void doSave() throws Throwable {

		// ��������� (���W,�����D)
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("APPLICANT", "�ӽФH");
		fieldMap.put("APP_TYPE", "�ӽ�����");
		fieldMap.put("RECEIPT_UNIT", "���z���");
		fieldMap.put("URGENCY", "�止��");
		fieldMap.put("MATERIAL", "�쪫�ƦW��");

		// �s�W����cdo���B�~��L���
		AddUtil addUtil = new AddUtil(service);
		ArrayList<String> ret = addUtil.emptyCheck(fieldMap);
		if (ret != null && ret.size() > 0) {
			message("�H�U���а���ܩο�J:" + ret);
		} else {
			int result = showConfirmDialog("�T�w�e�X���H", "���ɴ���", 0);
			if (result != 1) {
				// ���ͳ渹
				String uuid = addUtil.getUUID(cdo);

				// DMAKER ����ADD�\�� �ݱN��ƶ�i�h������~�Y����
				setValue(cdo.getTablePKName(), uuid);
				// confirm = true ����O�_�u���e�X
				if (confirm) {
					// Ĳ�oDmaker���ت��s�W�s�Ӱe�X���
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	// �N��ƶ�J���e�����
	private void fillData(String[][] data, String[][] allColumns) {
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], data[0][i]);
			}
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

	private CommonDataObj buildCdo() throws SQLException, Exception {

		/** init **/
		CommonDataObj inercdo = new CommonDataObj(getTalk(), getTableName(), "PNO", "APPLICANT");
		SampleEvaluationQuerySpec qs = new SampleEvaluationQuerySpec();
		// BaseService service = new BaseService(new
		// SampleEvaluationActionController());
		// inercdo.setService(service);
		inercdo.setTableAppDateFieldName("APP_DATE");
		inercdo.setLoginUserId(getUser());
		/** query spec **/
		// set field name
		qs.setQueryBillIdFieldName("QUERY_PNO");
		qs.setQueryEmpidFieldName("QUERY_EMP_ID");
		qs.setQueryReqSDateFieldName("QUERY_REQ_SDATE");
		qs.setQueryReqEDateFieldName("QUERY_REQ_EDATE");
		qs.setQueryStatusFieldName("r_status");
		qs.setQueryStatusCheckFieldName("r_status_check");
		// set field value
		qs.setQueryBillId(getValue("QUERY_PNO"));
		qs.setQueryEmpid(getValue("QUERY_EMP_ID"));
		qs.setQueryReqSDate(getValue("QUERY_REQ_SDATE"));
		qs.setQueryReqEDate(getValue("QUERY_REQ_EDATE"));
		qs.setQueryStatus(getValue("r_status"));
		qs.setQueryStatusCheck(getValue("r_status_check"));
		ArrayList<String> flist = new ArrayList<String>();
		flist.add("PNO");
		flist.add("APPLICANT");
		flist.add("APP_TYPE");
		flist.add("URGENCY");
		flist.add("APP_DATE");
		flist.add("'ñ�֪��A'");
		flist.add("'����'");
		flist.add("'ñ�֬���'");

		qs.setQueryResultView(flist);
		inercdo.setQuerySpec(qs);
		return inercdo;

	}

//	public String getUUID(CommonDataObj cdo) throws SQLException, Exception {
//		// talk t = c.getTalk();
//
//		String uuid = "";
//		String tablePKName = "PNO";
//		String table = getTableName();
//		String user = getValue("APPLICANT");
//		String sql = "select max(" + tablePKName + ") from " + table + " where " + tablePKName + " like '" + user
//				+ "%'";
//		String[][] ret = getTalk().queryFromPool(sql);
//
//		if ("".equals(ret[0][0])) {
//			uuid = user + "00001";
//		} else {
//			String m_uuid = ret[0][0].trim().replace(user, "");
//			uuid = user + String.format("%05d", (Long.parseLong(m_uuid) + 1));
//		}
//
//		return uuid;
//	}

}
