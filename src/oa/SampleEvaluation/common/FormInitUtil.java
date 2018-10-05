package oa.SampleEvaluation.common;

import java.sql.SQLException;

import jcx.jform.hproc;
import jcx.util.datetime;

/**
 * �i��BaseService�N�z hproc�ثe���X�� �Ѷ}�o�Ѧ�
 * 
 * @author u52116
 *
 */
public class FormInitUtil {

	UserData userdata;
	hproc c;

	public FormInitUtil(hproc c) throws SQLException, Exception {
		this.c = c;
		userdata = new UserData(c.getUser(), c.getTalk());
		// TODO Auto-generated constructor stub
	}

	public void doQueryPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		userdata = getNowApplicant();
		// ���ouser������O
		// ��Jquery�e���������
		c.setValue("QUERY_EMP_ID", userdata.getEmpid());
		c.setValue("QUERY_EMP_NAME", userdata.getHecname());
		c.setValue("QUERY_EMP_DEP", userdata.getDepName());
		String today = c.getToday("YYYYmmdd");
		String edate = today;
		String sdate = datetime.dateAdd(edate, "d", -14);
		c.setValue("QUERY_REQ_SDATE", sdate);
		c.setValue("QUERY_REQ_EDATE", edate);
		userdata = null;
		c.setNewView("QueryPage");
	}

	public void doAddPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		userdata = getNowApplicant();
		// �s�W���ӽФH�򥻸�ƶ�J
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT", c.getUser());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDepName());
		c.setValue("APP_DATE", c.getToday("YYYYmmdd"));
		userdata = null;
	}

	public void doPendingPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		setExistBillOtherData();

	}

	/**
	 * ���ӽФH != �ثe�ϥΪ� <br>
	 * ���� �ݳB�z �d��(�]�w�w�]��)��
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getBillApplicant() throws SQLException, Exception {
		// TODO Auto-generated method stub
		// c.message("doDetailPageProcess");
		return new UserData(c.getValue("APPLICANT"), c.getTalk());

	}

	/**
	 * ���ӽФH = �ثe�ϥΪ� <br>
	 * �_���<br>
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getNowApplicant() throws SQLException, Exception {
		// TODO Auto-generated method stub
		// c.message("doDetailPageProcess");
		return new UserData(c.getUser(), c.getTalk());

	}

	/**
	 * ���ӽФH = �ثe�ϥΪ� <br>
	 * �_���<br>
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public UserData getSpecUserData(String empid) throws SQLException, Exception {
		// TODO Auto-generated method stub
		// c.message("doDetailPageProcess");
		return new UserData(empid, c.getTalk());

	}

	public void doDetailPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		setExistBillOtherData();

	}

	/**
	 * �ثe���K�~��k
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void setExistBillOtherData() throws SQLException, Exception {
		// TODO Auto-generated method stub
		// �ӽФH���`���� ��򥻸�����ӳ���
		userdata = getBillApplicant();
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDepName());
		// �M�ץD���H���
		if (c.getValue("PROJECT_LEADER").equals("")) {
			c.setValue("PROJECT_LEADER_NAME", "");
		} else {
			c.setValue("PROJECT_LEADER_NAME", getSpecUserData(c.getValue("PROJECT_LEADER")).getHecname() + " "
					+ getSpecUserData(c.getValue("PROJECT_LEADER")).getDepName());
		}
		userdata = null;

	}

}
