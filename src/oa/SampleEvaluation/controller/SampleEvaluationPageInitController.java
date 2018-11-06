package oa.SampleEvaluation.controller;

import java.sql.SQLException;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.enums.*;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.UIHidderString;

/**
 * 判斷頁面名稱並於載入後執行
 * 
 * @author u52116
 *
 */
public class SampleEvaluationPageInitController extends Controller {

	private CommonDataObj cdo;
	BaseService service;

	@Override
	public String action(String arg0) throws Throwable {
		// 表單載入後處理
		// 各頁面載入處理於類別中實作

		service = new BaseService(this);
		FormInitUtil init = new FormInitUtil(this);
		setValue("receiveNowPage", getName());
		this.cdo = new CommonDataObj(getTalk(), getTableName(), "PNO", "APPLICANT");

		String actionObjName = getActionName(getName()).trim();

		switch (PageInitType.valueOf(actionObjName)) {
		case QUERY_PAGE_INIT:
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
	private void showRejectWarning(String pno) throws Exception {
		String sql = "select f_inp_info from " + getTableName() + "_flowc where PNO = '" + pno + "'";
		String[][] ret = getTalk().queryFromPool(sql);
		if (ret.length > 0) {
			String memo = ret[0][0];
			if (memo.startsWith("[退簽]")) {
				addScript("callRejectWarning();");
			}
		}
	}

	private String getActionName(String name) {

		name = name.toUpperCase();
		if ("[FORM INIT] ".equals(name) || "[FORM INIT] QUERYPAGE".equals(name)) {
			return "QUERY_PAGE_INIT";
		} else if ("[FORM INIT] ADDPAGE".equals(name)) {
			return "ADD_PAGE_INIT";
		} else if (name.startsWith("[FORM INIT] [FLOW]")) {

			if ("[FORM INIT] [FLOW].待處理".equals(name)) {
				return "PENING_PAGE_INIT";
			} else {
				return "FLOW_PAGE_INIT";
			}

		} else if (name.startsWith("[FORM INIT] FLOWPAGE")) {
			return "DETAIL_PAGE_INIT";
		} else if (name.startsWith("[FORM INIT] SIGNFLOWHISTORY")) {

			return "SIGN_FLOW_HISTORY_PAGE_INIT";
		}
		if (name.contains(".")) {
			return name.substring(name.indexOf('.') + 1);
		}
		return name.toUpperCase();

	}

}
