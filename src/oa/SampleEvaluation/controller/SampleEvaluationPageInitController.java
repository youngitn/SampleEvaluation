package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

import oa.SampleEvaluation.enums.*;
import oa.SampleEvaluation.common.global.FormInitUtil;
import oa.SampleEvaluation.common.global.UIHidderString;

/**
 * 判斷頁面名稱並於載入後執行
 * 
 * @author u52116
 *
 */
public class SampleEvaluationPageInitController extends HprocImpl {

	@Override
	public String action(String arg0) throws Throwable {
		// 表單載入後處理
		// 各頁面載入處理於類別中實作

		FormInitUtil init = new FormInitUtil(this);

		String actionObjName = getActionName(getName()).trim();
		try {
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
				switchByState(FlowState.valueOf(getState().trim()));
				// 如果帶出的資料 試製選項有打勾 就顯示評估人員
				if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")) {
					setVisible("ASSESSOR", true);
				}
				break;
			default:

				break;
			}
		} catch (EnumConstantNotPresentException e) {
			message("enum:PageInitType.clss 發生無法辨識的意外");
		}
		return null;

	}

	private void switchByState(FlowState en) {
		switch (en) {
		case 待處理:
		case 採購經辦確認:
//			Hashtable h = getAllcLabels();
//			for (Enumeration e = h.keys(); e.hasMoreElements();) {
//				String s = e.nextElement().toString();
//				setEditable(s, true);
//
//			}
			break;
		case 組長:
			setAllFieldUneditable();
			setEditable("IS_CHECK", true);
			setEditable("IS_TRIAL_PRODUCTION", true);

			setEditable("ASSESSOR", true);
			setEditable("LAB_EXE", true);
			setEditable("QR_NO", true);
			setEditable("DOC_CTRLER", true);
			break;
		case 受理單位主管分案:
			setAllFieldUneditable();
			setEditable("DESIGNEE", true);
			break;
		default:
			break;
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
