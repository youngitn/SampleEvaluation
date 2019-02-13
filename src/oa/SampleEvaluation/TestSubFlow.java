package oa.SampleEvaluation;

import jcx.db.talk;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.subflowbuilder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.TpFlowBuilder;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class TestSubFlow {

	public static void main(String[] args) {
		SubFlowBuilder sfb = new TpFlowBuilder();
		SampleEvaluationTp se = new SampleEvaluationTp();
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
