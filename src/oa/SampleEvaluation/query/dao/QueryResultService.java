package oa.SampleEvaluation.query.dao;

import jcx.db.talk;
import oa.SampleEvaluation.query.dto.QueryResultDto;
import oa.global.BaseDao;
/**
 * �D�n�O�i��d�ߵ��G���̿�`�J
 * @author u52116
 *
 */
public class QueryResultService extends BaseDao {
	public QueryResultService(talk t) {
		this.clazz = QueryResultDto.class;
		this.t = t;
	}
}
