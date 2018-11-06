package oa.SampleEvaluation.dto;

import java.io.Serializable;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.dto.SampleEvaluation;

/**
 * SampleEvaluation 子流程base dto
 * 繼承的方式有點怪
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
		//用父類別方法塞共同欄位資料
		super.setAllValue(service);
		// 子流程多一個ownPno欄位  ID = 表單單號+TP
		String ownPno = buildOwnPno(service.getValue("PNO"));
		// 為子流程主檔填入ID
		this.setOwnPno(ownPno);

	}

	//讓子類別實做 因為有分check和tp系列
	protected abstract String buildOwnPno(String pno);

}