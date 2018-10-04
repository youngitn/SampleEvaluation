package oa.SampleEvaluation;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;

import com.ysp.service.BaseService;

import jcx.jform.hproc;
import oa.SampleEvaluation.enums.*;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.UIHidderString;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl; 

/**
 * 嘗試可測試寫法sh
 * 
 * @author u52116
 *
 */
public class SampleEvaluationPageInitController extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	BaseService service;

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// 表單載入後處理
		// 各頁面載入處理於類別中實作
		// importJquery();
		service = new BaseService(this);
		FormInitUtil init = new FormInitUtil(this);
		setValue("NOW_INIT", getName());
		this.cdo = new CommonDataObj(service, "PNO", "APPLICANT");

		String actionObjName = getActionName(getName());
		File saveFile = new File("Data.txt");
		try {
			FileWriter fwriter = new FileWriter(saveFile);
			fwriter.write(actionObjName);
			fwriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		// 按鈕動作處理進入點
		switch (PageInitType.valueOf(actionObjName.trim())) {
		case QUERY_PAGE_INIT:// 進入查詢畫面
			addScript(UIHidderString.hideDmakerAddButton());
			init.doQueryPageProcess();
			break;

		case ADD_PAGE_INIT:// 進入新增畫面
			addScript(UIHidderString.hideDmakerAddButton());
			init.doAddPageProcess();
			break;

		case PENING_PAGE_INIT:// 進入待處理畫面

			init.doPendingPageProcess();
			break;

		case DETAIL_PAGE_INIT:// 進入明細畫面
			init.doDetailPageProcess();
			break;
		case FLOW_PAGE_INIT:// 進入流程簽核畫面
			init.doPendingPageProcess();
			// 簽核完後跳至主頁面
			String pno = getValue("PNO").trim();
			if (pno.length() <= 0) {
				changeForm(getFunctionName());
			} else {
				showRejectWarning(pno);
			}
			// 如果帶出的資料 試製選項有打勾 就顯示評估人員
			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")) {
				setVisible("ASSESSOR", true);
			}
			if (getState().trim().equals("組長")) {
				setEditable("IS_CHECK", true);
				setEditable("IS_TRIAL_PRODUCTION", true);

				setEditable("ASSESSOR", true);
				setEditable("LAB_EXE", true);
				setEditable("QR_NO", true);
				setEditable("DOC_CTRLER", true);

			}
			if (getState().trim().equals("試製單號填寫")) {
				setEditable("NOTIFY_NO_TRIAL_PROD", true);
			}
			if (getState().trim().equals("受理單位主管分案")) {
				setEditable("DESIGNEE", true);
			}
			if (getState().trim().equals("待處理")) {
				// 相關處理寫在UI的背景區塊ADD_BACKGROUND中
				// 因為這邊的邏輯 在待處理關卡吃不到
			}

			break;
		default:

			break;
		}
		return null;

	}

	/**
	 * @param pno
	 * @throws SQLException
	 * @throws Exception
	 */
	private void showRejectWarning(String pno) throws SQLException, Exception {
		String sql = "select f_inp_info from " + getTableName() + "_flowc where PNO = '" + pno + "'";
		String[][] ret = getTalk().queryFromPool(sql);
		if (ret.length > 0) {
			String memo = ret[0][0];
			if (memo.startsWith("[退簽]")) {
				addScript("callRejectWarning();");
			}
		}
	}

	public void detailPage() throws SQLException, Exception {

		// 塞入主檔資料
		String key = getValue("QUERY_LIST.PNO");

		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(key);
		String[][] allColumns = cdo.getTableAllColumn();
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], ret[0][i]);
			}
		} else {
			message("發生錯誤，找不到此表單資料！");
		}
	}

	public String getActionName(String Name) {

		Name = Name.toUpperCase();
		if ("[FORM INIT] ".equals(Name) || "[FORM INIT] QUERYPAGE".equals(Name)) {
			return "QUERY_PAGE_INIT";
		} else if ("[FORM INIT] ADDPAGE".equals(Name)) {
			return "ADD_PAGE_INIT";
		} else if (Name.startsWith("[FORM INIT] [FLOW]")) {

			if ("[FORM INIT] [FLOW].待處理".equals(Name)) {
				return "PENING_PAGE_INIT";
			} else {
				return "FLOW_PAGE_INIT";
			}

		} else if (Name.startsWith("[FORM INIT] DETAILPAGE")) {
			return "DETAIL_PAGE_INIT";
		} else if (Name.startsWith("[FORM INIT] SIGNFLOWHISTORY")) {

			return "SIGN_FLOW_HISTORY_PAGE_INIT";
		}
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	public void UIHide() {
		// 隱藏Dmaker底層新增按鈕
		addScript(UIHidderString.hideDmakerAddButton());

		// 隱藏上方標籤
		addScript(UIHidderString.hideDmakerFlowPanel());
		// 隱藏詳細詳細列表功能
		addScript(UIHidderString.hideFlowButtonDetail());

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
