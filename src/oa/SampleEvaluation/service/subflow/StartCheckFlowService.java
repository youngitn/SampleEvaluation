package oa.SampleEvaluation.service.subflow;

// oa/SampleEvaluation/subflowbuilder/StartCheckFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.CheckFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
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
	
	/** The is check value. */
	String isCheckValue;
	
	/** The doc ctrler check. */
	String docCtrlerCheck;
	
	/** The qc boss. */
	String qcBoss;

	/** The t. */
	talk t;

	/* (non-Javadoc)
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	@Override
	public String action(String arg0) throws Throwable {
		setNeedValue();
		BaseDao dao = new SampleEvaluationCheckService(t);
		SampleEvaluationCheckPO ck = (SampleEvaluationCheckPO) dao.findById(getValue("PNO") + "CHECK");
		if (ck != null) {
			message("已執行過請驗流程");
			return arg0;
		}
		// if ("1".equals(isCheckValue)) {
		SubFlowBuilder sfb = null;
		sfb = new CheckFlowBuilder();
		if (!sfb.isReady(this)) {
			message("請驗流程中之文管人員與品保課長欄位皆不得為空");
			return arg0;
		}
		// }
		// update main data
		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluationPO se = (SampleEvaluationPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationPO(), this);
		daoservice.update(se);
		// start sub flow

		String mailTitle = "簽核通知：" + this.getFunctionName();

		ck = (SampleEvaluationCheckPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationCheckPO(), this);
		ck.setOwnPno(ck.getPno() + "CHECK");

		sfb.setMainDto(ck);
		sfb.setTalk(t);
		sfb.construct();
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

	/**
	 * Sets the need value.
	 *
	 * @throws Throwable the throwable
	 */
	public void setNeedValue() throws Throwable {
		isCheckValue = getValue("IS_CHECK").trim();
		docCtrlerCheck = getValue("DOC_CTRLER_CHECK").trim();
		qcBoss = getValue("QC_BOSS").trim();
		t = getTalk();
	}

}
