package oa.SampleEvaluation.subflowbuilder.builder;

import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluationTest.model.SampleEvaluationTestPO;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;

/**
 * The Class TestFlowBuilder.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class TestFlowBuilder extends SubFlowBuilder {
	
	/**
	 * Instantiates a new test flow builder.
	 */
	public TestFlowBuilder() {
		this.startGateName = "配合人員";
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder#setAndInsertFlowData()
	 */

	
	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder#insertSubMainData()
	 */
	@Override
	public void insertSubMainData() throws Exception {
		SampleEvaluationTestService secs = new SampleEvaluationTestService(t);
		SampleEvaluationTestPO seTestDto = (SampleEvaluationTestPO) this.se;
		seTestDto.setOwnPno(seTestDto.getOwnPno());
		// insert Test主檔
		secs.upsert(this.se);
		this.ownPno = seTestDto.getOwnPno();

	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder#isReady(java.lang.Object)
	 */
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
