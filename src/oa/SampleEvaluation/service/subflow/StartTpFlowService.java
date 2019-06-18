package oa.SampleEvaluation.service.subflow;

// oa/SampleEvaluation/subflowbuilder/StartTpFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TpFlowBuilder;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcHisPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

/**
 * The Class StartTpFlowService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class StartTpFlowService extends HprocImpl {

	/** The is trial prod value. */
	String isTrialProdValue;

	/** The doc ctrler tp. */
	String docCtrlerTp;

	/** The assessor. */
	String assessor;

	/** The lab exe. */
	String labExe;

	/** The t. */
	talk t;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	@Override
	public String action(String arg0) throws Throwable {

		SubFlowBuilder sfb = null;
		sfb = new TpFlowBuilder();
		if (!sfb.isReady(this)) {
			message("試製流程中之文管人員,試製人員,檢驗人員欄位皆不得為空");
			return arg0;
		}
		setNeedValue();
		BaseDao dao = new SampleEvaluationTpService(t);
		SampleEvaluationTpPO tp = (SampleEvaluationTpPO) dao.findById(getValue("PNO") + "TP");
		if (tp != null) {
			message("已執行過試製流程");
			return arg0;
		}

		SyncDataService.mainFlowSync(t, this);

		String mailTitle = "簽核通知：" + this.getFunctionName();

		tp = (SampleEvaluationTpPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationTpPO(), this);
		tp.setOwnPno(tp.getOwnPno());

		sfb.setMainDto(tp);
		sfb.setTalk(t);
		Flowc flowc = new SampleEvaluationTpFlowcPO();
		Flowc flowcHis = new SampleEvaluationTpFlowcHisPO();
		sfb.construct(flowc, flowcHis);
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

	/**
	 * Sets the need value.
	 *
	 * @throws Throwable the throwable
	 */
	public void setNeedValue() throws Throwable {
		isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		docCtrlerTp = getValue("DOC_CTRLER_TP").trim();
		assessor = getValue("ASSESSOR").trim();
		labExe = getValue("LAB_EXE").trim();
		t = getTalk();
	}

}
