package oa.SampleEvaluation.jview;

import java.sql.SQLException;

/**
 * ��H��View
 * 
 * @author u52116
 *
 */
public interface Page {

	void pageInit() throws SQLException, Exception;

	void setBaseInfo() throws SQLException, Exception;

	String action(String arg0) throws Throwable;
}
