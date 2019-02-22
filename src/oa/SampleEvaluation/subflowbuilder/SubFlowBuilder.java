package oa.SampleEvaluation.subflowbuilder;

import jcx.db.talk;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.i.Flowc;

public abstract class SubFlowBuilder {

	protected SampleEvaluation se;
	protected talk t;
	protected String gateName;
	protected String ownPnoType;

	public abstract void setStartGateName();

	public abstract void insertSubMainData() throws Exception;

	public void setMainDto(SampleEvaluation se) {
		this.se = se;
	}

	public abstract void setAndInsertFlowData();

	public void setTalk(talk t) {
		this.t = t;

	}

	public void construct() throws Exception {
		this.setStartGateName();
		this.setSubFlowOwnPnoType();
		this.insertSubMainData();
		this.setAndInsertFlowData();

	}

	public abstract void setSubFlowOwnPnoType();

	public Flowc setFlowData(Flowc c, String State, String time) {
		c.setOwnPno(this.se.getPno() + this.ownPnoType);
		c.setfInpId(this.se.getApplicant());
		c.setfInpStat(State);
		c.setfInpTime(time);
		return c;
	}
}
