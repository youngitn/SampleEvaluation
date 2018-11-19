package oa.SampleEvaluation.daointerface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import oa.SampleEvaluation.exception.NotFoundException;
/**
 * 重覆蠻多ISampleEvaluationSubFlowcDao的code.
 * TODO 要讓flow和flowHis整合
 * @author u52116
 *
 * @param <E>
 */
public interface IFlowcHisDao<E> {

	E createValueObject();

	E getObject(Connection conn, String ownPno, String F_INP_STAT, String F_INP_TIME)
			throws NotFoundException, SQLException;

	void load(Connection conn, E valueObject) throws NotFoundException, SQLException;

	List loadAll(Connection conn) throws SQLException;

	void create(Connection conn, E valueObject) throws SQLException;

	void save(Connection conn, E valueObject) throws NotFoundException, SQLException;

	void delete(Connection conn, E valueObject) throws NotFoundException, SQLException;

	void deleteAll(Connection conn) throws SQLException;

	int countAll(Connection conn) throws SQLException;

	List searchMatching(Connection conn, E valueObject) throws SQLException;

	List listQuery(Connection conn, PreparedStatement stmt) throws SQLException;

	void singleQuery(Connection conn, PreparedStatement stmt, E valueObject) throws NotFoundException, SQLException;

}