package oa.SampleEvaluationCheck;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.UserData;

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
		//�D�ޤ��׫����H�m�W ������
		UserData u = new UserData(getValue("PROJECT_LEADER").trim(),getTalk());	
		setValue("PROJECT_LEADER_NAME",u.getHecname()+" "+u.getDep_name());
		
		//�ӽФH�򥻸��
		u = new UserData(getValue("APPLICANT").trim(),getTalk());
		setValue("APPLICANT_NAME",u.getHecname());
		setValue("CPNYID",u.getCpnyid());
		setValue("APPLICANT_DEP_NAME",u.getDep_name());
		
		if (getState().trim().equals("��g����渹")) {
			setEditable("NOTIFY_NO_CHECK", true);
			
		}
		return arg0;

	}

}
