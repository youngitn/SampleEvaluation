package oa.SampleEvaluationCheck.flow.approve;

import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class Approve extends bProcFlow {

	String table_name = "MIS_SERVICE";

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"

		String state = getState();
		boolean ret = doReminder("");
		switch (FlowState.valueOf(state)) {
		case 文管人員:
			/*
			 * 判斷請驗單號欄位是否空值會在填寫請驗單號舊處理完畢 到實驗室經辦時 只會同步更新三表
			 */

			if (getValue("NOTIFY_NO_CHECK").trim().equals("") || getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				message("請填寫原料或試製品請驗單號,如果未進行請驗請直接在欄位中填寫原因.");
				ret = false;
			}
			if (ret) {
				// 三表同步
				// BaseService service = new BaseService(this);

				BaseDao service = new SampleEvaluationTpService(getTalk());
				SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
				tp.setOwnPno(tp.getPno() + "TP");
				service.update(tp);

				service = new SampleEvaluationCheckService(getTalk());
				SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(),
						this);
				ck.setOwnPno(ck.getPno() + "CHECK");
				service.update(ck);

				service = new SampleEvaluationService(getTalk());
				SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
				service.update(se);
				// message("簽核完成！");
			}
			return ret;

		case 組長:// 目前未開放這個關卡
			// 能退?要退去哪?
			// 建立子流程FLOWC物件 使其出現在待簽核表單列表

			break;
		default:

			break;
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
