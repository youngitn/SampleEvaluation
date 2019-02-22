package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.Detail;
import oa.SampleEvaluation.enums.Actions;
import oa.SampleEvaluation.query.Query;

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
		// setProperty();
		try {
			// 按鈕動作處理進入點
			switch (Actions.valueOf(getActionName(getName()))) {
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
			message("enum:Actions.clss 發生無法辨識的意外");
		}
		return null;

	}

	/**
	 * 按下主查詢之明細鈕後所執行邏輯.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	private void showDetail() throws SQLException, Exception {

		Detail detail = new Detail(this);
		detail.show();
	}

	/**
	 * 主查詢
	 * 
	 * @throws Throwable
	 */
	private void doQuery() throws Throwable {
		Query query = new Query(this);
		String[][] list = query.getMainQueryResult();
		if (list == null || list.length <= 0) {
			message("查無紀錄");
		}
		setTableData("QUERY_LIST", list);
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
		// 新增不需cdo等額外其他資料
		AddUtil addUtil = new AddUtil(this);
		addUtil.emptyCheck(fieldMap);

	}

	private String getActionName(String name) {
		name = name.toUpperCase();
		if (name.contains(".")) {
			return name.substring(name.indexOf(".") + 1);
		}
		return name.toUpperCase();
	}

}
