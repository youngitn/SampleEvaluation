package oa.SampleEvaluationCheck;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.CommonDataObj;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class Controller extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;

	@Override
	public String action(String arg0) throws Throwable {

		if (getState().trim().equals("��g����渹")) {
			setEditable("NOTIFY_NO_CHECK", true);
		}
		return arg0;

	}

}
