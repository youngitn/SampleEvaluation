package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.DateTool;
import oa.SampleEvaluation.common.MainQuery;
import oa.SampleEvaluation.common.SampleEvaluationDataObj;
import oa.SampleEvaluation.common.SampleEvaluationQuerySpec;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.common.global.FormInitUtil;
import oa.SampleEvaluation.common.global.UIHidderString;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.enums.Actions;

/**
 * 
 * 
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends HprocImpl {

	private boolean confirm = true;
	private SampleEvaluationDataObj cdo;
	private BaseService service;
	String actionObjName;

	@Override
	public String action(String arg0) throws Throwable {
		// ���O�ݩʪ�l�Ƴ]�w
		setProperty();
		try {
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
		} catch (EnumConstantNotPresentException e) {
			message("enum:Actions.clss �o�͵L�k���Ѫ��N�~");
		}
		return null;

	}

	private void setProperty() throws Exception {
		// for enum switch
		actionObjName = getActionName(getName());

		service = new BaseService(this);
		if (service == null || service.getFunctionName().equals("")) {
			throw new Exception("new BaseService(this) is null......");
		}
		// ���ժ���
		cdo = buildCdo();

	}

	private void showDetail() throws SQLException, Exception {
		setValue("QUERY_LIST_PNO", getValue("QUERY_LIST.PNO"));
		BaseDao bao = new SampleEvaluationService(getTalk());
		SampleEvaluation s = (SampleEvaluation) bao.findById(getValue("QUERY_LIST.PNO"));
		DtoUtil.setDtoDataToForm(s, this);
		FormInitUtil init = new FormInitUtil(this);

		init.doDetailPageProcess();
		if (getValue("IS_TRIAL_PRODUCTION").equals("1")) {
			setVisible("ASSESSOR", true);
		}
		setDeadLine();
		addScript(UIHidderString.hideDmakerAddButton() + UIHidderString.hideDmakerFlowPanel());

	}

	private void setDeadLine() throws Exception {

		int addDaysNum = 0;
		String value = getValue("URGENCY");
		if (!value.isEmpty()) {
			if (value.equals("A")) {
				addDaysNum = 100;
			} else if (value.equals("B")) {
				addDaysNum = 110;
			} else if (value.equals("C")) {
				addDaysNum = 130;
			}

			setValue("DL", DateTool.getAfterWorkDate(getValue("APP_DATE"), addDaysNum, getTalk()));
		}
	}

	private void doQuery() throws Throwable {
		// go
		MainQuery mquery = new MainQuery(cdo);

		String[][] list = mquery.testtest();
		// message(mquery.getSqlQueryStr());
		if (list == null || list.length <= 0) {
			message("�d�L����");
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
				fileItemSetChecker();
				// confirm = true ����O�_�u���e�X
				if (confirm) {
					// Ĳ�oDmaker���ت��s�W�s�Ӱe�X���
					// �g�bview�����|�n�I
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	private void fileItemSetChecker() {

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

	private String getActionName(String Name) {

		Name = Name.toUpperCase();
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	private SampleEvaluationDataObj buildCdo() throws SQLException, Exception {

		/** init **/
		SampleEvaluationDataObj inercdo = new SampleEvaluationDataObj(getTalk(), getTableName(), "PNO", "APPLICANT");
		SampleEvaluationQuerySpec qs = new SampleEvaluationQuerySpec(this);

		inercdo.setTableAppDateFieldName("APP_DATE");
		inercdo.setLoginUserId(getUser());
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
