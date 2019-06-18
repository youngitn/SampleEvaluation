package oa.SampleEvaluation.common;

import java.sql.SQLException;

import jcx.jform.hproc;
import jcx.util.datetime;
import oa.global.UserData;

/**
 * The Class FormInitUtil.
 *
 * @author u52116
 */
public class FormInitUtil {

	/** The userdata. */
	UserData userdata;
	
	/** The c. */
	// form
	hproc c;

	/**
	 * Instantiates a new form init util.
	 *
	 * @param c [hproc]
	 * @throws NullPointerException the null pointer exception
	 */
	public FormInitUtil(hproc c) throws NullPointerException {

		try {
			this.c = c;
			userdata = new UserData(c.getUser(), c.getTalk());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Do query page process.
	 *
	 * @throws Exception the exception
	 */
	public void doQueryPageProcess() throws Exception {
		userdata = getNowApplicant();
		// ���ouser������O
		// ��Jquery�e���������
		c.setValue("Q_EMPID", userdata.getEmpid());
		c.setValue("Q_EMP_NAME", userdata.getHecname());
		c.setValue("Q_DEP_NO", userdata.getDepNo());
		c.setValue("Q_DEP_NAME", userdata.getDepName());
		String today = c.getToday("YYYYmmdd");
		String edate = today;
		c.setValue("Q_EDATE", edate);
		userdata = null;
		c.setNewView("QueryPage");
	}

	/**
	 * Do add page process.
	 *
	 * @throws Exception the exception
	 */
	public void doAddPageProcess() throws Exception {
		userdata = getNowApplicant();
		// �s�W���ӽФH�򥻸�ƶ�J
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT", c.getUser());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDepName());
		c.setValue("APP_DATE", c.getToday("YYYYmmdd"));
		userdata = null;
	}

	/**
	 * ���ӽФH != �ثe�ϥΪ� <br>
	 * ���� �ݳB�z �d��(�]�w�w�]��)��.
	 *
	 * @return [UserData]
	 * @throws Exception the exception
	 */
	public UserData getBillApplicant() throws Exception {
		String empid = c.getValue("APPLICANT");
		if ("".equals(empid)) {
			empid = c.getUser();
		}
		return new UserData(empid, c.getTalk());

	}

	/**
	 * ���ӽФH = �ثe�ϥΪ� <br>
	 * �_���<br>.
	 *
	 * @return [UserData]
	 * @throws Exception the exception
	 */
	public UserData getNowApplicant() throws Exception {
		return new UserData(c.getUser(), c.getTalk());

	}

	/**
	 * ���ӽФH = �ثe�ϥΪ� <br>
	 * �_���<br>.
	 *
	 * @param empid [String]
	 * @return [UserData]
	 * @throws Exception the exception
	 */
	public UserData getSpecUserData(String empid) throws Exception {
		return new UserData(empid, c.getTalk());

	}

	/**
	 * Do detail page process.
	 *
	 * @throws Exception the exception
	 */
	public void doDetailPageProcess() throws Exception {
		setBillOtherData();

	}

	/**
	 * ��ܪ��S�x�s�b��Ʈw����T �p�ӽФH���q�O,�m�W��.
	 *
	 * @throws Exception the exception
	 */
	public void setBillOtherData() throws Exception {
		// �ӽФH���`���� ��򥻸�����ӳ���
		userdata = getBillApplicant();
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDepName());
		String pl = c.getValue("PROJECT_LEADER").trim();
		// �M�ץD���H���
		if (pl.equals("")) {
			c.setValue("PROJECT_LEADER_NAME", "");
		} else {
			userdata = null;
			userdata = getSpecUserData(pl);
			c.setValue("PROJECT_LEADER_NAME", userdata.getHecname() + " " + userdata.getDepName());
		}
		userdata = null;

	}

}
