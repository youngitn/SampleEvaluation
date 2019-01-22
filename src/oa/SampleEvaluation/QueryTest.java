package oa.SampleEvaluation;

import java.util.ArrayList;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.query.QueryConditionDto;
import oa.SampleEvaluation.query.QueryResultService;

public class QueryTest extends hproc {

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
//		Test tes = new Test();
//		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		// 表單輸入值讀取進入物件
		QueryConditionDto targetLikeThis = new QueryConditionDto();
		targetLikeThis = (QueryConditionDto) DtoUtil.setFormDataToDto(new QueryConditionDto(), this);
		// 用條件執行查詢
		// s.doQuery();
		// 或取得SQL查詢字串
//		String targetCondition = targetLikeThis.getSqlSelectStr();
//		//String[][] step1ret = getTalk().queryFromPool(targetCondition);
//		//QueryService qs = new QueryService(getTalk());
//		//這邊要拿回來的list 應該是結果
//		QueryResultService qrs = new QueryResultService(getTalk());
//		ArrayList al = qrs.findByCondition(targetCondition);
		return null;
	}

}
