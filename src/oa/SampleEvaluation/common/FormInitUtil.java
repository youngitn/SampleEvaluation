package oa.SampleEvaluation.common;

import java.sql.SQLException;

import jcx.jform.hproc;
import jcx.util.datetime;

/**
 * 
 * @author u52116
 *
 */
public class FormInitUtil extends BaseFormOnload {

	UserData userdata;

	public FormInitUtil(hproc c) throws SQLException, Exception {
		super(c);
		userdata = new UserData(c.getUser(), c.getTalk());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doQueryPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		userdata = getNowApplicant();
		// ���ouser������O
		// ��Jquery�e���������
		c.setValue("QUERY_EMP_ID", userdata.getEmpid());
		c.setValue("QUERY_EMP_NAME", userdata.getHecname());
		c.setValue("QUERY_EMP_DEP", userdata.getDep_name());
		String today = c.getToday("YYYYmmdd");
		String edate = today;
		String sdate = datetime.dateAdd(edate, "d", -14);
		c.setValue("QUERY_REQ_SDATE", sdate);
		c.setValue("QUERY_REQ_EDATE", edate);
		userdata = null;
		c.setNewView("QueryPage");
	}

	@Override
	public void doAddPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		userdata = getNowApplicant();
		// �s�W���ӽФH�򥻸�ƶ�J
		c.setValue("CPNYID", userdata.getCpnyid());
		c.setValue("APPLICANT", c.getUser());
		c.setValue("APPLICANT_NAME", userdata.getHecname());
		c.setValue("APPLICANT_DEP_NAME", userdata.getDep_name());
		c.setValue("APP_DATE", c.getToday("YYYYmmdd"));
		userdata = null;
	}

	@Override
	/**
	 * ���ñ�֬���
	 */
	public void doFlowPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub
		// ��ܰhñ�q��
		setExistBillOtherData();
		c.message("FLOW");
		c.addScript("try{showRejectWarning();}catch(e){}");

	}

	@Override
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

	@Override
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
		c.setValue("APPLICANT_DEP_NAME", userdata.getDep_name());
		// �M�ץD���H���
		if (c.getValue("PROJECT_LEADER").equals("")) {
			c.setValue("PROJECT_LEADER_NAME", "");
		} else {
			c.setValue("PROJECT_LEADER_NAME", getSpecUserData(c.getValue("PROJECT_LEADER")).getHecname() + " "
					+ getSpecUserData(c.getValue("PROJECT_LEADER")).getDep_name());
		}
		userdata = null;

	}

	@Override
	public void doOtherPageProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void doViewProcess() throws SQLException, Exception {
		// TODO Auto-generated method stub

	}

}
