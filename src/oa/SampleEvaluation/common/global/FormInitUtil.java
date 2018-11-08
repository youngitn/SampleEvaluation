package oa.SampleEvaluation.common.global;

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

	public FormInitUtil(hproc c) throws Exception {
		this.c = c;
		userdata = new UserData(c.getUser(), c.getTalk());
	}

	public void doQueryPageProcess() throws Exception {

		userdata = getNowApplicant();
		// ���ouser������O
		// ��Jquery�e���������
		c.setValue(FinalString.queryEmpidFieldName, userdata.getEmpid());
		c.setValue(FinalString.queryEmpNameFieldName, userdata.getHecname());
		c.setValue(FinalString.queryDepNoFieldName, userdata.getDepNo());
		c.setValue(FinalString.queryDepNameFieldName, userdata.getDepName());
		String today = c.getToday("YYYYmmdd");
		String edate = today;
		String sdate = datetime.dateAdd(edate, "d", -14);
		c.setValue(FinalString.queryReqSDateFieldName, sdate);
		c.setValue(FinalString.queryReqEDateFieldName, edate);
		userdata = null;
		c.setNewView("QueryPage");
	}

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

	public void doPendingPageProcess() throws Exception {
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
	public UserData getBillApplicant() throws Exception {
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
	public UserData getNowApplicant() throws Exception {
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
	public UserData getSpecUserData(String empid) throws Exception {
		return new UserData(empid, c.getTalk());

	}

	public void doDetailPageProcess() throws Exception {
		setExistBillOtherData();

	}

	/**
	 * �ثe���K�~��k
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void setExistBillOtherData() throws Exception {
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
