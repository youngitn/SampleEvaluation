package oa.SampleEvaluation;

import jcx.db.talk;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluation.subflowbuilder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.TestFlowBuilder;
import oa.SampleEvaluationTest.dto.SampleEvaluationTest;

public class TestSubFlow {

	public static void main(String[] args) {
		SubFlowBuilder sfb = new TestFlowBuilder();
		SampleEvaluationSubBaseDto se = new SampleEvaluationTest();
		se.setPno("999999");
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
