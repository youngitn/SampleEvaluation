package oa.SampleEvaluation.query;

import java.sql.SQLException;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.global.DtoUtil;

/**
 * 查詢條件欄位
 * 
 * @author u52116
 *
 */
public class Query {
	QueryConditionDto targetLikeThis;
	hproc hpc;

	public Query(hproc hpc) {

		this.hpc = hpc;

		this.targetLikeThis = (QueryConditionDto) DtoUtil.setFormDataToDto(new QueryConditionDto(), hpc);

	}

	public String[][] get2DStringArrayResult() throws SQLException, Exception {
		QueryResultService qrs = new QueryResultService(hpc.getTalk());
		return (String[][]) qrs.findByConditionReturn2DStringArray(getCondition());
	}

	public String getCondition() {
		String targetCondition = DtoUtil.getSelectConditionByDtoWithXmakerAdapDbFieldName(this.targetLikeThis);
		return targetCondition;
	}

}
