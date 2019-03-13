package oa.SampleEvaluation.test;

import jcx.db.talk;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TpFlowBuilder;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;

public class TestSubFlow {

	public void test(String[] args) {
		SubFlowBuilder sfb = new TpFlowBuilder();
		SampleEvaluationTpPO se = new SampleEvaluationTpPO();
		se.setPno("88888");
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");
		sfb.setMainDto(se);
		sfb.setTalk(t);
		try {
			sfb.construct();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
