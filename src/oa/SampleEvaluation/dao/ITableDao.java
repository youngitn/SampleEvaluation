package oa.SampleEvaluation.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ITableDao<E> {

	String add(E sampleEvaluation) throws SQLException, Exception;

	String update(E sampleEvaluation) throws SQLException, Exception;

	String delete(String id) throws SQLException, Exception;

	E findById(String id) throws SQLException, Exception;

	ArrayList<E> findAllList(String params) throws SQLException, Exception;

	String[][] findArrayById(String id) throws SQLException, Exception;

	String[][] findAllArray(String params, String selectFields) throws SQLException, Exception;

}
