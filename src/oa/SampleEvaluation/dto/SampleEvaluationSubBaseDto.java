package oa.SampleEvaluation.dto;

/**
 * SampleEvaluation 子流程base dto 繼承的方式有點怪
 */
public abstract class SampleEvaluationSubBaseDto extends SampleEvaluation {
	private static final long serialVersionUID = 42L;

	/**
	 * ownPno
	 */
	protected String ownPno;

	public SampleEvaluationSubBaseDto() {

	}

	public String getOwnPno() {
		return ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

	// 讓子類別實做 因為有分check和tp系列
	protected abstract String buildOwnPno(String pno);

}