package oa.SampleEvaluation.test;

import org.junit.Test;

import jcx.db.talk;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TpFlowBuilder;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcHisPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;

/**
 * The Class TestSubFlow.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class TestSubFlow {

	/**
	 * Test.
	 *
	 * @param args [String[]]
	 */
	@Test
	public void test() {
		SubFlowBuilder sfb = new TpFlowBuilder();
		SampleEvaluationTpPO se = new SampleEvaluationTpPO();
		se.setPno("88888");
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");
		sfb.setMainDto(se);
		sfb.setTalk(t);
		try {
			Flowc flowc = new SampleEvaluationTpFlowcPO();
			Flowc flowcHis = new SampleEvaluationTpFlowcHisPO();
			sfb.construct(flowc, flowcHis);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
