package oa.SampleEvaluation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import jcx.db.talk;
import oa.SampleEvaluation.common.DateTool;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.model.QueryResultDTO;
import oa.global.BaseDao;
import oa.global.UserData;

/**
 * �D�n�O�i��d�ߵ��G���̿�`�J
 * 
 * @author u52116
 *
 */
public class QueryResultService extends BaseDao {
	HprocImpl h;

	public QueryResultService(talk t) {
		this.clazz = QueryResultDTO.class;
		this.t = t;
	}

	public QueryResultService(HprocImpl h) {
		this.clazz = QueryResultDTO.class;
		this.h = h;
		this.t = this.h.getTalk();

	}

	public void setForm(HprocImpl h) {
		this.h = h;
	}

	public String[][] getResult(String sqlWhereString) throws SQLException, Exception {
		return addInfoToResult(getResultBySqlWhereString(sqlWhereString));
	}

	/**
	 * ��d�ߵ��G�G���}�C�i��һݸ�ƳB�z&�m�� �B�z���:ñ�֪��A,�O���Ѽ�
	 * 
	 * @param result [String[][]]
	 * @return [String[][]]
	 * @throws Exception the exception
	 */
	private String[][] addInfoToResult(String[][] result) throws Exception {
		int stateFieldIndex = 6;// ñ�֪��A��ܩ�d�ߵ��G��7��
		int numberOfOverdueDaysFieldIndex = 5;// �O���Ѽ���ܩ��d�ߵ��G��6��
		int pnoFieldIndex = 0; // �渹,���d�ߵ��G��1��
		int appDateFieldIndex = 4;// �O���ѼƷ|�Ψ�ӽФ�����,���d�ߵ��G��5��
		int urgencyFieldIndex = 3;// �O���ѼƷ|�Ψ�止�ʸ��,���d�ߵ��G��4��
		String overdueDays = "";
		String appDate = "";
		String urgency = "";
		String[][] ret = new String[1][];
		ArrayList<String[]> al = new ArrayList<String[]>();
		for (int i = 0; i < result.length; i++) {
			// �]�mñ�֪��A
			result[i][stateFieldIndex] = setNowFlowStateAndNowSignPeopleToField(result[i][pnoFieldIndex]);

			// �P�_�O���Ѽ�-->
			overdueDays = "�d�L���";
			appDate = result[i][appDateFieldIndex];
			urgency = result[i][urgencyFieldIndex];
			if (h.notEmpty(appDate) && h.notEmpty(urgency)) {
				//overdueDays = setNumberOfOverdueDaysToField(appDate, urgency);
			}
			result[i][numberOfOverdueDaysFieldIndex] = overdueDays;

			// �N�ŦX�ثeuser�d���v������ƥ�iArrayList
			if (isUserGotRight(result[i][pnoFieldIndex], result[i][1])) {
				al.add(result[i]);
			}
		}
		// �N�L�o��d�ߵ��G arrayList�ഫ2D array
		ret = (String[][]) al.toArray(new String[0][]);
		return ret;
	}

	/**
	 * ���o�ثeñ�֤H�Pñ�֪��A
	 *
	 * @param pno [String]
	 * @return [String]
	 */
	public String setNowFlowStateAndNowSignPeopleToField(String pno) {
		Vector nowPeople = h.getApprovablePeople(h.getFunctionName(), "pno='" + pno + "'");

		// ���oñ�־��v(���h�i���٬O�ϥ�SQL,�o�����API�Φ��I�s)
		String[][] flowHis = h.getFlowHistory(h.getFunctionName(), "a.pno='" + pno + "'");

		// �w�qñ�֪��A�r��
		StringBuilder nowFlowInfo = new StringBuilder();
		// �P�_�ӵ��渹�O�_�s�bñ�֪��A
		// �p�Lñ�֪��A�h���"�d�Lñ�����d"
		if (flowHis.length == 0) {
			nowFlowInfo.append("<font color=\"red\">�d�Lñ�����d</font>");
		} else {
			// �̷s�@��ñ�־��v=��e���d
			int lastFlowStateIndex = flowHis.length - 1;
			nowFlowInfo.append("<font color=\"blue\">" + flowHis[lastFlowStateIndex][0] + "</font>");
		}

		// �P�_�ӵ��渹�O�_�s�bñ�֪�
		// �p�Lñ�֪��A�h���"�d�Lñ�֤H"
		String peopleId = "<font color=\"red\">(�d�Lñ�֤H)</font>";
		if (nowPeople == null || nowPeople.isEmpty()) {
			nowFlowInfo.append(peopleId);
		} else {
			peopleId = (String) nowPeople.get(0);
			nowFlowInfo.append("(");
			nowFlowInfo.append(peopleId + h.getName(peopleId));
			nowFlowInfo.append(")");
		}

		return nowFlowInfo.toString();
	}

	/**
	 * �O���Ѽƭp��
	 *
	 * @param appDate [String]
	 * @param urgency [String]
	 * @return [String]
	 * @throws Exception the exception
	 */
	public String setNumberOfOverdueDaysToField(String appDate, String urgency) throws Exception {

		if (appDate == null || "".equals(appDate)) {
			return "�d�L���";
		} else {
			appDate = appDate.trim();
			urgency = urgency.trim();

			String deadLine = h.getDeadLine(appDate, urgency);
			int days = DateTool.diffDays(appDate, deadLine);
			if (days > 0) {
				return "���O��";
			} else {
				return "�w�O�� " + days + " ��";
			}
		}

	}

	/**
	 * �P�_�ثe�n�J�ϥΪ̪��v��
	 *
	 * @param pno       [String]
	 * @param applicant [String]
	 * @return true, if is user got right
	 * @throws Exception the exception
	 */
	private boolean isUserGotRight(String pno, String applicant) throws Exception {
		UserData nowUserData = new UserData(h.getUser(), t);
		UserData applicantData = new UserData(applicant, t);
		boolean right = false;
		// ���ʳB�g�z�i�d���
		if (nowUserData.getDepNo().equals("21") || nowUserData.getEmpid().equals("admin")) {
			right = true;
		}
		// ���ʦP�Ҭd��
		else if (nowUserData.getDepNo().equals(applicantData.getDepNo())) {
			right = true;
		} else {
			// ñ�ֹL�H���i�d
			String[][] his = h.getFlowHistory(h.getFunctionName(), "pno='" + pno + "'");
			for (String[] strings : his) {
				if (strings[1].equals(nowUserData.getEmpid())) {
					right = true;
					break;
				}
			}
		}
		return right;
	}
}
