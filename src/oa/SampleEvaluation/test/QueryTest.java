package oa.SampleEvaluation.test;

import jcx.jform.hproc;
import oa.SampleEvaluation.model.QueryConditionDTO;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.DtoUtil;

/**
 * The Class QueryTest.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class QueryTest extends hproc {

	/* (non-Javadoc)
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
//		Test tes = new Test();
//		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		// 表單輸入值讀取進入物件
		QueryConditionDTO targetLikeThis = new QueryConditionDTO();
		targetLikeThis = (QueryConditionDTO) DtoUtil.setFormDataIntoDto(new QueryConditionDTO(), this);

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
