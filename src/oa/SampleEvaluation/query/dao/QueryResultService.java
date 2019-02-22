package oa.SampleEvaluation.query.dao;

import jcx.db.talk;
import oa.SampleEvaluation.query.dto.QueryResultDto;
import oa.global.BaseDao;

public class QueryResultService extends BaseDao {
	public QueryResultService(talk t) {
		this.clazz = QueryResultDto.class;
		this.t = t;
	}
}
