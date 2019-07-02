package oa.SampleEvaluation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import jcx.db.talk;
import oa.SampleEvaluation.common.DateTool;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.enums.AppTypeEnum;
import oa.SampleEvaluation.enums.UrgencyType;
import oa.SampleEvaluation.model.QueryResultDTO;
import oa.global.BaseDao;
import oa.global.UserData;

/**
 * �D�n�O�i��d�ߵ��G���̿�`�J.
 *
 * @author u52116
 */
public class QueryResultService extends BaseDao {

	/** The h. */
	HprocImpl h;

	/**
	 * Instantiates a new query result service.
	 *
	 * @param t [talk]
	 */
	public QueryResultService(talk t) {
		this.clazz = QueryResultDTO.class;
		this.t = t;
	}

	/**
	 * Instantiates a new query result service.
	 *
	 * @param h [HprocImpl]
	 */
	public QueryResultService(HprocImpl h) {
		this.clazz = QueryResultDTO.class;
		this.h = h;
		this.t = this.h.getTalk();

	}

	/**
	 * Sets the Form.
	 *
	 * @param h void
	 */
	public void setForm(HprocImpl h) {
		this.h = h;
	}

	/**
	 * Gets the Result.
	 *
	 * @param sqlWhereString [String]
	 * @return [String[][]]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public String[][] getResult(String sqlWhereString) throws SQLException, Exception {
		System.out.println("getResult------>" + sqlWhereString);
		return addInfoToResult(getResultBySqlWhereString(sqlWhereString));
	}

	/**
	 * ��d�ߵ��G�G���}�C�i��һݸ�ƳB�z&�m�� �B�z���:ñ�֪��A,�O���Ѽ�.
	 * 
	 * ����k�ݭ��c,���z��,���ʻݭק�{��. �W�[�d�ߵ��G���: 1. DTO�[�J�������ݩʦ��� 2 . �bem�]�p�Ҧ����W�[ 3. �b����k��ʦ첾�}�C
	 * 
	 * @param result [String[][]]
	 * @return [String[][]]
	 * @throws Exception the exception
	 */
	private String[][] addInfoToResult(String[][] result) throws Exception {
		System.out.println("resul----->t" + result.length);
		int stateFieldIndex = 6;// ñ�֪��A��ܩ�d�ߵ��G��7��
		int numberOfOverdueDaysFieldIndex = 8;// �O���Ѽ���ܩ��d�ߵ��G��9��
		int pnoFieldIndex = 1; // �渹,���d�ߵ��G��2��
		int appDateFieldIndex = 10;// �O���ѼƷ|�Ψ�ӽФ�����,���d�ߵ��G��11��
		int urgencyFieldIndex = 0;// �O���ѼƷ|�Ψ�止�ʸ��,���d�ߵ��G��1��
		int appUserFieldIndex = 9;// �ӽФH���d�ߵ��G��10��
		int appTypeFieldIndex = 11;// �ӽ��������d�ߵ��G��12��
		int urgencyTypeFieldIndex = 13;// �止�ʦ��d�ߵ��G��14��
		int custnoAndCustName = 4;
		String overdueDays = "";
		String appDate = "";
		String urgency = "";
		String[][] ret = new String[1][];
		ArrayList<String[]> al = new ArrayList<String[]>();
		for (int i = 0; i < result.length; i++) {

			// ñ�֪��A�B�z�n������w���,��ñ�֬y�{���ץ�~����o�q.
			// �渹8�X��ܰe�X�᪺���,�p��8�X���Ȧs
			if (result[i][pnoFieldIndex].length() == 8) {
				result[i][stateFieldIndex] = setNowFlowStateAndNowSignPeopleToField(result[i][pnoFieldIndex]);
			}
			// �P�_�O���Ѽ�-->
			overdueDays = "�d�L���";
			appDate = result[i][appDateFieldIndex];
			urgency = result[i][urgencyFieldIndex];
			if (h.notEmpty(appDate) && h.notEmpty(urgency)) {
				overdueDays = setNumberOfOverdueDaysToField(appDate, urgency);
			}
			result[i][numberOfOverdueDaysFieldIndex] = overdueDays;

			if (!"".equals(result[i][appTypeFieldIndex]) && !"".equals(result[i][urgencyTypeFieldIndex])) {
				result[i][11] = AppTypeEnum.getAppType(result[i][appTypeFieldIndex]) + "-"
						+ UrgencyType.getUrgency(result[i][urgencyTypeFieldIndex]);
			}

			result[i][13] = getDeadLine(urgency, h.getTalk(), result[i][appDateFieldIndex]);// �w�p������
																							// ������13
																							// sql
																							// select���15(dto)
			result[i][14] = result[i][16];// ���z���
			result[i][15] = new UserData(result[i][appUserFieldIndex], t).getDepName();// �ӽФH���
			result[i][16] = result[i][18];// QR�s��
			result[i][17] = result[i][stateFieldIndex];// ñ�֪��A
			result[i][18] = result[i][20];// �s�y�Ӱ�O
			result[i][19] = result[i][21];// �s�y�ӧ帹
			result[i][20] = result[i][22];// �s�y�Ӽt�}
			result[i][21] = result[i][23];// �ƶq
			result[i][22] = result[i][24];// ���

			if (result[i][custnoAndCustName] != null && result[i][custnoAndCustName].length() > 4) {
				result[i][custnoAndCustName] = result[i][custnoAndCustName].substring(5);
			}
			// �N�ŦX�ثeuser�d���v������ƥ�iArrayList

			if (isUserGotRight(result[i][pnoFieldIndex], result[i][appUserFieldIndex])) {
				result[i][appUserFieldIndex] = result[i][appUserFieldIndex] + h.getName(result[i][appUserFieldIndex]);
				al.add(result[i]);
			}
		}
		// �N�L�o��d�ߵ��G arrayList�ഫ2D array
		ret = (String[][]) al.toArray(new String[0][]);
		return ret;
	}

