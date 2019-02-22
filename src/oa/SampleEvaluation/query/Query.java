package oa.SampleEvaluation.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import jcx.db.talk;
import oa.SampleEvaluation.common.DateTool;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.query.dao.QueryResultService;
import oa.SampleEvaluation.query.dto.QueryConditionDto;
import oa.global.DtoUtil;
import oa.global.UserData;

/**
 * �d�߱������
 * 
 * @author u52116
 *
 */
public class Query {

	talk t;
	HprocImpl h;
	DtoUtil dtoUtil;

	public Query(HprocImpl h) {

		this.t = h.getTalk();
		this.h = h;

	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String[][] getMainQueryResult() throws SQLException, Exception {
		return get2DStringArrayResult();

	}

	/**
	 * �N�d�߱��󪫥󰵬��ѼƶǤJ, �^�Ǭd�ߵ��G���G���}�C.
	 * 
	 * @param targetLikeThis �ݷf�txmaker�ϥ�
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String[][] get2DStringArrayResult() throws SQLException, Exception {
		QueryResultService resultService = new QueryResultService(this.t);
		return setInfoTo2DStringArrayResult((String[][]) resultService.findByConditionReturn2DStringArray(getCondition()));
	}

	public String getCondition() throws SQLException, Exception {
		QueryConditionDto targetLikeThis = (QueryConditionDto) DtoUtil.setFormDataToDto(new QueryConditionDto(), h);
		//�a��DTO
		String targetCondition = DtoUtil.getSqlWhereStringByXmakerMappingDbFieldName(targetLikeThis);
		return targetCondition + qDepNoInSqlWhere(targetLikeThis.getQ_DEP_NO());
	}

	/**
	 * �w��ӽФH�γ������d�߱���, ��ʳЫ�where�r��
	 * 
	 * @param qDepNo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String qDepNoInSqlWhere(String qDepNo) throws SQLException, Exception {
		if (!"".equals(qDepNo)) {

			return " AND APPLICANT IN (SELECT EMPID FROM USER_INOFFICE_INFO_VIEW WHERE DEPT_NO = '" + qDepNo + "' )";
		}

		return "";
	}

	/**
	 * �]�m�U�i���ñ�����d�Pñ�֤H��T
	 * 
	 * @param pno
	 * @return
	 */
	public String setNowFlowStateAndNowSignPeopleToFidld(String pno) {
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
	 * ���o��O���Ѽ�
	 * 
	 * @param appDate
	 * @param urgency
	 * @return
	 * @throws Exception
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
	 * �N�G���}�C�d�ߵ��G�ǤJ, �i���ƥ[�u�@�~
	 * 
	 * @param result String[][]
	 * @return
	 * @throws Exception
	 */
	private String[][] setInfoTo2DStringArrayResult(String[][] result) throws Exception {
		int stateFieldIndex = 6;
		int numberOfOverdueDaysFieldIndex = 5;
		int pnoFieldIndex = 0;
		int appDateFieldIndex = 4;
		int urgencyFieldIndex = 3;
		String overdueDays = "";
		String appDate = "";
		String urgency = "";
		String[][] ret = new String[1][];
		ArrayList<String[]> al = new ArrayList<String[]>();
		for (int i = 0; i < result.length; i++) {
			// �]�mñ�֪��A
			result[i][stateFieldIndex] = setNowFlowStateAndNowSignPeopleToFidld(result[i][pnoFieldIndex]);

			// �P�_�O���Ѽ�-->
			overdueDays = "�d�L���";
			appDate = result[i][appDateFieldIndex];
			urgency = result[i][urgencyFieldIndex];
			if (h.notEmpty(appDate) && h.notEmpty(urgency)) {
				overdueDays = setNumberOfOverdueDaysToField(appDate, urgency);
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
