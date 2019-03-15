package oa.SampleEvaluation.controller;

import java.sql.SQLException;

import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.enums.FlowStateEnum;
import oa.SampleEvaluation.enums.PageInitTypeEnum;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;
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
		addScript(UIHidderString.hideDmakerAddButton());
		String actionObjName = getActionName(getName()).trim();
		try {
			switch (PageInitTypeEnum.valueOf(actionObjName)) {
			case QUERY_PAGE_INIT:
				init.doQueryPageProcess();
				break;

			case ADD_PAGE_INIT:// 進入新增畫面
				init.doAddPageProcess();
				break;

			case PENING_PAGE_INIT:// 進入待處理畫面
				init.setBillOtherData();
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));

				break;

			case FLOW_PAGE_INIT:// 進入流程簽核畫面
				init.setBillOtherData();
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));
				// 所有欄位不可編輯
				setAllFieldUneditable();

				// 簽核完後跳至主頁面
				String pno = getValue("PNO").trim();
				if (pno.length() <= 0) {
					changeForm(getFunctionName());
				} else {
					showRejectWarning(pno);
				}

				// 流程畫面在各關卡的初始化switch處理方法
				switchByStateForFlowInit(FlowStateEnum.valueOf(getState().trim()));

				// 判斷子流程checkBox勾選狀況作介面調整
				setTextAndCheckIsSubFlowRunning();

				break;
			default:

				break;
			}
		} catch (EnumConstantNotPresentException e) {
			message("enum:PageInitType.clss 發生無法辨識的意外");
		}
		return null;

	}

	private void switchByStateForFlowInit(FlowStateEnum en) {

		switch (en) {
		case 課主管:
			break;
		case 組長:
			// 夾檔欄位可編輯
			setAllFileUploadFieldEditable();
			setEditable("IS_CHECK", true);
			setEditable("IS_TEST", true);
			setEditable("IS_TRIAL_PRODUCTION", true);
			setEditable("ASSESSOR", true);
			setEditable("NOTE", true);
			setEditable("LAB_EXE", true);
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

			// 根據對應表填入預設組長
			try {
				String[][] designee = getTalk()
						.queryFromPool("SELECT DESIGNEE FROM SAMPLE_EVALUATION_SUB_FLOW_SIGN_MAP WHERE DEPNO='"
								+ getValue("RECEIPT_UNIT") + "'");
				if (designee != null && designee.length > 0) {
					setValue("DESIGNEE", designee[0][0]);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case 採購經辦:
			setEditable("EVALUATION_RESULT", true);
			setEditable("FILE_EVALUATION_RESULT", true);
			break;
		case 文管人員:
			setAllFileUploadFieldEditable();
			setEditable("QR_NO", true);
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
