package oa.SampleEvaluation.dto;

import java.io.Serializable;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.dto.SampleEvaluation;

/**
 * SampleEvaluation �l�y�{base dto
 * �~�Ӫ��覡���I��
 */
public abstract class SampleEvaluationSubBaseDto extends SampleEvaluation implements Serializable {
	private static final long serialVersionUID = 42L;

	/**
	 * ownPno
	 */
	protected String ownPno;

	public SampleEvaluationSubBaseDto(String[] strings) {
		super(strings);
		this.ownPno = strings[28];
	}

	public SampleEvaluationSubBaseDto() {

	}

	public SampleEvaluationSubBaseDto(BaseService service) {
		setAllValue(service);
	}

	public String getOwnPno() {
		return ownPno;
	}

	public void setOwnPno(String ownPno) {
		this.ownPno = ownPno;
	}

	public void setAllValue(BaseService service) {
		//�Τ����O��k��@�P�����
		super.setAllValue(service);
		// �l�y�{�h�@��ownPno���  ID = ���渹+TP
		String ownPno = buildOwnPno(service.getValue("PNO"));
		// ���l�y�{�D�ɶ�JID
		this.setOwnPno(ownPno);

	}

	//���l���O�갵 �]������check�Mtp�t�C
	protected abstract String buildOwnPno(String pno);

}