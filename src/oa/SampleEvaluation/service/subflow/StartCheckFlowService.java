package oa.SampleEvaluation.service.subflow;

// oa/SampleEvaluation/subflowbuilder/StartCheckFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.CheckFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcHisPO;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcPO;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

/**
 * The Class StartCheckFlowService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class StartCheckFlowService extends HprocImpl {

	String isCheckValue;
	String docCtrlerCheck;
	String qcBoss;
	talk t;

	@Override
	public String action(String arg0) throws Throwable {

		SubFlowBuilder sfb = null;
		sfb = new CheckFlowBuilder();
		if (!sfb.isReady(this)) {
			message("請驗流程中之文管人員與品保課長欄位皆不得為空");
			return arg0;
		}
		// 必要欄位取值
		setNeedValue();
		// 確認是否已經執行過子流程
		BaseDao dao = new SampleEvaluationCheckService(t);
		SampleEvaluationCheckPO ck = (SampleEvaluationCheckPO) dao.findById(getValue("PNO") + "CHECK");
		if (ck != null) {
			message("已執行過請驗流程");
			return arg0;
		}

		SyncDataService.mainFlowSync(t, this);

		String mailTitle = "簽核通知：" + this.getFunctionName();

		ck = (SampleEvaluationCheckPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationCheckPO(), this);
		ck.setOwnPno(ck.getPno() + "CHECK");
		Flowc flowc = new SampleEvaluationCheckFlowcPO();
		Flowc flowcHis = new SampleEvaluationCheckFlowcHisPO();
		sfb.setMainDto(ck);
		sfb.setTalk(t);
		sfb.construct(flowc, flowcHis);
		SyncDataService.subFlowSync(t, this);
		// 有請驗流程 寄出通知信
		String title = mailTitle + "_請驗流程";
		MailToolInApprove.sendSubFlowMail(new BaseService(this), docCtrlerCheck, ck, title);
		message("已執行");
		setValue("START_CHECK_FLOW_TEXT", "已執行!");
		setEditable("START_CHECK_FLOW", false);
		setEditable("IS_CHECK", false);
		return arg0;
	}

	public void setNeedValue() throws Throwable {
		isCheckValue = getValue("IS_CHECK").trim();
		docCtrlerCheck = getValue("DOC_CTRLER_CHECK").trim();
		qcBoss = getValue("QC_BOSS").trim();
		t = getTalk();
	}

}
