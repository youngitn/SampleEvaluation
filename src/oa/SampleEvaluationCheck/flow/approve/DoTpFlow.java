package oa.SampleEvaluationCheck.flow.approve;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.FlowcUtil;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class DoTpFlow extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"
		boolean ret = true;
		talk t = getTalk();
		ret = doReminder("將新增資訊傳遞通知單 試製簽核流程  ");
		if (ret) {

			if (getValue("IS_TRIAL_PRODUCTION").equals("0")) {
				if ("".equals(getValue("ASSESSOR")) || "".equals(getValue("LAB_EXE"))) {
					message("請指定評估人員/實驗室經辦");
					return false;
				}
				setValue("IS_TRIAL_PRODUCTION", "1");
				BaseService service = new BaseService(this);
				BaseDao bdservice = new SampleEvaluationTpService(t);
				SampleEvaluationTp tk = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
				tk.setOwnPno(tk.getPno() + "TP");
				bdservice.upsert(tk);
				FlowcUtil.goTpSubFlow(tk.getPno() + "TP", tk.getApplicant(), "評估人員", t);

				String title = "簽核通知：資訊傳遞通知單_試製流程";
				// 有試製流程 寄出通知信
				MailToolInApprove.sendSubFlowMail(service, getValue("ASSESSOR"), tk, title);

				// 同步請驗主檔
				bdservice = new SampleEvaluationCheckService(t);
				SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(),
						this);
				bdservice.upsert(ck);

				// 同步主檔
				bdservice = new SampleEvaluationService(t);
				SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
				bdservice.upsert(se);
				ret = true;
			} else if (getValue("IS_TRIAL_PRODUCTION").equals("1")) {
				message("無法重覆進行試製流程");
				ret = false;
			}
		}
		return ret;

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
