package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import jcx.db.talk;
import oa.global.BaseDao;

public class PublicDao extends BaseDao {
	public PublicDao setServiceClass(final Object o) throws SQLException, Exception {
		this.clazz = o.getClass();
		return this;
	}

	public PublicDao setTalk(talk t) {
		this.t = t;
		return this;
	};
}
