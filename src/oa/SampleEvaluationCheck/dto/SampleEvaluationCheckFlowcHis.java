package oa.SampleEvaluationCheck.dto;

import java.io.*;

import oa.SampleEvaluation.dto.AbstractGenericFlowcHisDto;

public class SampleEvaluationCheckFlowcHis extends AbstractGenericFlowcHisDto<SampleEvaluationCheckFlowcHis>
		implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ownPno;
	private String F_INP_STAT;
	private String F_INP_TIME;
	private String F_INP_ID;
	private String F_INP_INFO;

	public SampleEvaluationCheckFlowcHis() {

	}

	public SampleEvaluationCheckFlowcHis(String ownPnoIn, String F_INP_STATIn, String F_INP_TIMEIn) {

		this.ownPno = ownPnoIn;
		this.F_INP_STAT = F_INP_STATIn;
		this.F_INP_TIME = F_INP_TIMEIn;

	}

	@Override
	public String getOwnPno() {
		return this.ownPno;
	}

	@Override
	public void setOwnPno(String ownPnoIn) {
		this.ownPno = ownPnoIn;
	}

	@Override
	public String getF_INP_STAT() {
		return this.F_INP_STAT;
	}

	@Override
	public void setF_INP_STAT(String F_INP_STATIn) {
		this.F_INP_STAT = F_INP_STATIn;
	}

	@Override
	public String getF_INP_TIME() {
		return this.F_INP_TIME;
	}

	@Override
	public void setF_INP_TIME(String F_INP_TIMEIn) {
		this.F_INP_TIME = F_INP_TIMEIn;
	}

	@Override
	public String getF_INP_ID() {
		return this.F_INP_ID;
	}

	@Override
	public void setF_INP_ID(String F_INP_IDIn) {
		this.F_INP_ID = F_INP_IDIn;
	}

	@Override
	public String getF_INP_INFO() {
		return this.F_INP_INFO;
	}

	@Override
	public void setF_INP_INFO(String F_INP_INFOIn) {
		this.F_INP_INFO = F_INP_INFOIn;
	}

	@Override
	public void setAll(String ownPnoIn, String F_INP_STATIn, String F_INP_TIMEIn, String F_INP_IDIn,
			String F_INP_INFOIn) {
		this.ownPno = ownPnoIn;
		this.F_INP_STAT = F_INP_STATIn;
		this.F_INP_TIME = F_INP_TIMEIn;
		this.F_INP_ID = F_INP_IDIn;
		this.F_INP_INFO = F_INP_INFOIn;
	}

	@Override
	public boolean hasEqualMapping(SampleEvaluationCheckFlowcHis valueObject) {

		if (this.ownPno == null) {
			if (valueObject.getOwnPno() != null)
				return (false);
		} else if (!this.ownPno.equals(valueObject.getOwnPno())) {
			return (false);
		}
		if (this.F_INP_STAT == null) {
			if (valueObject.getF_INP_STAT() != null)
				return (false);
		} else if (!this.F_INP_STAT.equals(valueObject.getF_INP_STAT())) {
			return (false);
		}
		if (this.F_INP_TIME == null) {
			if (valueObject.getF_INP_TIME() != null)
				return (false);
		} else if (!this.F_INP_TIME.equals(valueObject.getF_INP_TIME())) {
			return (false);
		}
		if (this.F_INP_ID == null) {
			if (valueObject.getF_INP_ID() != null)
				return (false);
		} else if (!this.F_INP_ID.equals(valueObject.getF_INP_ID())) {
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

	@Override
	public Class getClazz() {
		// TODO Auto-generated method stub
		return null;
	}

}
