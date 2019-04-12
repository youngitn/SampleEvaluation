package oa.global;

import java.sql.SQLException;

import jcx.db.talk;

/**
 * 用於填入表單的申請人基本資料欄位.
 * 
 * @author u52116
 *
 */
public class UserData {
	
	/** The empid. */
	private String cpnyid, hecname, dep_no, ext, dep_name, empid;

	/**
	 * Instantiates a new user data.
	 *
	 * @param empid [String]
	 * @param t [talk]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public UserData(String empid, talk t) throws SQLException, Exception {
		if (empid == null || empid.equals("")) {
			setCpnyid("查無此人");
			setHecname("查無此人");
			setDepNo("查無此人");
			setExt("查無此人");
			setDepName("查無此人");
			this.empid = "查無此人";
		} else {
			String sql = "select cpnyid, hecname, dep_no, ext, dep_name from user_info_view where empid = '" + empid
					+ "' ";
			String[][] ret = t.queryFromPool(sql);
			setCpnyid(ret[0][0]);
			setHecname(ret[0][1]);
			setDepNo(ret[0][2]);
			setExt(ret[0][3]);
			setDepName(ret[0][4]);
			this.empid = empid;
		}
	}

	/**
	 * Gets the Empid.
	 *
	 * @return [String]
	 */
	public String getEmpid() {
		return this.empid;
	}

	/**
	 * Gets the Cpnyid.
	 *
	 * @return the cpnyid
	 */
	public String getCpnyid() {
		return cpnyid;
	}

	/**
	 * Sets the Cpnyid.
	 *
	 * @param cpnyid the cpnyid to set
	 */
	public void setCpnyid(String cpnyid) {
		this.cpnyid = cpnyid;
	}

	/**
	 * Gets the Hecname.
	 *
	 * @return the hecname
	 */
	public String getHecname() {
		return hecname;
	}

	/**
	 * Sets the Hecname.
	 *
	 * @param hecname the hecname to set
	 */
	public void setHecname(String hecname) {
		this.hecname = hecname;
	}

	/**
	 * Gets the DepNo.
	 *
	 * @return the dep_no
	 */
	public String getDepNo() {
		return dep_no;
	}

	/**
	 * Sets the DepNo.
	 *
	 * @param dep_no the dep_no to set
	 */
	public void setDepNo(String dep_no) {
		this.dep_no = dep_no;
	}

	/**
	 * Gets the Ext.
	 *
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * Sets the Ext.
	 *
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * Gets the DepName.
	 *
	 * @return the dep_name
	 */
	public String getDepName() {
		return dep_name;
	}

	/**
	 * Sets the DepName.
	 *
	 * @param dep_name the dep_name to set
	 */
	public void setDepName(String dep_name) {
		this.dep_name = dep_name;
	}

}
