package oa.SampleEvaluation.service.subflow;

// oa/SampleEvaluation/subflowbuilder/StartTestFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TestFlowBuilder;
import oa.SampleEvaluationTest.model.SampleEvaluationTestPO;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class StartTestFlowService extends HprocImpl {
	String isTestValue;
	String coordinator;
	talk t;

	@Override
	public String action(String arg0) throws Throwable {
		setNeedValue();
		BaseDao dao = new SampleEvaluationTestService(t);
		SampleEvaluationTestPO test = (SampleEvaluationTestPO) dao.findById(getValue("PNO") + "TEST");
		if (test != null) {
			message("�w����L�ը��y�{");
			return arg0;
		}
		SubFlowBuilder sfb = null;
		sfb = new TestFlowBuilder();
		if (!sfb.isReady(this)) {
			message("�ը��y�{�����t�X�H�����o����");
			return arg0;
		}
		// update main data
		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluationPO se = (SampleEvaluationPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationPO(), this);
		daoservice.update(se);

		// start sub flow

		String mailTitle = "ñ�ֳq���G" + this.getFunctionName();

		test = (SampleEvaluationTestPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationTestPO(), this);
		test.setOwnPno(test.getPno() + "TEST");

		sfb.setMainDto(test);
		sfb.setTalk(t);
		sfb.construct();
		SyncDataService.subFlowSync(t, this);
		// ���ջs�y�{ �H�X�q���H
		String title = mailTitle + "_�ը��y�{";
		MailToolInApprove.sendSubFlowMail(new BaseService(this), coordinator, test, title);
		message("�w����");
		setValue("START_TEST_FLOW_TEXT", "�w����!");
		setEditable("START_TEST_FLOW", false);
		setEditable("IS_TEST", false);
		return arg0;
	}

	public void setNeedValue() throws Throwable {
		isTestValue = getValue("IS_TEST").trim();
		coordinator = getValue("COORDINATOR").trim();
		t = getTalk();
	}

}
