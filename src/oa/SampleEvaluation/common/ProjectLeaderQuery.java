package oa.SampleEvaluation.common;

import jcx.jform.bQuery;

public class ProjectLeaderQuery extends bQuery{
	public String getFilter()throws Throwable{
		// �^�ǭȬ��۩w�d�߱���
		// �^�ǭȥ����O�ťթΥH and �}�l,�p "and FIELD1='ABC'"
		String depNo = getValue("RECEIPT_UNIT");
		if ("80".equals(depNo.trim())){
			return "AND dep_no in ('80','91')";
			}else{
				return "AND dep_no in ('"+depNo+"')";
			}
	}
	public String getInformation(){
		return "---------------null(null).setf_defined_where_clause()----------------";
	}
}

