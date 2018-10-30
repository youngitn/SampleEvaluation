package oa.SampleEvaluationTp.dto;

import java.io.Serializable;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;

/**
 * SampleEvaluation
 * 
 */
public class SampleEvaluationTp extends SampleEvaluationSubBaseDto implements Serializable {
	private static final long serialVersionUID = 42L;

	public SampleEvaluationTp(String[] strings) {
		super(strings);
		this.ownPno = strings[28];
	}

	public SampleEvaluationTp() {

	}

	public SampleEvaluationTp(BaseService service) {
		super(service);
	}

	@Override
	protected String buildOwnPno(String pno) {

		return pno + "TP";
	}

}