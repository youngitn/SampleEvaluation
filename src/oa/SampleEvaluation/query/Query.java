package oa.SampleEvaluation.query;

import java.sql.SQLException;

import jcx.db.talk;
import jcx.jform.hproc;
import oa.SampleEvaluation.common.global.DtoUtil;

/**
 * 查詢條件欄位
 * 
 * @author u52116
 *
 */
public class Query {
	;

	public Query() {

	}

	public String[][] get2DStringArrayResult(QueryConditionDto targetLikeThis, talk t) throws SQLException, Exception {
		QueryResultService qrs = new QueryResultService(t);
		return (String[][]) qrs.findByConditionReturn2DStringArray(getCondition(targetLikeThis));
	}

	public String getCondition(QueryConditionDto targetLikeThis) {
		String targetCondition = DtoUtil.getSelectConditionByDtoWithXmakerAdapDbFieldName(targetLikeThis);
		return targetCondition;
	}

}
