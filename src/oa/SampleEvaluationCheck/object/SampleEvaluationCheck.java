package oa.SampleEvaluationCheck.object;

import java.io.Serializable;

import oa.SampleEvaluation.common.SampleEvaluation;

/**
*  SampleEvaluation
* 
*/
public class SampleEvaluationCheck extends SampleEvaluation implements Serializable {
    private static final long serialVersionUID = 42L;

    /**
    * ownPno
    */
    private String ownPno;


    public SampleEvaluationCheck(String[] strings) {
		super( strings);
		this.ownPno = strings[28];
	}
    public SampleEvaluationCheck() {
		
	}

	

	public String getOwnPno() {
        return ownPno;
    }

    public void setOwnPno(String ownPno) {
        this.ownPno = ownPno;
    }

    

}