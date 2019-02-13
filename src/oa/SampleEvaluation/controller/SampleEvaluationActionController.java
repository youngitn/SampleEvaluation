package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.common.global.FormInitUtil;
import oa.SampleEvaluation.common.global.UIHidderString;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.enums.Actions;
import oa.SampleEvaluation.query.Query;
import oa.SampleEvaluation.query.QueryConditionDto;

/**
 * 
 * 
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends HprocImpl {

	private boolean confirm = true;
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
		showSubFlowSignPeopleTab();
		addScript(UIHidderString.hideDmakerAddButton() + UIHidderString.hideDmakerFlowPanel());

	}

	/**
	 * �D�d��
	 * @throws Throwable
	 */
	private void doQuery() throws Throwable {
		// �z�L DtoUtil ���oclient�ݬd�߱���value,�üg�JQueryConditionDto
		QueryConditionDto targetLikeThis = (QueryConditionDto) DtoUtil.setFormDataToDto(new QueryConditionDto(), this);
		//���o2D array�榡�d�ߵ��G
		String[][] list = new Query().get2DStringArrayResult(targetLikeThis, getTalk());
		
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
		fieldMap.put("AB_CODE", "AB�s��");
		fieldMap.put("MFR", " �s�y��");
		fieldMap.put("MFG_LOT_NO", "�s�y�帹");
		fieldMap.put("SUPPLIER", "������");
		fieldMap.put("QTY", "�ƶq");
		fieldMap.put("UNIT", "���");
		fieldMap.put("SAP_CODE", "SAP���ƽs��");
		// �s�W����cdo���B�~��L���
		AddUtil addUtil = new AddUtil(service);
		ArrayList<String> ret = (ArrayList<String>) addUtil.emptyCheck(fieldMap);
		if (ret != null && ret.size() > 0) {
			message("�H�U���а���ܩο�J:" + ret);
		} else {
			int result = showConfirmDialog("�T�w�e�X���H", "���ɴ���", 0);
			if (result != 1) {
				// ���ͳ渹
				String uuid = addUtil.getUUID(getTableName());

				// DMAKER ����ADD�\�� �ݱN��ƶ�i�h������~�Y����
				setValue("PNO", uuid);
				fileItemSetChecker();
				// confirm = true ����O�_�u���e�X
				if (confirm) {
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

}
