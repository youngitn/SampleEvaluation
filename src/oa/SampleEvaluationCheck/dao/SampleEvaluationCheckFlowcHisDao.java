package oa.SampleEvaluationCheck.dao;

import java.sql.*;
import java.util.*;

import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowcHis;

public class SampleEvaluationCheckFlowcHisDao {

	public SampleEvaluationCheckFlowcHis createValueObject() {
		return new SampleEvaluationCheckFlowcHis();
	}

	public SampleEvaluationCheckFlowcHis getObject(Connection conn, String ownPno, String F_INP_STAT, String F_INP_TIME)
			throws NotFoundException, SQLException {

		SampleEvaluationCheckFlowcHis valueObject = createValueObject();
		valueObject.setOwnPno(ownPno);
		valueObject.setF_INP_STAT(F_INP_STAT);
		valueObject.setF_INP_TIME(F_INP_TIME);
		load(conn, valueObject);
		return valueObject;
	}

	public void load(Connection conn, SampleEvaluationCheckFlowcHis valueObject)
			throws NotFoundException, SQLException {

		if (valueObject.getOwnPno() == null) {
			// System.out.println("Can not select without Primary-Key!");
			throw new NotFoundException("Can not select without Primary-Key!");
		}

		if (valueObject.getF_INP_STAT() == null) {
			// System.out.println("Can not select without Primary-Key!");
			throw new NotFoundException("Can not select without Primary-Key!");
		}

		if (valueObject.getF_INP_TIME() == null) {
			// System.out.println("Can not select without Primary-Key!");
			throw new NotFoundException("Can not select without Primary-Key!");
		}

		String sql = "SELECT * FROM SAMPLE_EVALUATION_CHECK_FLOWC_HIS WHERE (OWN_PNO = ? AND F_INP_STAT = ? AND F_INP_TIME = ? ) ";
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, valueObject.getOwnPno());
			stmt.setString(2, valueObject.getF_INP_STAT());
			stmt.setString(3, valueObject.getF_INP_TIME());

			singleQuery(conn, stmt, valueObject);

		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public List loadAll(Connection conn) throws SQLException {

		String sql = "SELECT * FROM SAMPLE_EVALUATION_CHECK_FLOWC_HIS ORDER BY F_INP_TIME ASC ";
		List searchResults = listQuery(conn, conn.prepareStatement(sql));

		return searchResults;
	}

