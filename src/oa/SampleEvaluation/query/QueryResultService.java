package oa.SampleEvaluation.query;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;

public class QueryResultService extends BaseDao {
	public QueryResultService(talk t) {
		this.clazz = QueryResultDto.class;
		this.t = t;
	}
}
