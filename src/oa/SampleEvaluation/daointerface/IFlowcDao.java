package oa.SampleEvaluation.daointerface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import oa.SampleEvaluation.exception.NotFoundException;



public interface IFlowcDao<E> {

	E createValueObject();

	E getObject(Connection conn, String ownPno) throws NotFoundException, SQLException;

	void load(Connection conn, E valueObject) throws NotFoundException, SQLException;

	List loadAll(Connection conn) throws SQLException;

	void create(Connection conn, E valueObject) throws SQLException;

	void save(Connection conn, E valueObject) throws NotFoundException, SQLException;

	void delete(Connection conn, E valueObject) throws NotFoundException, SQLException;

	void deleteAll(Connection conn) throws SQLException;

	int countAll(Connection conn) throws SQLException;

	List searchMatching(Connection conn, E valueObject) throws SQLException;

}