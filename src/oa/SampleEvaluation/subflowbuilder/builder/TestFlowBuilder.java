package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluationTest.model.SampleEvaluationTestPO;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.SampleEvaluationTest.model.SampleEvaluationTestFlowcPO;
import oa.SampleEvaluationTest.model.SampleEvaluationTestFlowcHisPO;

public class TestFlowBuilder extends SubFlowBuilder {
	public TestFlowBuilder() {
		this.startGateName = "配合人員";
	}

	@Override
	public void setAndInsertFlowData() throws SQLException, Exception {
		insertFlowData(new SampleEvaluationTestFlowcPO());
		insertFlowData(new SampleEvaluationTestFlowcHisPO());
	}

	@Override
	public void insertSubMainData() throws Exception {
		SampleEvaluationTestService secs = new SampleEvaluationTestService(t);
		SampleEvaluationTestPO seTestDto = (SampleEvaluationTestPO) this.se;
		seTestDto.setOwnPno(seTestDto.getOwnPno());
		// insert Test主檔
		secs.upsert(this.se);
		this.ownPno = seTestDto.getOwnPno();

	}

	public boolean isReady(Object form) {

		if (form instanceof hproc) {

			checkEmpty(((hproc) form).getValue("COORDINATOR").trim());
			checkEmpty(((hproc) form).getValue("IS_TEST").trim());

		}
		if (form instanceof bProcFlow) {

			checkEmpty(((bProcFlow) form).getValue("COORDINATOR").trim());
			checkEmpty(((bProcFlow) form).getValue("IS_TEST").trim());
		}
		if (al.indexOf(false) == -1) {
			return true;
		} else {
			return false;
		}

	}
}
