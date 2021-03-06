package oa.SampleEvaluation.common;

import jcx.jform.bQuery;

public class ProjectLeaderQuery extends bQuery{
	public String getFilter()throws Throwable{
		// 回傳值為自定查詢條件
		// 回傳值必須是空白或以 and 開始,如 "and FIELD1='ABC'"
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

