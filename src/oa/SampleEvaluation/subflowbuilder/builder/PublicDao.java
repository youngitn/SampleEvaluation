package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import jcx.db.talk;
import oa.global.BaseDao;

public class PublicDao extends BaseDao {
	/**
	 * 
	 * @param PO [���֦���������ƪ�&XMAKER�ŧi]
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public PublicDao setServiceClass(final Object PO) throws SQLException, Exception {
		this.clazz = PO.getClass();
		return this;
	}

	public PublicDao setTalk(talk t) {
		this.t = t;
		return this;
	};
}
