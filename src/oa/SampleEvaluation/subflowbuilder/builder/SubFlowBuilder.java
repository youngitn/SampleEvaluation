package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ysp.util.DateTimeUtil;

import jcx.db.talk;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluation.model.SampleEvaluationPO;

public abstract class SubFlowBuilder {

	protected SampleEvaluationPO se;
	protected talk t;
	protected String startGateName;
	protected ArrayList<Boolean> al = new ArrayList<Boolean>();
	protected String ownPno;
	protected String designee;
	protected String now;
	
	public abstract void insertSubMainData() throws Exception;

	public void setMainDto(SampleEvaluationPO se) {
		this.se = se;
	}

	public abstract void setAndInsertFlowData() throws SQLException, Exception;

	public void setTalk(talk t) {
		this.t = t;

	}

	public void construct() throws Exception {
		this.insertSubMainData();
		this.setAndInsertFlowData();

	}

	public Flowc insertFlowData(Flowc c) throws SQLException, Exception {
		this.now = DateTimeUtil.getApproveAddSeconds(0);
		c.setOwnPno(this.ownPno);
		c.setfInpId(this.se.getDesignee().split(" ")[0]);//
		c.setfInpStat(this.startGateName);
		c.setfInpTime(this.now);
		PublicDao service = new PublicDao();
		service.setTalk(this.t).setServiceClass(c);
		service.upsert(c);
		return c;
	}

	public abstract boolean isReady(Object form);

	public void checkEmpty(String s) {
		if (s == null || s.trim().length() == 0) {
			al.add(false);
		} else {
			al.add(true);
		}
	}
}
