package oa.SampleEvaluation.controller;

import java.sql.SQLException;

import oa.SampleEvaluation.enums.PageInitType;
import oa.SampleEvaluation.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.global.BaseDao;
import oa.global.FormInitUtil;
import oa.global.UIHidderString;

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
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));

				break;

			case FLOW_PAGE_INIT:// 進入流程簽核畫面
				init.doPendingPageProcess();
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));
				// 所有欄位不可編輯
				setAllFieldUneditable();
				// 夾檔欄位可編輯
				setAllFileUploadFieldEditable();
				// 簽核完後跳至主頁面
				String pno = getValue("PNO").trim();
				if (pno.length() <= 0) {
					changeForm(getFunctionName());
				} else {
					showRejectWarning(pno);
				}

				// 流程畫面在各關卡的初始化switch處理方法
				switchByStateForFlowInit(FlowState.valueOf(getState().trim()));

				// 根據勾選的子流程將相關資料顯示
				if ("1".equals(getValue("IS_CHECK").trim())) {
					setVisible("SUB_FLOW_TAB_CHECK", true);
					setTextAndCheckWithSubFlow(new SampleEvaluationCheckService(getTalk()), getValue("PNO") + "CHECK");

				}
				if ("1".equals(getValue("IS_TRIAL_PRODUCTION").trim())) {
					setVisible("SUB_FLOW_TAB_TP", true);
					setTextAndCheckWithSubFlow(new SampleEvaluationTpService(getTalk()), getValue("PNO") + "TP");
				}
				if ("1".equals(getValue("IS_TEST").trim())) {
					setVisible("SUB_FLOW_TAB_TEST", true);
					setTextAndCheckWithSubFlow(new SampleEvaluationTestService(getTalk()), getValue("PNO") + "TEST");
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

	protected void setTextAndCheckWithSubFlow(BaseDao dao, String pno) {

		try {
			if (dao.findById(pno) != null) {
				if (pno.contains("CHECK")) {
					setValue("START_CHECK_FLOW_TEXT", "已執行!");
					setEditable("START_CHECK_FLOW", false);
					setEditable("IS_CHECK", false);
				}
				if (pno.contains("TEST")) {
					setValue("START_TEST_FLOW_TEXT", "已執行!");
					setEditable("START_TEST_FLOW", false);
					setEditable("IS_TEST", false);
				}
				if (pno.contains("TP")) {
					setValue("START_TP_FLOW_TEXT", "已執行!");
					setEditable("START_TP_FLOW", false);
					setEditable("IS_TRIAL_PRODUCTION", false);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void switchByStateForFlowInit(FlowState en) {

		switch (en) {
		case 組長:

			setEditable("IS_CHECK", true);
			setEditable("IS_TEST", true);
			setEditable("IS_TRIAL_PRODUCTION", true);
			setEditable("ASSESSOR", true);
			setEditable("NOTE", true);
			setEditable("LAB_EXE", true);
			setEditable("QR_NO", true);
			setEditable("DOC_CTRLER_TP", true);
			setEditable("DOC_CTRLER_CHECK", true);
			setEditable("QC_BOSS", true);
			setEditable("COORDINATOR", true);

			setEditable("START_CHECK_FLOW", true);
			setEditable("START_TP_FLOW", true);
			setEditable("START_TEST_FLOW", true);
			break;
		case 受理單位主管分案:
			setEditable("DESIGNEE", true);
			break;
		case 採購經辦:
			setEditable("EVALUATION_RESULT", true);
			setEditable("FILE_EVALUATION_RESULT", true);
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
