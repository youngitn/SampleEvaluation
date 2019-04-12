package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.Detail;
import oa.SampleEvaluation.enums.ActionsEnum;
import oa.SampleEvaluation.model.QueryConditionDTO;
import oa.SampleEvaluation.service.QueryResultService;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluation.service.TempSaveService;

/**
 * The Class SampleEvaluationActionController.
 *
 * @author u52116
 */
public class SampleEvaluationActionController extends HprocImpl {

	/*
	 * (non-Javadoc)
	 * 
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	@Override
	public String action(String arg0) throws Throwable {

		// ���O�ݩʪ�l�Ƴ]�w
		try {
			// ���s�ʧ@�B�z�i�J�I
			String[][] ret = null;
			switch (ActionsEnum.valueOf(getActionName(getName()))) {
			case QUERY_CLICK:
				ret = doQuery();

				if (ret == null || ret.length <= 0) {
					message("�d�L����");
				}
				setTableData("QUERY_LIST", ret);
				break;
			case EXPORT_TO_EXCEL_CLICK:

				ret = doQuery();

				if (ret == null || ret.length <= 0) {
					message("�d�L����");
				}
				setTableData("EXPORT_QUERY_LIST", ret);
				break;
			case SAVE_CLICK:
				doSave();
				break;
			case SHOW_DETAIL_CLICK:
				Detail detail = new Detail(this);
				detail.show();
				setTextAndCheckIsSubFlowRunning();
				break;
			case TEMP_CLICK:
				try {
					new TempSaveService(this).save();
				} catch (SQLException e) {
					if (e.getErrorCode() == 0) {
						int result = showConfirmDialog("�T�w�N��Ȧs���л\��?", "���ɴ���", 0);
						if (result == 1) {
							message("�����e�X!");
						} else {
							new TempSaveService(this).update();
						}
					}
				}
				break;
			case LOAD_TEMP_CLICK:
				new TempSaveService(this).load();
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));

				break;
			default:
				break;
			}
		} catch (EnumConstantNotPresentException e) {
			message("enum:Actions.clss �o�͵L�k���Ѫ��N�~");
		}
		return null;

	}

	private String[][] doQuery() throws SQLException, Exception {
		// �q�e�����o�d�߱���ö�JQueryConditionDto
		QueryConditionDTO qcDto = new QueryConditionDTO();
		qcDto.getFormData(this);
		// �N�e�@�B�J���o��QueryConditionDto�ഫ��SQL WHERE�ԭz��
		String sql = qcDto.toSql();

		// �����dao�A��,�]���G���ݲŦXQueryResultDto���ݩʩҩw�q,�ҥH����Ƭ۹���Dao.
		QueryResultService resultService = new QueryResultService(this);
		resultService.setForm(this);
		String[][] ret = (String[][]) resultService.getResult(sql);
		return ret;
	}

	private String[][] doExportQuery() throws SQLException, Exception {
		// �q�e�����o�d�߱���ö�JQueryConditionDto
		QueryConditionDTO qcDto = new QueryConditionDTO();
		qcDto.getFormData(this);
		// �N�e�@�B�J���o��QueryConditionDto�ഫ��SQL WHERE�ԭz��
		String sql = qcDto.toSql();

		// �����dao�A��,�]���G���ݲŦXQueryResultDto���ݩʩҩw�q,�ҥH����Ƭ۹���Dao.

		String[][] ret = getTalk().queryFromPool("SELECT * FROM SAMPLE_EVALUATION " + sql);
		return ret;
	}

	/**
	 * �_��.
	 *
	 * @throws Throwable the throwable
	 */
	private void doSave() throws Throwable {
		// ��ʫإߥ�������� (���W,�����D)
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
		AddUtil addUtil = new AddUtil(this);
		addUtil.doAdd(fieldMap);

	}

	/**
	 * Gets the ActionName.
	 *
	 * @param name [String]
	 * @return [String]
	 */
	private String getActionName(String name) {
		name = name.toUpperCase();
		if (name.contains(".")) {
			return name.substring(name.indexOf(".") + 1);
		}
		return name.toUpperCase();
	}

}
