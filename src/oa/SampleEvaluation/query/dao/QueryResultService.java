package oa.SampleEvaluation.query.dao;

import jcx.db.talk;
import oa.SampleEvaluation.query.dto.QueryResultDto;
import oa.global.BaseDao;
/**
 * 主要是進行查詢結果的依賴注入
 * @author u52116
 *
 */
public class QueryResultService extends BaseDao {
	public QueryResultService(talk t) {
		this.clazz = QueryResultDto.class;
		this.t = t;
	}
}
