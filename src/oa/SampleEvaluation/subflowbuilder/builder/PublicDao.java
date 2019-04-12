package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import jcx.db.talk;
import oa.global.BaseDao;

/**
 * The Class PublicDao.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class PublicDao extends BaseDao {
	
	/**
	 * Sets the service class.
	 *
	 * @param PO [須擁有對應的資料表&XMAKER宣告]
	 * @return  [PublicDao]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public PublicDao setServiceClass(final Object PO) throws SQLException, Exception {
		this.clazz = PO.getClass();
		return this;
	}

	/**
	 * Sets the talk.
	 *
	 * @param t [talk]
	 * @return  [PublicDao]
	 */
	public PublicDao setTalk(talk t) {
		this.t = t;
		return this;
	};
}
