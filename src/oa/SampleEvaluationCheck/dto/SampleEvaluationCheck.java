package oa.SampleEvaluationCheck.dto;

import java.io.Serializable;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;

/**
 * SampleEvaluation
 * 
 */
public class SampleEvaluationCheck extends SampleEvaluationSubBaseDto implements Serializable {
	private static final long serialVersionUID = 42L;

	public SampleEvaluationCheck(String[] strings) {
		super(strings);
		this.ownPno = strings[28];
	}

	public SampleEvaluationCheck() {

	}

	public SampleEvaluationCheck(BaseService service) {
		super(service);
	}

	@Override
	protected String buildOwnPno(String pno) {

		return pno + "CHECK";
	}

}