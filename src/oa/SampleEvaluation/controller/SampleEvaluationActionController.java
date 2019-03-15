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

		// 類別屬性初始化設定
		try {
			// 按鈕動作處理進入點

			switch (ActionsEnum.valueOf(getActionName(getName()))) {
			case QUERY_CLICK:

				// 從畫面取得查詢條件並塞入QueryConditionDto
				QueryConditionDTO qcDto = new QueryConditionDTO();
				qcDto.getFormData(this);
				// 將前一步驟取得的QueryConditionDto轉換成SQL WHERE敘述式
				String sql = qcDto.toSql();

				// 實體化dao服務,因結果欄位需符合QueryResultDto的屬性所定義,所以實體化相對應Dao.
				QueryResultService resultService = new QueryResultService(this);
				String[][] ret = (String[][]) resultService.getResult(sql);

				if (ret == null || ret.length <= 0) {
					message("查無紀錄");
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
						int result = showConfirmDialog("確定將原暫存檔覆蓋嗎?", "溫馨提醒", 0);
						if (result == 1) {
							message("取消送出!");
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
			message("enum:Actions.clss 發生無法辨識的意外");
		}
		return null;

	}

	/**
	 * 起單
	 * 
	 * @throws Throwable
	 */
	private void doSave() throws Throwable {
		// 手動建立必填欄位資料 (欄位名,欄位標題)
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("APPLICANT", "申請人");
		fieldMap.put("APP_TYPE", "申請類型");
		fieldMap.put("RECEIPT_UNIT", "受理單位");
		fieldMap.put("URGENCY", "急迫性");
		fieldMap.put("MATERIAL", "原物料名稱");
		fieldMap.put("AB_CODE", "AB編號");
		fieldMap.put("MFR", " 製造商");
		fieldMap.put("MFG_LOT_NO", "製造批號");
		fieldMap.put("SUPPLIER", "供應商");
		fieldMap.put("QTY", "數量");
		fieldMap.put("UNIT", "單位");
		fieldMap.put("SAP_CODE", "SAP物料編號");
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
