package oa.SampleEvaluation.service.subflow;

// oa/SampleEvaluation/subflowbuilder/StartTestFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TestFlowBuilder;
import oa.SampleEvaluationTest.model.SampleEvaluationTestFlowcHisPO;
import oa.SampleEvaluationTest.model.SampleEvaluationTestFlowcPO;
import oa.SampleEvaluationTest.model.SampleEvaluationTestPO;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

/**
 * The Class StartTestFlowService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class StartTestFlowService extends HprocImpl {

	/** The is test value. */
	String isTestValue;

	/** The coordinator. */
	String coordinator;

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
		sfb = new TestFlowBuilder();
		if (!sfb.isReady(this)) {
			message("試車流程中之配合人員不得為空");
			return arg0;
		}
		setNeedValue();
		BaseDao dao = new SampleEvaluationTestService(t);
		SampleEvaluationTestPO test = (SampleEvaluationTestPO) dao.findById(getValue("PNO") + "TEST");
		if (test != null) {
			message("已執行過試車流程");
			return arg0;
		}

		// update main data
		SyncDataService.mainFlowSync(t, this);

		// start sub flow

		String mailTitle = "簽核通知：" + this.getFunctionName();

		test = (SampleEvaluationTestPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationTestPO(), this);
		test.setOwnPno(test.getPno() + "TEST");

		sfb.setMainDto(test);
		sfb.setTalk(t);
		Flowc flowc = new SampleEvaluationTestFlowcPO();
		Flowc flowcHis = new SampleEvaluationTestFlowcHisPO();
		sfb.construct(flowc, flowcHis);
		SyncDataService.subFlowSync(t, this);
		// 有試製流程 寄出通知信
		String title = mailTitle + "_試車流程";
		MailToolInApprove.sendSubFlowMail(new BaseService(this), coordinator, test, title);
		message("已執行");
		setValue("START_TEST_FLOW_TEXT", "已執行!");
		setEditable("START_TEST_FLOW", false);
		setEditable("IS_TEST", false);
		return arg0;
	}

	/**
	 * Sets the need value.
	 *
	 * @throws Throwable the throwable
	 */
	public void setNeedValue() throws Throwable {
		isTestValue = getValue("IS_TEST").trim();
		coordinator = getValue("COORDINATOR").trim();
		t = getTalk();
	}

}