	public synchronized void create(Connection conn, SampleEvaluationCheckFlowcHis valueObject) throws SQLException {

		String sql = "";
		PreparedStatement stmt = null;
		ResultSet result = null;

		try {
			sql = "INSERT INTO SAMPLE_EVALUATION_CHECK_FLOWC_HIS ( OWN_PNO, F_INP_STAT, F_INP_TIME, "
					+ "F_INP_ID, F_INP_INFO) VALUES (?, ?, ?, ?, ?) ";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, valueObject.getOwnPno());
			stmt.setString(2, valueObject.getF_INP_STAT());
			stmt.setString(3, valueObject.getF_INP_TIME());
			stmt.setString(4, valueObject.getF_INP_ID());
			stmt.setString(5, valueObject.getF_INP_INFO());

			int rowcount = databaseUpdate(conn, stmt);
			if (rowcount != 1) {
				// System.out.println("PrimaryKey Error when updating DB!");
				throw new SQLException("PrimaryKey Error when updating DB!");
			}

		} finally {
			if (stmt != null)
				stmt.close();
		}

	}

	public void save(Connection conn, SampleEvaluationCheckFlowcHis valueObject)
			throws NotFoundException, SQLException {

		String sql = "UPDATE SAMPLE_EVALUATION_CHECK_FLOWC_HIS SET F_INP_ID = ?, F_INP_INFO = ? WHERE (OWN_PNO = ? AND F_INP_STAT = ? AND F_INP_TIME = ? ) ";
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, valueObject.getF_INP_ID());
			stmt.setString(2, valueObject.getF_INP_INFO());

			stmt.setString(3, valueObject.getOwnPno());
			stmt.setString(4, valueObject.getF_INP_STAT());
			stmt.setString(5, valueObject.getF_INP_TIME());

			int rowcount = databaseUpdate(conn, stmt);
			if (rowcount == 0) {
				// System.out.println("Object could not be saved! (PrimaryKey not found)");
				throw new NotFoundException("Object could not be saved! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				// System.out.println("PrimaryKey Error when updating DB! (Many objects were
				// affected!)");
				throw new SQLException("PrimaryKey Error when updating DB! (Many objects were affected!)");
			}
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public void delete(Connection conn, SampleEvaluationCheckFlowcHis valueObject)
			throws NotFoundException, SQLException {

		if (valueObject.getOwnPno() == null) {
			// System.out.println("Can not delete without Primary-Key!");
			throw new NotFoundException("Can not delete without Primary-Key!");
		}

		if (valueObject.getF_INP_STAT() == null) {
			// System.out.println("Can not delete without Primary-Key!");
			throw new NotFoundException("Can not delete without Primary-Key!");
		}

		if (valueObject.getF_INP_TIME() == null) {
			// System.out.println("Can not delete without Primary-Key!");
			throw new NotFoundException("Can not delete without Primary-Key!");
		}

		String sql = "DELETE FROM SAMPLE_EVALUATION_CHECK_FLOWC_HIS WHERE (OWN_PNO = ? AND F_INP_STAT = ? AND F_INP_TIME = ? ) ";
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, valueObject.getOwnPno());
			stmt.setString(2, valueObject.getF_INP_STAT());
			stmt.setString(3, valueObject.getF_INP_TIME());

			int rowcount = databaseUpdate(conn, stmt);
			if (rowcount == 0) {
				// System.out.println("Object could not be deleted (PrimaryKey not found)");
				throw new NotFoundException("Object could not be deleted! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				// System.out.println("PrimaryKey Error when updating DB! (Many objects were
				// deleted!)");
				throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");
			}
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public void deleteAll(Connection conn) throws SQLException {

		String sql = "DELETE FROM SAMPLE_EVALUATION_CHECK_FLOWC_HIS";
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			int rowcount = databaseUpdate(conn, stmt);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public int countAll(Connection conn) throws SQLException {

		String sql = "SELECT count(*) FROM SAMPLE_EVALUATION_CHECK_FLOWC_HIS";
		PreparedStatement stmt = null;
		ResultSet result = null;
		int allRows = 0;

		try {
			stmt = conn.prepareStatement(sql);
			result = stmt.executeQuery();

			if (result.next())
				allRows = result.getInt(1);
		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
		}
		return allRows;
	}

	public List searchMatching(Connection conn, SampleEvaluationCheckFlowcHis valueObject) throws SQLException {

		List searchResults;

		boolean first = true;
		StringBuffer sql = new StringBuffer("SELECT * FROM SAMPLE_EVALUATION_CHECK_FLOWC_HIS WHERE 1=1 ");

		if (valueObject.getOwnPno() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND OWN_PNO LIKE '").append(valueObject.getOwnPno()).append("%' ");
		}

		if (valueObject.getF_INP_STAT() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND F_INP_STAT LIKE '").append(valueObject.getF_INP_STAT()).append("%' ");
		}

		if (valueObject.getF_INP_TIME() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND F_INP_TIME LIKE '").append(valueObject.getF_INP_TIME()).append("%' ");
		}

		if (valueObject.getF_INP_ID() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND F_INP_ID LIKE '").append(valueObject.getF_INP_ID()).append("%' ");
		}

		if (valueObject.getF_INP_INFO() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND F_INP_INFO LIKE '").append(valueObject.getF_INP_INFO()).append("%' ");
		}

		sql.append("ORDER BY F_INP_TIME ASC ");

		// Prevent accidential full table results.
		// Use loadAll if all rows must be returned.
		if (first)
			searchResults = new ArrayList();
		else
			searchResults = listQuery(conn, conn.prepareStatement(sql.toString()));

		return searchResults;
	}

	public String getDaogenVersion() {
		return "DaoGen version 2.4.1";
	}

	protected int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {

		int result = stmt.executeUpdate();

		return result;
	}

	protected void singleQuery(Connection conn, PreparedStatement stmt, SampleEvaluationCheckFlowcHis valueObject)
			throws NotFoundException, SQLException {

		ResultSet result = null;

		try {
			result = stmt.executeQuery();

			if (result.next()) {

				valueObject.setOwnPno(result.getString("OWN_PNO"));
				valueObject.setF_INP_STAT(result.getString("F_INP_STAT"));
				valueObject.setF_INP_TIME(result.getString("F_INP_TIME"));
				valueObject.setF_INP_ID(result.getString("F_INP_ID"));
				valueObject.setF_INP_INFO(result.getString("F_INP_INFO"));

			} else {
				// System.out.println("SampleEvaluationCheckFlowcHis Object Not Found!");
				throw new NotFoundException("SampleEvaluationCheckFlowcHis Object Not Found!");
			}
		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
		}
	}

	protected List listQuery(Connection conn, PreparedStatement stmt) throws SQLException {

		ArrayList searchResults = new ArrayList();
		ResultSet result = null;

		try {
			result = stmt.executeQuery();

			while (result.next()) {
				SampleEvaluationCheckFlowcHis temp = createValueObject();

				temp.setOwnPno(result.getString("OWN_PNO"));
				temp.setF_INP_STAT(result.getString("F_INP_STAT"));
				temp.setF_INP_TIME(result.getString("F_INP_TIME"));
				temp.setF_INP_ID(result.getString("F_INP_ID"));
				temp.setF_INP_INFO(result.getString("F_INP_INFO"));

				searchResults.add(temp);
			}

		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
		}

		return (List) searchResults;
	}

}
