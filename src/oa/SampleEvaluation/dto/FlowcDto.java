package oa.SampleEvaluation.dto;

public class FlowcDto {

	protected String id;
	protected String F_INP_STAT;
	protected String F_INP_ID;
	protected String F_INP_TIME;
	protected String F_INP_INFO;

	public FlowcDto() {

	}

	public FlowcDto(String id) {

		this.id = id;

	}

	public String getId() {
		return this.id;
	}

	public void setId(String IdIn) {
		this.id = IdIn;
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

	public void setAll(String IdIn, String F_INP_STATIn, String F_INP_IDIn, String F_INP_TIMEIn, String F_INP_INFOIn) {
		this.id = IdIn;
		this.F_INP_STAT = F_INP_STATIn;
		this.F_INP_ID = F_INP_IDIn;
		this.F_INP_TIME = F_INP_TIMEIn;
		this.F_INP_INFO = F_INP_INFOIn;
	}

}
