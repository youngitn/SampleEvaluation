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
 * The Class Query.
 */
public class Query {

	talk t;
	HprocImpl h;
	String[][] ret;

	/**
	 * Instantiates a new query.
	 *
	 * @param h [HprocImpl]
	 */
	public Query(HprocImpl h) {

		this.t = h.getTalk();
		this.h = h;

	}

	/**
	 * get Result By QueryConditionDto define.
	 *
	 * @return the result by query condition
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public Query getResultByQueryCondition() throws SQLException, Exception {
		// 從畫面取得查詢條件並塞入QueryConditionDto,後回傳
		QueryConditionDto sqlWhereSto = new QueryConditionDto();
		sqlWhereSto.getFormData(h);
		// 將前一步驟取得的QueryConditionDto轉換成SQL WHERE敘述式
		String sqlWhereString = DtoUtil.queryConditionDtoConvertToSqlWhereString(sqlWhereSto);
		// 將原本的SQL WHERE增加申請人部門編號的查詢條件-qDepNoInSqlWhere(部門編號)
		// 因申請人部門編號沒有儲存在資料庫,所以須手動撰寫SQL敘述
		sqlWhereString = sqlWhereString + qDepNoInSqlWhere(sqlWhereSto.getQ_DEP_NO());
		// 實體化dao服務,因查詢結果需符合QueryResultDto,所以實體化相對應Dao.
		QueryResultService resultService = new QueryResultService(this.t);

		ret = (String[][]) resultService.getResultBySqlWhereString(sqlWhereString);
		//
		ret = addInfoToResult(ret);
		return this;

	}

	public String[][] getResult() {
		return this.ret;
	}

	/**
	 * Q dep no in sql where.
	 *
	 * @param depno [String]
	 * @return [String]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public String qDepNoInSqlWhere(String depno) throws SQLException, Exception {
		if (!"".equals(depno)) {

			return " AND APPLICANT IN (SELECT EMPID FROM USER_INOFFICE_INFO_VIEW WHERE DEPT_NO = '" + depno + "' )";
		}

		return "";
	}

	/**
	 * Sets the now flow state and now sign people to field.
	 *
	 * @param pno [String]
	 * @return [String]
	 */
	public String setNowFlowStateAndNowSignPeopleToField(String pno) {
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
	 * Sets the number of overdue days to field.
	 *
	 * @param appDate [String]
	 * @param urgency [String]
	 * @return [String]
	 * @throws Exception the exception
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
	 * Sets the info to 2 D string array result.
	 *
	 * @param result [String[][]]
	 * @return [String[][]]
	 * @throws Exception the exception
	 */
	private String[][] addInfoToResult(String[][] result) throws Exception {
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
			result[i][stateFieldIndex] = setNowFlowStateAndNowSignPeopleToField(result[i][pnoFieldIndex]);

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

	/**
	 * Checks if is user got right.
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
