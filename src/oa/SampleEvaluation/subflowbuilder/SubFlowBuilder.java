package oa.SampleEvaluation.subflowbuilder;

import jcx.db.talk;
import oa.SampleEvaluation.common.Flowc;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;

public abstract class SubFlowBuilder {

	protected SampleEvaluationSubBaseDto se;
	protected talk t;
	protected String gateName;

	public abstract void setOwnPno();

	public void setStartGateName(String gateName) {
		this.gateName = gateName;
	}

	public abstract void insertSubMainData() throws Exception;

	public void setMainDto(SampleEvaluationSubBaseDto se) {
		this.se = se;
	}

	public abstract void setAndInsertFlowData();

	public void setTalk(talk t) {
		this.t = t;

	}

	public void construct() throws Exception {

		this.setOwnPno();
		this.insertSubMainData();
		this.setAndInsertFlowData();

	}

	public Flowc setFlowData(Flowc c, String State, String time) {

		c.setOwnPno(this.se.getOwnPno());
		c.setfInpId(this.se.getApplicant());
		c.setfInpStat(State);
		c.setfInpTime(time);
		return c;
	}
}
