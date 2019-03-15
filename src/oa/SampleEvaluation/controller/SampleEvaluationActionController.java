package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.Detail;
import oa.SampleEvaluation.enums.ActionsEnum;
import oa.SampleEvaluation.model.QueryConditionDTO;
import oa.SampleEvaluation.service.QueryResultService;
import oa.SampleEvaluation.service.TempSaveService;

/**
 * 
 * 
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends HprocImpl {

	@Override
	public String action(String arg0) throws Throwable {

		// ���O�ݩʪ�l�Ƴ]�w
		try {
			// ���s�ʧ@�B�z�i�J�I

			switch (ActionsEnum.valueOf(getActionName(getName()))) {
			case QUERY_CLICK:

				// �q�e�����o�d�߱���ö�JQueryConditionDto
				QueryConditionDTO qcDto = new QueryConditionDTO();
				qcDto.getFormData(this);
				// �N�e�@�B�J���o��QueryConditionDto�ഫ��SQL WHERE�ԭz��
				String sql = qcDto.toSql();

				// �����dao�A��,�]���G���ݲŦXQueryResultDto���ݩʩҩw�q,�ҥH����Ƭ۹���Dao.
				QueryResultService resultService = new QueryResultService(this);
				String[][] ret = (String[][]) resultService.getResult(sql);

				if (ret == null || ret.length <= 0) {
					message("�d�L����");
				}
				setTableData("QUERY_LIST", ret);
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

	/**
	 * �_��
	 * 
	 * @throws Throwable
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

	private String getActionName(String name) {
		name = name.toUpperCase();
		if (name.contains(".")) {
			return name.substring(name.indexOf(".") + 1);
		}
		return name.toUpperCase();
	}

}
