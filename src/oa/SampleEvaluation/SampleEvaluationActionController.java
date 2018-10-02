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
import oa.SampleEvaluation.common.UIHidderString;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;

/**
 * 嘗試可測試寫法
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	public String functionName = "樣品評估申請作業";
	public boolean isTest = false;
	BaseService service;

	@Override
	public String action(String arg0) throws Throwable {

		// for enum switch
		String actionObjName = getActionName(getName());

		service = new BaseService(this);

		this.cdo = new CommonDataObj(service, "PNO", "APPLICANT");

		// 按鈕動作處理進入點
		switch (Actions.valueOf(actionObjName.trim())) {
		case QUERY_CLICK:
			// go
			doQuery();
			break;
		case SAVE_CLICK:
			doSave();
			break;
		case SHOW_DETAIL_CLICK:// 這個動作比較尷尬 屬於載入畫面但卻是按鈕發動
			readAndFillData(getValue("QUERY_LIST.PNO"));
			break;
		default:
			break;
		}
		return null;

	}

	public void doSave() throws Throwable {

		// 必填欄位資料 (欄位名,欄位標題)
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("APPLICANT", "申請人");
		fieldMap.put("APP_TYPE", "申請類型");
		fieldMap.put("RECEIPT_UNIT", "受理單位");
		fieldMap.put("URGENCY", "急迫性");

		// 新增不需cdo等額外其他資料
		AddUtil addUtil = new AddUtil(service);
		ArrayList<String> ret = addUtil.emptyCheck(fieldMap);
		if (ret != null && ret.size() > 0) {
			message("以下欄位請做選擇或輸入:" + ret);
		} else {
			int result = showConfirmDialog("確定送出表單？", "溫馨提醒", 0);
			if (result != 1) {
				// 產生單號
				String uuid = addUtil.getUUID(cdo);

				// DMAKER 內建ADD功能 需將資料塞進去表單欄位才吃的到
				setValue(cdo.getTablePKName(), uuid);
				// confirm = true 控制是否真的送出
				if (confirm) {
					// 觸發Dmaker內建的新增鈕來送出表單
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	public void doQuery() throws Throwable {
		ArrayList<String> flist = new ArrayList<String>();
		flist.add("PNO");
		flist.add("APPLICANT");
		flist.add("APP_TYPE");
		flist.add("URGENCY");
		flist.add("APP_DATE");
		flist.add("'簽核狀態'");
		flist.add("'明細'");
		flist.add("'簽核紀錄'");

		cdo.setQueryResultShowTableFieldList(flist);

		MainQuery mquery = new MainQuery(cdo);
		String[][] list = mquery.getQueryResult();
		// TODO 移動到MainQuery中
		if (list == null || list.length <= 0) {
			message("查無紀錄");
		}
		setTableData("QUERY_LIST", list);
	}

	/**
	 * 目前為免洗方法 TODO ,單號 入參數可重用
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */

	public void readAndFillData(String key) throws SQLException, Exception {

		// 塞入主檔資料
//		String key = getValue("QUERY_LIST.PNO");

		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(key);
		String[][] allColumns = cdo.getTableAllColumn();
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], ret[0][i]);
			}
			FormInitUtil init = new FormInitUtil(this);
			init.doDetailPageProcess();
			addScript(UIHidderString.hideDmakerAddButton());
		} else {
			message("發生錯誤，找不到此表單資料！");
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
	 * 載入有時間差問題 無法直接用addScript使用Jquery 直覺用法 在UI拉字符將引用標籤貼上後, 方法寫在按鈕內,如有onLoad事件,
	 * 一樣寫在字符讓表單讀取到就自動執行.
	 */
	public void importJquery() {
		addScript("var script = document.createElement('script');"
				+ "script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';"
				+ "script.type = 'text/javascript';" + "document.getElementsByTagName('head')[0].appendChild(script);");

	}

}
