package oa.SampleEvaluation.subflowbuilder;

// oa/SampleEvaluation/subflowbuilder/StartTestFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.common.SyncData;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTest.dto.SampleEvaluationTest;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class StartTestFlow extends HprocImpl {
	String isTestValue;
	String coordinator;
	talk t;

	@Override
	public String action(String arg0) throws Throwable {
		setNeedValue();
		BaseDao dao = new SampleEvaluationTestService(t);
		SampleEvaluationTest test = (SampleEvaluationTest) dao.findById(getValue("PNO") + "TEST");
		if (test != null) {
			message("�w����L�ը��y�{");
			return arg0;
		}
		if ("1".equals(isTestValue) && "".equals(coordinator)) {
			message("�ը��y�{�����t�X�H�����o����");
			return arg0;
		}
		// update main data
		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataIntoDto(new SampleEvaluation(), this);
		daoservice.update(se);

		// start sub flow
		SubFlowBuilder sfb = null;
		String mailTitle = "ñ�ֳq���G" + this.getFunctionName();

		test = (SampleEvaluationTest) DtoUtil.setFormDataIntoDto(new SampleEvaluationTest(), this);
		test.setOwnPno(test.getPno() + "TEST");
		sfb = new TestFlowBuilder();
		sfb.setMainDto(test);
		sfb.setTalk(t);
		sfb.construct();
		SyncData.subFlowSync(t, this);
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
		isTestValue = getValue("IS_CHECK").trim();
		coordinator = getValue("COORDINATOR").trim();
		t = getTalk();
	}

}
