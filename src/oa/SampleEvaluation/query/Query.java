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
 * 查詢條件欄位
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
	 * 將查詢條件物件做為參數傳入, 回傳查詢結果的二維陣列.
	 * 
	 * @param targetLikeThis 需搭配xmaker使用
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
		//帶值DTO
		String targetCondition = DtoUtil.getSqlWhereStringByXmakerMappingDbFieldName(targetLikeThis);
		return targetCondition + qDepNoInSqlWhere(targetLikeThis.getQ_DEP_NO());
	}

	/**
	 * 針對申請人用部門做查詢條件, 手動創建where字串
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
	 * 設置各張單據簽核關卡與簽核人資訊
	 * 
	 * @param pno
	 * @return
	 */
	public String setNowFlowStateAndNowSignPeopleToFidld(String pno) {
		Vector nowPeople = h.getApprovablePeople(h.getFunctionName(), "pno='" + pno + "'");

		// 取得簽核歷史(底層可能還是使用SQL,這邊維持API形式呼叫)
		String[][] flowHis = h.getFlowHistory(h.getFunctionName(), "a.pno='" + pno + "'");

		// 定義簽核狀態字串
		StringBuilder nowFlowInfo = new StringBuilder();
		// 判斷該筆單號是否存在簽核狀態
		// 如無簽核狀態則顯示"查無簽核關卡"
		if (flowHis.length == 0) {
			nowFlowInfo.append("<font color=\"red\">查無簽核關卡</font>");
		} else {
			// 最新一筆簽核歷史=當前關卡
			int lastFlowStateIndex = flowHis.length - 1;
			nowFlowInfo.append("<font color=\"blue\">" + flowHis[lastFlowStateIndex][0] + "</font>");
		}

		// 判斷該筆單號是否存在簽核者
		// 如無簽核狀態則顯示"查無簽核人"
		String peopleId = "<font color=\"red\">(查無簽核人)</font>";
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
	 * 取得兩逾期天數
	 * 
	 * @param appDate
	 * @param urgency
	 * @return
	 * @throws Exception
	 */
	public String setNumberOfOverdueDaysToField(String appDate, String urgency) throws Exception {

		if (appDate == null || "".equals(appDate)) {
			return "查無資料";
		} else {
			appDate = appDate.trim();
			urgency = urgency.trim();

			String deadLine = h.getDeadLine(appDate, urgency);
			int days = DateTool.diffDays(appDate, deadLine);
			if (days > 0) {
				return "未逾期";
			} else {
				return "已逾期 " + days + " 日";
			}
		}

	}

	/**
	 * 將二維陣列查詢結果傳入, 進行資料加工作業
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
			// 設置簽核狀態
			result[i][stateFieldIndex] = setNowFlowStateAndNowSignPeopleToFidld(result[i][pnoFieldIndex]);

			// 判斷逾期天數-->
			overdueDays = "查無資料";
			appDate = result[i][appDateFieldIndex];
			urgency = result[i][urgencyFieldIndex];
			if (h.notEmpty(appDate) && h.notEmpty(urgency)) {
				overdueDays = setNumberOfOverdueDaysToField(appDate, urgency);
			}
			result[i][numberOfOverdueDaysFieldIndex] = overdueDays;

			// 將符合目前user查詢權限的資料丟進ArrayList
			if (isUserGotRight(result[i][pnoFieldIndex], result[i][1])) {
				al.add(result[i]);
			}
		}
		// 將過濾後查詢結果 arrayList轉換2D array
		ret = (String[][]) al.toArray(new String[0][]);
		return ret;
	}

	private boolean isUserGotRight(String pno, String applicant) throws Exception {
		UserData nowUserData = new UserData(h.getUser(), t);
		UserData applicantData = new UserData(applicant, t);
		boolean right = false;
		// 採購處經理可查兩課
		if (nowUserData.getDepNo().equals("21") || nowUserData.getEmpid().equals("admin")) {
			right = true;
		}
		// 採購同課查詢
		else if (nowUserData.getDepNo().equals(applicantData.getDepNo())) {
			right = true;
		} else {
			// 簽核過人員可查
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
