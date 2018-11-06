package oa.SampleEvaluation.dto;

public interface ISampleEvaluationFlowcDto<E> {

	String getOwnPno();

	void setOwnPno(String ownPnoIn);

	String getF_INP_STAT();

	void setF_INP_STAT(String F_INP_STATIn);

	String getF_INP_ID();

	void setF_INP_ID(String F_INP_IDIn);

	String getF_INP_TIME();

	void setF_INP_TIME(String F_INP_TIMEIn);

	String getF_INP_INFO();

	void setF_INP_INFO(String F_INP_INFOIn);

	void setAll(String ownPnoIn, String F_INP_STATIn, String F_INP_IDIn, String F_INP_TIMEIn, String F_INP_INFOIn);

	boolean hasEqualMapping(E valueObject);

}