package oa.SampleEvaluationTest.flow.approve;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.FlowcUtil;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class DoCheckFlow extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"
		boolean ret = true;
		talk t = getTalk();
		ret = doReminder("將新增資訊傳遞通知單 請驗簽核流程  ");
		if (ret) {

			if (getValue("IS_CHECK").equals("0")) {

				if ("".equals(getValue("DOC_CTRLER"))) {
					message("請選擇文管人員");
					return false;
				}
				setValue("IS_CHECK", "1");
				BaseService service = new BaseService(this);
				BaseDao bdservice = new SampleEvaluationCheckService(t);
				SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(),
						this);
				ck.setOwnPno(ck.getPno() + "CHECK");
				bdservice.upsert(ck);
				FlowcUtil.goCheckSubFlow(ck.getPno() + "CHECK", ck.getApplicant(), "填寫請驗單號", t);

				String title = "簽核通知：資訊傳遞通知單_請驗流程";
				// 有請驗流程 寄出通知信
				MailToolInApprove.sendSubFlowMail(service, getValue("DOC_CTRLER"), ck, title);

				bdservice = new SampleEvaluationTpService(t);
				SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
				bdservice.upsert(tp);

				bdservice = new SampleEvaluationService(t);
				SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
				bdservice.upsert(se);
				ret = true;

			} else if (getValue("IS_CHECK").equals("1")) {
				message("無法重覆進行請驗流程");
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
