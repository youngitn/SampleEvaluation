package oa.SampleEvaluation.controller;

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
 * 
 * �i����<br>
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends Controller {

	private boolean confirm = true;
	private CommonDataObj cdo;
	private BaseService service;
	String actionObjName;

	@Override
	public String action(String arg0) throws Throwable {
		// ���O�ݩʪ�l�Ƴ]�w
		SetProperty();

		// ���s�ʧ@�B�z�i�J�I
		switch (Actions.valueOf(actionObjName.trim())) {
		case QUERY_CLICK:
			doQuery();
			break;
		case SAVE_CLICK:
			doSave();
			break;
		case SHOW_DETAIL_CLICK:
			showDetail();
			break;
		default:
			break;
		}
		return null;

	}

	private void SetProperty() throws SQLException, Exception {
		// for enum switch
		actionObjName = getActionName(getName());

		service = new BaseService(this);

		// ���ժ���
		cdo = buildCdo();

	}

	private void showDetail() throws SQLException, Exception {
		setValue("QUERY_LIST_PNO", getValue("QUERY_LIST.PNO"));
		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(getValue("QUERY_LIST.PNO"));
		String[][] allColumns = cdo.getTableAllColumn();
		if (ret != null && ret.length > 0) {
			fillData(ret, allColumns);
		} else {
			message("�d�L���");
		}
		FormInitUtil init = new FormInitUtil(this);

		init.doDetailPageProcess();

		addScript(UIHidderString.hideDmakerAddButton() + UIHidderString.hideDmakerFlowPanel());

	}

	private void doQuery() throws Throwable {
		// go
		MainQuery mquery = new MainQuery(cdo);

		String[][] list = mquery.getQueryResult();
		message(mquery.getSqlQueryStr());
		if (list == null || list.length <= 0) {
			// message("�d�L����");
		}
		setTableData("QUERY_LIST", list);
	}

	private void doSave() throws Throwable {

		// ��������� (���W,�����D)
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("APPLICANT", "�ӽФH");
		fieldMap.put("APP_TYPE", "�ӽ�����");
		fieldMap.put("RECEIPT_UNIT", "���z���");
		fieldMap.put("URGENCY", "�止��");
		fieldMap.put("MATERIAL", "�쪫�ƦW��");

		// �s�W����cdo���B�~��L���
		AddUtil addUtil = new AddUtil(service);
		ArrayList<String> ret = (ArrayList<String>) addUtil.emptyCheck(fieldMap);
		if (ret != null && ret.size() > 0) {
			message("�H�U���а���ܩο�J:" + ret);
		} else {
			int result = showConfirmDialog("�T�w�e�X���H", "���ɴ���", 0);
			if (result != 1) {
				// ���ͳ渹
				String uuid = addUtil.getUUID(cdo);

				// DMAKER ����ADD�\�� �ݱN��ƶ�i�h������~�Y����
				setValue(cdo.getTablePKName(), uuid);
				FileItemSetChecker();
				// confirm = true ����O�_�u���e�X
				if (confirm) {
					// Ĳ�oDmaker���ت��s�W�s�Ӱe�X���
					// �g�bview�����|�n�I
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	private void FileItemSetChecker() {
		// TODO Auto-generated method stub
		if (!getValue("FILE_SPEC").equals("")) {
			setValue("PROVIDE_SPEC", "1");
		}
		if (!getValue("FILE_COA").equals("")) {
			setValue("PROVIDE_COA", "1");
		}
		if (!getValue("FILE_SDS").equals("")) {
			setValue("PROVIDE_SDS", "1");
		}
		if (!getValue("FILE_OTHERS").equals("")) {
			setValue("PROVIDE_OTHERS", "1");
		}
		if (!getValue("FILE_TEST_METHOD").equals("")) {
			setValue("PROVIDE_TEST_METHOD", "1");
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

	private String getActionName(String Name) {

		Name = Name.toUpperCase();
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	private CommonDataObj buildCdo() throws SQLException, Exception {

		/** init **/
		CommonDataObj inercdo = new CommonDataObj(getTalk(), getTableName(), "PNO", "APPLICANT");
		SampleEvaluationQuerySpec qs = new SampleEvaluationQuerySpec();

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
		qs.setQueryDepNoFieldName("QUERY_EMP_DEP");
		// set field value
		qs.setQueryBillId(getValue("QUERY_PNO"));
		qs.setQueryEmpid(getValue("QUERY_EMP_ID"));
		qs.setQueryReqSDate(getValue("QUERY_REQ_SDATE"));
		qs.setQueryReqEDate(getValue("QUERY_REQ_EDATE"));
		qs.setQueryStatus(getValue("r_status"));
		qs.setQueryStatusCheck(getValue("r_status_check"));
		qs.setQueryDepNo(getValue("QUERY_EMP_DEP"));
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

}
