package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ysp.util.DateTimeUtil;

import jcx.db.talk;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluation.model.SampleEvaluationPO;

/**
 * The Class SubFlowBuilder.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public abstract class SubFlowBuilder {

	/** The se. */
	protected SampleEvaluationPO se;
	
	/** The t. */
	protected talk t;
	
	/** The start gate name. */
	protected String startGateName;
	
	/** The al. */
	protected ArrayList<Boolean> al = new ArrayList<Boolean>();
	
	/** The own pno. */
	protected String ownPno;
	
	/** The designee. */
	protected String designee;
	
	/** The now. */
	protected String now;
	
	/**
	 * Insert sub main data.
	 *
	 * @throws Exception the exception
	 */
	public abstract void insertSubMainData() throws Exception;

	/**
	 * Sets the MainDto.
	 *
	 * @param se  void
	 */
	public void setMainDto(SampleEvaluationPO se) {
		this.se = se;
	}

	/**
	 * Sets the and insert flow data.
	 *
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public abstract void setAndInsertFlowData() throws SQLException, Exception;

	/**
	 * Sets the Talk.
	 *
	 * @param t  void
	 */
	public void setTalk(talk t) {
		this.t = t;

	}

	/**
	 * Construct.
	 *
	 * @throws Exception the exception
	 */
	public void construct() throws Exception {
		this.insertSubMainData();
		this.setAndInsertFlowData();

	}

	/**
	 * Insert flow data.
	 *
	 * @param c [Flowc]
	 * @return  [Flowc]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
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

	/**
	 * Checks if is ready.
	 *
	 * @param form [Object]
	 * @return true, if is ready
	 */
	public abstract boolean isReady(Object form);

	/**
	 * Check empty.
	 *
	 * @param s [String]
	 */
	public void checkEmpty(String s) {
		if (s == null || s.trim().length() == 0) {
			al.add(false);
		} else {
			al.add(true);
		}
	}
	
	
}
