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
 * 主要是進行查詢結果的依賴注入
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
	 * 對查詢結果二維陣列進行所需資料處理&置換 處理欄位:簽核狀態,逾期天數
	 * 
	 * @param result [String[][]]
	 * @return [String[][]]
	 * @throws Exception the exception
	 */
	private String[][] addInfoToResult(String[][] result) throws Exception {
		int stateFieldIndex = 6;// 簽核狀態顯示於查詢結果第7欄
		int numberOfOverdueDaysFieldIndex = 5;// 逾期天數顯示於於查詢結果第6欄
		int pnoFieldIndex = 0; // 單號,位於查詢結果第1欄
		int appDateFieldIndex = 4;// 逾期天數會用到申請日期資料,位於查詢結果第5欄
		int urgencyFieldIndex = 3;// 逾期天數會用到急迫性資料,位於查詢結果第4欄
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
				//overdueDays = setNumberOfOverdueDaysToField(appDate, urgency);
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
	 * 取得目前簽核人與簽核狀態
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
	 * 逾期天數計算
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
	 * 判斷目前登入使用者的權限
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
