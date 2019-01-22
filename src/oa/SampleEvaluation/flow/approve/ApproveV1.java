package oa.SampleEvaluation.flow.approve;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.FlowcUtil;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class ApproveV1 extends bProcFlow {

	String nowState;
	talk t;
	String isCheckValue;
	String isTrialProdValue;

	public boolean action(String value) throws Throwable {
		nowState = getState();
		t = getTalk();
		SampleEvaluation s = null;
		BaseDao bdService = new SampleEvaluationService(t);
		BaseService service = new BaseService(this);
		String labExe = getValue("LAB_EXE").trim();
		String lassessor = getValue("ASSESSOR").trim();
		String docCtrler = getValue("DOC_CTRLER").trim();
		String designee = getValue("DESIGNEE").trim();
		designee.trim().split(" ");
		getValue("PNO");
		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		boolean ret = doReminder("");
		switch (FlowState.valueOf(nowState)) {
		case 組長:

			if ((isCheckValue.equals("1") || isTrialProdValue.equals("1")) && labExe.equals("")) {
				message("請選擇實驗室經辦人員");
				ret = false;
			}

			if (isTrialProdValue.equals("1") && lassessor.equals("")) {
				message("請選擇試製評估人員");
				ret = false;
			}

			if ((isTrialProdValue.equals("1") || isCheckValue.equals("1")) && docCtrler.equals("")) {
				message("請選擇文管人員");
				ret = false;
			}
			if (getValue("QR_NO").trim().equals("")) {
				message("請填寫QR號碼");
				ret = false;
			}
			if (ret) {
				// 更新主表請驗和試製評估勾選欄位
				// 更新主表 評估人員和實驗室經辦
				s = new SampleEvaluation();
				s = (SampleEvaluation) DtoUtil.setFormDataToDto(s, this);
				bdService.update(s);

				// 建立子流程FLOWC物件 使其出現在待簽核表單列表
				if (isCheckValue.equals("1")) {
					SampleEvaluationCheck secDto = (SampleEvaluationCheck) DtoUtil
							.setFormDataToDto(new SampleEvaluationCheck(), this);

					String ownPno = secDto.getPno() + "CHECK";
					secDto.setOwnPno(ownPno);
					SampleEvaluationCheckService secs = new SampleEvaluationCheckService(t);
					// insert check主檔
					secs.upsert(secDto);
					// insert check流程DATA
					FlowcUtil.goCheckSubFlow(ownPno, getValue("APPLICANT"), "填寫請驗單號", t);

					// 有請驗流程 寄出通知信
					String title = "簽核通知：" + this.getFunctionName() + "_請驗流程";
					MailToolInApprove.sendSubFlowMail(service, getValue("DOC_CTRLER"), secDto, title);

				}
				if (isTrialProdValue.equals("1")) {
					SampleEvaluationTp setDto = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(),
							this);
					String ownPno = setDto.getPno() + "TP";
					setDto.setOwnPno(ownPno);
					SampleEvaluationTpService sets = new SampleEvaluationTpService(t);
					// insert TP主檔
					sets.upsert(setDto);
					// insert TP流程DATA
					FlowcUtil.goTpSubFlow(ownPno, getValue("APPLICANT"), "評估人員", t);

					// 有試製流程 寄出通知信
					String title = "簽核通知：" + this.getFunctionName() + "_試製流程";
					MailToolInApprove.sendSubFlowMail(service, getValue("ASSESSOR"), setDto, title);
				}
			}
			break;
		case 受理單位主管分案:
			// 更新主表分案人欄位
			if (ret && !designee.equals("")) {
				t.execFromPool("UPDATE  sample_evaluation  SET DESIGNEE=?" + " where pno=?",
						new Object[] { designee, getValue("PNO") });
				t.execFromPool("UPDATE  sample_evaluation_check  SET DESIGNEE=?" + " where own_pno=?",
						new Object[] { designee, getValue("OWN_PNO") });
			} else {
				message("請選擇 受理單位指派人員 欄位");
				ret = false;
			}
			break;
		case 待處理:
		case 採購經辦確認:
			// 更新主表分案人欄位
			if (ret) {
				FileItemSetChecked();
				s = new SampleEvaluation();
				s = (SampleEvaluation) DtoUtil.setFormDataToDto(s, this);
				bdService.update(s);
			}
			break;
		case 採購經辦:

			// 當請驗與試製選項皆未勾選則核准後結案
			if ("0".equals(getValue("IS_CHECK")) && "0".equals(getValue("IS_TRIAL_PRODUCTION"))) {
				ret = true;

			} // 如果有勾選請驗試製任一選項則會判斷評估結果&夾檔是否為空
			else if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("評估結果與其夾檔不得為空");
				ret = false;
			} else if (ret) {
				FileItemSetChecked();
				BaseDao daoservice = null;
				if ("1".equals(getValue("IS_TRIAL_PRODUCTION"))) {
					daoservice = new SampleEvaluationTpService(t);
					SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(),
							this);
					tp.setOwnPno(tp.getPno() + "TP");
					daoservice.update(tp);
				}
				if ("1".equals(getValue("IS_CHECK"))) {
					daoservice = new SampleEvaluationCheckService(t);
					SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil
							.setFormDataToDto(new SampleEvaluationCheck(), this);
					ck.setOwnPno(ck.getPno() + "CHECK");
					daoservice.update(ck);
				}
				daoservice = new SampleEvaluationService(t);
				SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
				daoservice.update(se);
				ret = true;
			}
			break;

		default:
			break;
		}
		return ret;
	}

	private void FileItemSetChecked() {

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
		String space = "";
		for (int i = 0; i < 16; i++) {
			space += "&emsp;";
		}
		percent(100, space + "表單送出中，請稍候...<font color=white>");
		message("簽核完成");
		return true;
	}

}
