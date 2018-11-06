package oa.SampleEvaluationTp.dto;

import java.io.*;

import oa.SampleEvaluation.dto.AbstractGenericFlowcHisDto;

public class SampleEvaluationTpFlowcHis extends AbstractGenericFlowcHisDto<SampleEvaluationTpFlowcHis>
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

	public SampleEvaluationTpFlowcHis() {

	}

	public SampleEvaluationTpFlowcHis(String ownPnoIn, String F_INP_STATIn, String F_INP_TIMEIn) {

		this.ownPno = ownPnoIn;
		this.F_INP_STAT = F_INP_STATIn;
		this.F_INP_TIME = F_INP_TIMEIn;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#getOwnPno()
	 */
	@Override
	public String getOwnPno() {
		return this.ownPno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#setOwnPno(java.lang.
	 * String)
	 */
	@Override
	public void setOwnPno(String ownPnoIn) {
		this.ownPno = ownPnoIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#getF_INP_STAT()
	 */
	@Override
	public String getF_INP_STAT() {
		return this.F_INP_STAT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#setF_INP_STAT(java.
	 * lang.String)
	 */
	@Override
	public void setF_INP_STAT(String F_INP_STATIn) {
		this.F_INP_STAT = F_INP_STATIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#getF_INP_TIME()
	 */
	@Override
	public String getF_INP_TIME() {
		return this.F_INP_TIME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#setF_INP_TIME(java.
	 * lang.String)
	 */
	@Override
	public void setF_INP_TIME(String F_INP_TIMEIn) {
		this.F_INP_TIME = F_INP_TIMEIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#getF_INP_ID()
	 */
	@Override
	public String getF_INP_ID() {
		return this.F_INP_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#setF_INP_ID(java.lang.
	 * String)
	 */
	@Override
	public void setF_INP_ID(String F_INP_IDIn) {
		this.F_INP_ID = F_INP_IDIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#getF_INP_INFO()
	 */
	@Override
	public String getF_INP_INFO() {
		return this.F_INP_INFO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#setF_INP_INFO(java.
	 * lang.String)
	 */
	@Override
	public void setF_INP_INFO(String F_INP_INFOIn) {
		this.F_INP_INFO = F_INP_INFOIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oa.SampleEvaluationTp.dto.ISampleEvaluationFlowcHisDto#setAll(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
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
	public boolean hasEqualMapping(SampleEvaluationTpFlowcHis valueObject) {

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
