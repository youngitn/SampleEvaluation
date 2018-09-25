package oa.SampleEvaluationCheck;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.CommonDataObj;

/**
 * 嘗試可測試寫法
 * 
 * @author u52116
 *
 */
public class Controller extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;

	@Override
	public String action(String arg0) throws Throwable {

		if (getState().trim().equals("填寫請驗單號")) {
			setEditable("NOTIFY_NO_CHECK", true);
		}
		return arg0;

	}

}
