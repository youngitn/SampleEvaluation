package oa.SampleEvaluation.flow;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.enums.FlowStateEnum;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.CheckFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TestFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TpFlowBuilder;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.global.DtoUtil;

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

		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		this.isTestValue = getValue("IS_TEST").trim();
		boolean ret = doReminder("");

		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluationPO se = new SampleEvaluationPO();
		se.getFormData(this);

		switch (FlowStateEnum.valueOf(nowState)) {
		case 課主管:
			daoservice.update(se);
			// 通知經辦
			String mailTitle = "您申請的 " + this.getFunctionName() + " 主管已核准 ( 單號：" + se.getPno() + ") ";
			MailToolInApprove.sendNotifyToApplicant(new BaseService(this), se.getApplicant(), se, mailTitle);
			break;
		case 組長:
			SyncDataService.subFlowSync(t, this);

			SubFlowBuilder sfbCheck = new CheckFlowBuilder();
			SubFlowBuilder sfbTp = new TpFlowBuilder();
			SubFlowBuilder sfbTest = new TestFlowBuilder();
			if (!sfbCheck.isReady(this) || !sfbTp.isReady(this) || !sfbTest.isReady(this)) {
				message("子流程相關簽核人欄位不可空白");
				ret = false;
			}
			if (getValue("QR_NO") == null || "".equals(getValue("QR_NO").trim())) {
				message("QR號碼不可空白");
				ret = false;
			}
			break;
		case 受理單位主管分案:

			if (ret && !designee.equals("")) {
				daoservice.update(se);
			} else {
				message("ˋ主管指派人員(組長)不可空白");
				ret = false;
			}
			break;
		case 採購經辦確認:
		case 待處理:

			if (ret) {
				daoservice.update(se);
			}
			break;
		case 採購經辦:

			if ("0".equals(this.isCheckValue) && "0".equals(this.isTrialProdValue) && "0".equals(this.isTestValue)) {
				ret = true;

			} else if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("評估結果與夾檔不可空白");
				ret = false;
			} else if (ret) {

				SyncDataService.subFlowSync(t, this);
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

	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "確定送出?", "溫馨提醒", 0);
		if (result == 1) {
			message("取消送出!");
			return false;
		}
		StringBuilder space = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			space.append("&emsp;");
		}
		percent(100, space.toString() + "處理中<font color=white>");
		message("已送出");
		return true;
	}

}
