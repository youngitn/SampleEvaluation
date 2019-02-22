package oa.global;

import java.sql.SQLException;

import jcx.db.talk;

public class UserData {
	private String cpnyid, hecname, dep_no, ext, dep_name, empid;

	public UserData(String empid, talk t) throws SQLException, Exception {
		if (empid == null || empid.equals("")) {
			setCpnyid("촥킠┨짩");
			setHecname("촥킠┨짩");
			setDepNo("촥킠┨짩");
			setExt("촥킠┨짩");
			setDepName("촥킠┨짩");
			this.empid = "촥킠┨짩";
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

	public String getEmpid() {
		return this.empid;
	}

//	public static UserData getInstance(String empid,talk t) throws SQLException, Exception {
//		if (instance == null) {
//			synchronized (UserData.class) {
//				if (instance == null) {
//					instance = new UserData(empid,t);
//				}
//			}
//		}
//		return instance;
//	}

	/**
	 * @return the cpnyid
	 */
	public String getCpnyid() {
		return cpnyid;
	}

	/**
	 * @param cpnyid the cpnyid to set
	 */
	public void setCpnyid(String cpnyid) {
		this.cpnyid = cpnyid;
	}

	/**
	 * @return the hecname
	 */
	public String getHecname() {
		return hecname;
	}

	/**
	 * @param hecname the hecname to set
	 */
	public void setHecname(String hecname) {
		this.hecname = hecname;
	}

	/**
	 * @return the dep_no
	 */
	public String getDepNo() {
		return dep_no;
	}

	/**
	 * @param dep_no the dep_no to set
	 */
	public void setDepNo(String dep_no) {
		this.dep_no = dep_no;
	}

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * @return the dep_name
	 */
	public String getDepName() {
		return dep_name;
	}

	/**
	 * @param dep_name the dep_name to set
	 */
	public void setDepName(String dep_name) {
		this.dep_name = dep_name;
	}

}
