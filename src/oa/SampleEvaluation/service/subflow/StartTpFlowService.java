package oa.SampleEvaluation.service.subflow;

// oa/SampleEvaluation/subflowbuilder/StartTpFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TpFlowBuilder;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class StartTpFlowService extends HprocImpl {
	String isTrialProdValue;
	String docCtrlerTp;
	String assessor;
	String labExe;
	talk t;

	@Override
	public String action(String arg0) throws Throwable {
		setNeedValue();
		BaseDao dao = new SampleEvaluationTpService(t);
		SampleEvaluationTpPO tp = (SampleEvaluationTpPO) dao.findById(getValue("PNO") + "TP");
		if (tp != null) {
			message("已執行過試製流程");
			return arg0;
		}
		// if ("1".equals(isTrialProdValue)) {
		SubFlowBuilder sfb = null;
		sfb = new TpFlowBuilder();
		if (!sfb.isReady(this)) {
			message("試製流程中之文管人員,試製人員,檢驗人員欄位皆不得為空");
			return arg0;
		}
		// }
		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluationPO se = (SampleEvaluationPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationPO(), this);
		daoservice.update(se);

		String mailTitle = "簽核通知：" + this.getFunctionName();

		tp = (SampleEvaluationTpPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationTpPO(), this);
		tp.setOwnPno(tp.getOwnPno());

		sfb.setMainDto(tp);
		sfb.setTalk(t);
		sfb.construct();
		SyncDataService.subFlowSync(t, this);
		// 有請驗流程 寄出通知信
		String title = mailTitle + "_試製流程";
		MailToolInApprove.sendSubFlowMail(new BaseService(this), assessor, tp, title);
		message("已執行");
		setValue("START_TP_FLOW_TEXT", "已執行!");
		setEditable("START_TP_FLOW", false);
		setEditable("IS_TRIAL_PRODUCTION", false);
		return arg0;
	}

	public void setNeedValue() throws Throwable {
		isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		docCtrlerTp = getValue("DOC_CTRLER_TP").trim();
		assessor = getValue("ASSESSOR").trim();
		labExe = getValue("LAB_EXE").trim();
		t = getTalk();
	}

}
