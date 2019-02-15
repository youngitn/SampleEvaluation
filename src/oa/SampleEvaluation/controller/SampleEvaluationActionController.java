package oa.SampleEvaluation.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.common.global.FormInitUtil;
import oa.SampleEvaluation.common.global.UIHidderString;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.enums.Actions;
import oa.SampleEvaluation.query.Query;
import oa.SampleEvaluation.query.QueryConditionDto;

/**
 * 
 * 
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends HprocImpl {

	private boolean confirm = true;
	private BaseService service;
	String actionObjName;
	int stateFieldIndex = 6;
	int numberOfOverdueDaysFieldIndex = 5;
	int pnoFieldIndex = 0;
	int appDateFieldIndex = 4;
	int urgencyFieldIndex = 3;

	@Override
	public String action(String arg0) throws Throwable {
		// 類別屬性初始化設定
		setProperty();
		try {
			// 按鈕動作處理進入點
			switch (Actions.valueOf(actionObjName.trim())) {
			case QUERY_CLICK:
				doQuery();

				break;
			case SAVE_CLICK:
				doSave();
				break;
			case SHOW_DETAIL_CLICK:
				showDetail();
				break;
			default:
				break;
			}
		} catch (EnumConstantNotPresentException e) {
			message("enum:Actions.clss 發生無法辨識的意外");
		}
		return null;

	}

	private void setProperty() throws Exception {
		// for enum switch
		actionObjName = getActionName(getName());

		service = new BaseService(this);
		if (service == null || service.getFunctionName().equals("")) {
			throw new Exception("new BaseService(this) is null......");
		}

	}

	private void showDetail() throws SQLException, Exception {
		setValue("QUERY_LIST_PNO", getValue("QUERY_LIST.PNO"));
		BaseDao bao = new SampleEvaluationService(getTalk());
		SampleEvaluation s = (SampleEvaluation) bao.findById(getValue("QUERY_LIST.PNO"));
		DtoUtil.setDtoDataToForm(s, this);
		FormInitUtil init = new FormInitUtil(this);

		init.doDetailPageProcess();
		if (getValue("IS_TRIAL_PRODUCTION").equals("1")) {
			setVisible("ASSESSOR", true);
		}
		setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));

		showSubFlowSignPeopleTab();
		addScript(UIHidderString.hideDmakerAddButton() + UIHidderString.hideDmakerFlowPanel());

	}

	/**
	 * 主查詢
	 * 
	 * @throws Throwable
	 */
	private void doQuery() throws Throwable {
		// 透過 DtoUtil 取得client端查詢條件value,並寫入QueryConditionDto
		QueryConditionDto targetLikeThis = (QueryConditionDto) DtoUtil.setFormDataToDto(new QueryConditionDto(), this);
		// 取得2D array格式查詢結果
		String[][] list = new Query().get2DStringArrayResult(targetLikeThis, getTalk());

		if (list == null || list.length <= 0) {
			message("查無紀錄");
		} else {
			setTableData("QUERY_LIST", setInfoTo2DStringArrayResult(list));
		}
	}

	private void doSave() throws Throwable {

		// 必填欄位資料 (欄位名,欄位標題)
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("APPLICANT", "申請人");
		fieldMap.put("APP_TYPE", "申請類型");
		fieldMap.put("RECEIPT_UNIT", "受理單位");
		fieldMap.put("URGENCY", "急迫性");
		fieldMap.put("MATERIAL", "原物料名稱");
		fieldMap.put("AB_CODE", "AB編號");
		fieldMap.put("MFR", " 製造商");
		fieldMap.put("MFG_LOT_NO", "製造批號");
		fieldMap.put("SUPPLIER", "供應商");
		fieldMap.put("QTY", "數量");
		fieldMap.put("UNIT", "單位");
		fieldMap.put("SAP_CODE", "SAP物料編號");
		// 新增不需cdo等額外其他資料
		AddUtil addUtil = new AddUtil(service);
		ArrayList<String> ret = (ArrayList<String>) addUtil.emptyCheck(fieldMap);
		if (ret != null && ret.size() > 0) {
			message("以下欄位請做選擇或輸入:" + ret);
		} else {
			int result = showConfirmDialog("確定送出表單？", "溫馨提醒", 0);
			if (result != 1) {
				// 產生單號
				String uuid = addUtil.getUUID(getTableName());

				// DMAKER 內建ADD功能 需將資料塞進去表單欄位才吃的到
				setValue("PNO", uuid);
				fileItemSetChecker();
				// confirm = true 控制是否真的送出
				if (confirm) {
					// 寫在view部分會好點
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	private void fileItemSetChecker() {

		if (!getValue("FILE_SPEC").equals("")) {
			setValue("PROVIDE_SPEC", "1");
		}
		if (!getValue("FILE_COA").equals("")) {
			setValue("PROVIDE_COA", "1");
		}
		if (!getValue("FILE_SDS").equals("")) {
			setValue("PROVIDE_SDS", "1");
		}
		if (!getValue("FILE_OTHERS").equals("")) {
			setValue("PROVIDE_OTHERS", "1");
		}
		if (!getValue("FILE_TEST_METHOD").equals("")) {
			setValue("PROVIDE_TEST_METHOD", "1");
		}
	}

	private String getActionName(String Name) {

		Name = Name.toUpperCase();
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	public String setNumberOfOverdueDaysToField(String appDate, String urgency) throws Exception {

		if (appDate == null || "".equals(appDate)) {
			return "查無資料";
		} else {
			appDate = appDate.trim();
			urgency = urgency.trim();

			String deadLine = getDeadLine(appDate, urgency);
			int days = diffDays(appDate, deadLine);
			if (days > 0) {
				return "未逾期";
			} else {
				return "已逾期 " + days + " 日";
			}
		}

	}

	public int diffDays(String FD, String SD) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date start = sdf.parse(FD);
		Date end = sdf.parse(SD);

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(start);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 閏年
				{
					timeDistance += 366;
				} else // 不是閏年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			System.out.println("判斷day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}

	public String setNowFlowStateAndNowSignPeopleToFidld(String pno) {
		Vector nowPeople = getApprovablePeople(getFunctionName(), "pno='" + pno + "'");

		// 取得簽核歷史(底層可能還是使用SQL,這邊維持API形式呼叫)
		String[][] flowHis = getFlowHistory(getFunctionName(), "a.pno='" + pno + "'");

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
			nowFlowInfo.append(peopleId + getName(peopleId));
			nowFlowInfo.append(")");
		}

		return nowFlowInfo.toString();
	}

	public String[][] setInfoTo2DStringArrayResult(String[][] result) throws Exception {
		String overdueDays = "";
		String appDate = "";
		String urgency = "";
		for (int i = 0; i < result.length; i++) {
			overdueDays = "查無資料";
			result[i][stateFieldIndex] = setNowFlowStateAndNowSignPeopleToFidld(result[i][pnoFieldIndex]);
			appDate = result[i][appDateFieldIndex];
			urgency = result[i][urgencyFieldIndex];
			if (notEmpty(appDate) && notEmpty(urgency)) {
				//System.out.println("appDate not null");
				overdueDays = setNumberOfOverdueDaysToField(appDate, urgency);
			}
			result[i][numberOfOverdueDaysFieldIndex] = overdueDays;
		}

		return result;
	}

}
