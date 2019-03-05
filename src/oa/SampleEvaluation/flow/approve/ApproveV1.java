package oa.SampleEvaluation.flow.approve;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.SyncData;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTest.dto.SampleEvaluationTest;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class ApproveV1 extends bProcFlow {

	String nowState;
	talk t;
	String isCheckValue;
	String isTrialProdValue;
	String isTestValue;

	public boolean action(String value) throws Throwable {
		fileItemSetChecked();
		nowState = getState();
		t = getTalk();

		String designee = getValue("DESIGNEE").trim();

		// designee.trim().split(" ");
		getValue("PNO");
		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		this.isTestValue = getValue("IS_TEST").trim();
		boolean ret = doReminder("");
		// 建立帶資料DTO&DAO

		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluation se = new SampleEvaluation();
		se.getFormData(this);

		switch (FlowState.valueOf(nowState)) {
		case 課主管:
			daoservice.update(se);
			break;
		case 組長:
			SyncData.subFlowSync(t, this);
			break;
		case 受理單位主管分案:
			// 更新主表分案人欄位
			if (ret && !designee.equals("")) {
				daoservice.update(se);
			} else {
				message("請選擇 受理單位指派人員(組長) 欄位");
				ret = false;
			}
			break;
		case 待處理:
		case 採購經辦確認:
			// 更新主表分案人欄位
			if (ret) {
				daoservice.update(se);
			}
			break;
		case 採購經辦:

			// 當請驗與試製選項皆未勾選則核准後結案
			if ("0".equals(this.isCheckValue) && "0".equals(this.isTrialProdValue) && "0".equals(this.isTestValue)) {
				ret = true;

			} // 如果有勾選請驗試製任一選項則會判斷評估結果&夾檔是否為空
			else if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("評估結果與其夾檔不得為空");
				ret = false;
			} else if (ret) {

				SyncData.subFlowSync(t, this);
				ret = true;
			}
			break;

		default:
			break;
		}
		return ret;
	}

	private void fileItemSetChecked() {

		if (!getValue("FILE_SPEC").equals("")) {
			setValue("PROVIDE_SPEC", "1");
		}
		if (!getValue("FILE_COA").equals("")) {
			setValue("PROVIDE_COA", "1");
		}
		if (!getValue("FILE_SDS").equals("")) {
			setValue("PROVIDE_SDS", "1");
		}
		if (!getValue("FILE_OTHERS").equals("")) {
			setValue("PROVIDE_OTHERS", "1");
		}
		if (!getValue("FILE_TEST_METHOD").equals("")) {
			setValue("PROVIDE_TEST_METHOD", "1");
		}
	}

	/**
	 * 溫馨提醒 不傳入 回傳true/false
	 */
	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "確定送出表單？", "溫馨提醒", 0);
		if (result == 1) {
			message("已取消送出表單");
			return false;
		}
		StringBuilder space = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			space.append("&emsp;");
		}
		percent(100, space.toString() + "表單送出中，請稍候...<font color=white>");
		message("簽核完成");
		return true;
	}

}
