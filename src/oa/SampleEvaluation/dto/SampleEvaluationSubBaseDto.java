package oa.SampleEvaluation.dto;

/**
 * SampleEvaluation �l�y�{base dto �~�Ӫ��覡���I��
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

	// ���l���O�갵 �]������check�Mtp�t�C
	protected abstract String buildOwnPno(String pno);

}