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
 * 嘗試可測試寫法
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

		// 測試物件 卡在誤將BaseService依賴注入
		cdo = buildCdo();

		// 按鈕動作處理進入點
		switch (Actions.valueOf(actionObjName.trim())) {
		case QUERY_CLICK:
			// go
			MainQuery mquery = new MainQuery(cdo);

			String[][] list = mquery.getQueryResult();
			if (list == null || list.length <= 0) {
				message("查無紀錄");
			}
			setTableData("QUERY_LIST", list);
			break;
		case SAVE_CLICK:
			// message(getValue(cdo.getTableApplicantFieldName()));
			doSave();
			break;
		case SHOW_DETAIL_CLICK:// 這個動作比較尷尬 屬於載入畫面但卻是按鈕發動
			String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(getValue("QUERY_LIST.PNO"));
			String[][] allColumns = cdo.getTableAllColumn();
			if (ret != null && ret.length > 0) {
				fillData(ret, allColumns);
			} else {
				message("查無資料");
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

		// 必填欄位資料 (欄位名,欄位標題)
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("APPLICANT", "申請人");
		fieldMap.put("APP_TYPE", "申請類型");
		fieldMap.put("RECEIPT_UNIT", "受理單位");
		fieldMap.put("URGENCY", "急迫性");
		fieldMap.put("MATERIAL", "原物料名稱");

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

	// 將資料填入表單畫面欄位
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
	 * 載入有時間差問題 無法直接用addScript使用Jquery 直覺用法 在UI拉字符將引用標籤貼上後, 方法寫在按鈕內,如有onLoad事件,
	 * 一樣寫在字符讓表單讀取到就自動執行.
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
		flist.add("'簽核狀態'");
		flist.add("'明細'");
		flist.add("'簽核紀錄'");

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
