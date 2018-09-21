package oa.SampleEvaluationCheck.object;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;

public class SampleEvaluationCheckFlowc implements Cloneable, Serializable {


	private String ownPno;
	private String F_INP_STAT;
	private String F_INP_ID;
	private String F_INP_TIME;
	private String F_INP_INFO;

	
	public SampleEvaluationCheckFlowc() {

	}

	public SampleEvaluationCheckFlowc(String ownPnoIn) {

		this.ownPno = ownPnoIn;

	}

	

	public String getOwnPno() {
		return this.ownPno;
	}

	public void setOwnPno(String ownPnoIn) {
		this.ownPno = ownPnoIn;
	}

	public String getF_INP_STAT() {
		return this.F_INP_STAT;
	}

	public void setF_INP_STAT(String F_INP_STATIn) {
		this.F_INP_STAT = F_INP_STATIn;
	}

	public String getF_INP_ID() {
		return this.F_INP_ID;
	}

	public void setF_INP_ID(String F_INP_IDIn) {
		this.F_INP_ID = F_INP_IDIn;
	}

	public String getF_INP_TIME() {
		return this.F_INP_TIME;
	}

	public void setF_INP_TIME(String F_INP_TIMEIn) {
		this.F_INP_TIME = F_INP_TIMEIn;
	}

	public String getF_INP_INFO() {
		return this.F_INP_INFO;
	}

	public void setF_INP_INFO(String F_INP_INFOIn) {
		this.F_INP_INFO = F_INP_INFOIn;
	}


	public void setAll(String ownPnoIn, String F_INP_STATIn, String F_INP_IDIn, String F_INP_TIMEIn, String F_INP_INFOIn) {
		this.ownPno = ownPnoIn;
		this.F_INP_STAT = F_INP_STATIn;
		this.F_INP_ID = F_INP_IDIn;
		this.F_INP_TIME = F_INP_TIMEIn;
		this.F_INP_INFO = F_INP_INFOIn;
	}

	
	public boolean hasEqualMapping(SampleEvaluationCheckFlowc valueObject) {

		if (valueObject.getOwnPno() != this.ownPno) {
			return (false);
		}
		if (this.F_INP_STAT == null) {
			if (valueObject.getF_INP_STAT() != null)
				return (false);
		} else if (!this.F_INP_STAT.equals(valueObject.getF_INP_STAT())) {
			return (false);
		}
		if (this.F_INP_ID == null) {
			if (valueObject.getF_INP_ID() != null)
				return (false);
		} else if (!this.F_INP_ID.equals(valueObject.getF_INP_ID())) {
			return (false);
		}
		if (this.F_INP_TIME == null) {
			if (valueObject.getF_INP_TIME() != null)
				return (false);
		} else if (!this.F_INP_TIME.equals(valueObject.getF_INP_TIME())) {
			return (false);
		}
		if (this.F_INP_INFO == null) {
			if (valueObject.getF_INP_INFO() != null)
				return (false);
		} else if (!this.F_INP_INFO.equals(valueObject.getF_INP_INFO())) {
			return (false);
		}

		return true;
	}


	public String toString() {
		StringBuffer out = new StringBuffer(this.getDaogenVersion());
		out.append("\nclass SampleEvaluationCheckFlowc, mapping to table SAMPLE_EVALUATION_CHECK_FLOWC\n");
		out.append("Persistent attributes: \n");
		out.append("ownPno = " + this.ownPno + "\n");
		out.append("F_INP_STAT = " + this.F_INP_STAT + "\n");
		out.append("F_INP_ID = " + this.F_INP_ID + "\n");
		out.append("F_INP_TIME = " + this.F_INP_TIME + "\n");
		out.append("F_INP_INFO = " + this.F_INP_INFO + "\n");
		return out.toString();
	}

	
	public Object clone() {
		SampleEvaluationCheckFlowc cloned = new SampleEvaluationCheckFlowc();

		cloned.setOwnPno(this.ownPno);
		if (this.F_INP_STAT != null)
			cloned.setF_INP_STAT(new String(this.F_INP_STAT));
		if (this.F_INP_ID != null)
			cloned.setF_INP_ID(new String(this.F_INP_ID));
		if (this.F_INP_TIME != null)
			cloned.setF_INP_TIME(new String(this.F_INP_TIME));
		if (this.F_INP_INFO != null)
			cloned.setF_INP_INFO(new String(this.F_INP_INFO));
		return cloned;
	}


	public String getDaogenVersion() {
		return "DaoGen version 2.4.1";
	}

}
