package oa.SampleEvaluation.flow.approve;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluation.subflowbuilder.CheckFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.TestFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.TpFlowBuilder;
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
		nowState = getState();
		t = getTalk();

		String labExe = getValue("LAB_EXE").trim();
		String assessor = getValue("ASSESSOR").trim();
		String docCtrlerTp = getValue("DOC_CTRLER_TP").trim();
		String docCtrlerCheck = getValue("DOC_CTRLER_CHECK").trim();
		String qcBoss = getValue("QC_BOSS").trim();
		String designee = getValue("DESIGNEE").trim();
		String coordinator = getValue("COORDINATOR").trim();
		// designee.trim().split(" ");
		getValue("PNO");
		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		this.isTestValue = getValue("IS_TEST").trim();
		boolean ret = doReminder("");
		// 建立帶資料DTO&DAO

		SampleEvaluationTpService daoserviceTp = new SampleEvaluationTpService(t);
		SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
		tp.setOwnPno(tp.getPno() + "TP");

		SampleEvaluationCheckService daoserviceCheck = new SampleEvaluationCheckService(t);
		SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(), this);
		ck.setOwnPno(ck.getPno() + "CHECK");

		SampleEvaluationTestService daoserviceTest = new SampleEvaluationTestService(t);
		SampleEvaluationTest test = (SampleEvaluationTest) DtoUtil.setFormDataToDto(new SampleEvaluationTest(), this);
		test.setOwnPno(test.getPno() + "TEST");

		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);

		switch (FlowState.valueOf(nowState)) {
		case 組長:

			if ("1".equals(isCheckValue)) {
				if ("".equals(docCtrlerCheck) || "".equals(qcBoss)) {
					message("請驗流程中之文管人員與品保課長欄位皆不得為空");
					ret = false;
				}
			}

			if ("1".equals(isTrialProdValue)) {
				if ("".equals(docCtrlerTp) || "".equals(assessor) || "".equals(labExe)) {
					message("試製流程中之文管人員,試製人員,檢驗人員欄位皆不得為空");
					ret = false;
				}
			}

			if ("1".equals(isTestValue) && "".equals(coordinator)) {
				message("試車流程中之配合人員不得為空");
				ret = false;
			}

			if (ret) {

				daoservice.update(se);
				SubFlowBuilder sfb = null;
				String mailTitle = "簽核通知：" + this.getFunctionName();
				// 建立子流程FLOWC物件 使其出現在待簽核表單列表
				if (isCheckValue.equals("1")) {
					sfb = new CheckFlowBuilder();
					sfb.setStartGateName("文管人員");
					sfb.setMainDto(ck);
					sfb.setTalk(t);
					sfb.construct();

					// 有請驗流程 寄出通知信
					String title = mailTitle + "_請驗流程";
					MailToolInApprove.sendSubFlowMail(new BaseService(this), docCtrlerCheck, ck, title);

				}
				if (isTrialProdValue.equals("1")) {

					sfb = new TpFlowBuilder();
					sfb.setStartGateName("試製人員");
					sfb.setMainDto(tp);
					sfb.setTalk(t);
					sfb.construct();
					// 有試製流程 寄出通知信
					String title = mailTitle + "_試製流程";
					MailToolInApprove.sendSubFlowMail(new BaseService(this), assessor, tp, title);
				}
				// 試車流程
				if (isTestValue.equals("1")) {

					sfb = new TestFlowBuilder();
					sfb.setStartGateName("配合人員");
					sfb.setMainDto(test);
					sfb.setTalk(t);
					sfb.construct();
					// 有試製流程 寄出通知信
					String title = mailTitle + "_試車流程";
					MailToolInApprove.sendSubFlowMail(new BaseService(this), coordinator, test, title);
				}
			}
			break;
		case 受理單位主管分案:
			// 更新主表分案人欄位
			if (ret && !designee.equals("")) {
				daoserviceCheck.update(ck);
				daoservice.update(se);
			} else {
				message("請選擇 受理單位指派人員 欄位");
				ret = false;
			}
			break;
		case 待處理:
		case 採購經辦確認:
			// 更新主表分案人欄位
			if (ret) {
				fileItemSetChecked();
				daoservice.update(se);
			}
			break;
		case 採購經辦:

			// 當請驗與試製選項皆未勾選則核准後結案
			if ("0".equals(this.isCheckValue) && "0".equals(this.isTrialProdValue)) {
				ret = true;

			} // 如果有勾選請驗試製任一選項則會判斷評估結果&夾檔是否為空
			else if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("評估結果與其夾檔不得為空");
				ret = false;
			} else if (ret) {
				fileItemSetChecked();
				if ("1".equals(getValue("IS_TRIAL_PRODUCTION"))) {
					daoserviceTp.update(tp);
				}

				if ("1".equals(getValue("IS_CHECK"))) {
					daoserviceCheck.update(ck);
				}

				daoservice.update(se);
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