	/**
	 * ���o�ثeñ�֤H�Pñ�֪��A.
	 *
	 * @param pno [String]
	 * @return [String]
	 */
	public String setNowFlowStateAndNowSignPeopleToField(String pno) {

		// ���oñ�־��v(���h�i���٬O�ϥ�SQL,�o�����API�Φ��I�s)
		String[][] flowHis = h.getFlowHistory(h.getFunctionName(), "a.pno='" + pno + "'");

		// �w�qñ�֪��A�r��
		StringBuilder nowFlowInfo = new StringBuilder();
		// �P�_�ӵ��渹�O�_�s�bñ�֪��A
		// �p�Lñ�֪��A�h���"�d�Lñ�����d"
		int lastFlowStateIndex = flowHis.length - 1;
		if (flowHis.length == 0) {

			nowFlowInfo.append("<font color=\"red\">�d�Lñ�����d</font>");
			return nowFlowInfo.toString();
		} else {
			// �̷s�@��ñ�־��v=��e���d

			nowFlowInfo.append("<font color=\"blue\">" + flowHis[lastFlowStateIndex][0] + "</font>");
		}
		if (flowHis[lastFlowStateIndex][0].equals("����")) {
			return nowFlowInfo.toString();
		}
		// �P�_�ӵ��渹�O�_�s�bñ�֪�
		// �p�Lñ�֪��A�h���"�d�Lñ�֤H"
		Vector nowPeople = h.getApprovablePeople(h.getFunctionName(), "pno='" + pno + "'");
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
	 * �O���Ѽƭp��.
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
			System.out.println("appDate----------->" + appDate);
			System.out.println("urgency----------->" + urgency);
			String deadLine = h.getDeadLine(appDate, urgency);
			System.out.println("deadLine----------->" + deadLine);
			int days = DateTool.diffDays(appDate, deadLine);
			if (days > 0) {
				return "���O��";
			} else {
				return "�w�O�� " + days + " ��";
			}
		}

	}

	private String getDeadLine(String value, talk t, String appDate) {
		int addDaysNum = 0;
		String dl = "";
		System.out.println("value=========>" + value);
		if (value.equals("A")) {
			addDaysNum = 100;
		} else if (value.equals("B")) {
			addDaysNum = 110;
		} else if (value.equals("C")) {
			addDaysNum = 130;
		}
		// c.add(Calendar.DATE, addDaysNum);
		// Date d = c.getTime();
		// SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		// String dldate = sdFormat.format(d);
		try {
			dl = DateTool.getAfterWorkDate(appDate, addDaysNum, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dl;

		// setValue("DL", DateTool.getAfterWorkDate(getValue("APP_DATE"),addDaysNum,
		// getTalk()));

	}

	/**
	 * �P�_�ثe�n�J�ϥΪ̪��v��.
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
