package oa.SampleEvaluation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.DoQuery;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.tableObject.SampleEvaluation;
import oa.SampleEvaluation.common.UIHidderString;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;

/**
 * 嘗試可測試寫法
 * 
 * @author u52116
 *
 */
public class Controller extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// 表單載入後處理
		// 各頁面載入處理於類別中實作
		// importJquery();
		FormInitUtil init = new FormInitUtil(this);
		setValue("NOW_INIT", getName());
		buildSysFormObj();

		String actionObjName = getActionName(getName());

		//
		// 按鈕動作處理進入點
		switch (ButtonActionByName.valueOf(actionObjName.trim())) {
		case QUERY_PAGE_INIT:// 進入查詢畫面
			addScript(UIHidderString.hideDmakerAddButton());
			init.doQueryPageProcess();
			break;

		case ADD_PAGE_INIT:// 進入新增畫面
			addScript(UIHidderString.hideDmakerAddButton());
			init.doAddPageProcess();
			break;

		case PENING_PAGE_INIT:// 進入待處理畫面

			init.doPendingPageProcess();
			break;

		case DETAIL_PAGE_INIT:// 進入明細畫面
			init.doDetailPageProcess();
			break;
		case FLOW_PAGE_INIT:// 進入流程簽核畫面
			init.doPendingPageProcess();
			// 簽核完後跳至主頁面
			String pno = getValue("PNO").trim();
			if (pno.length() <= 0) {
				changeForm(getFunctionName());
			} else {
				String sql = "select f_inp_info from " + getTableName() + "_flowc where PNO = '" + pno + "'";
				String[][] ret = getTalk().queryFromPool(sql);
				if (ret.length > 0) {
					String memo = ret[0][0];
					if (memo.startsWith("[退簽]")) {
						addScript("callRejectWarning();");
					}
				}
			}
			// 如果帶出的資料 試製選項有打勾 就顯示評估人員
			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")) {
				setVisible("ASSESSOR", true);
			}
			if (getState().trim().equals("組長")) {
				setEditable("IS_CHECK", true);
				setEditable("IS_TRIAL_PRODUCTION", true);

				setEditable("ASSESSOR", true);
				setEditable("LAB_EXE", true);

			}
			if (getState().trim().equals("試製單號填寫")) {
				setEditable("NOTIFY_NO_TRIAL_PROD", true);
			}
			if (getState().trim().equals("受理單位主管分案")) {
				setEditable("DESIGNEE", true);
			}
			if (getState().trim().equals("待處理")) {
				// 相關處理寫在UI的背景區塊ADD_BACKGROUND中
				// 因為這邊的邏輯 在待處理關卡吃不到
			}

			break;
		case QUERY_CLICK:
			setCommonObiQueryData();
			doQuery();
			break;

		case SAVE_CLICK:
			save();
			break;

		case SHOW_DETAIL_CLICK:// 這個動作比較尷尬 屬於載入畫面但卻是按鈕發動
			UIHide();
			detailPage();
			init.doDetailPageProcess();
			break;

		default:

			break;
		}
		return null;

	}

	public void setCommonObiQueryData() {
		cdo.setTableApplicantFieldName("APPLICANT");
		cdo.setTableAppDateFieldName("APP_DATE");
		cdo.setQueryFieldNameBillId("QUERY_PNO");
		cdo.setQueryFieldNameStartAppDate("QUERY_REQ_SDATE");
		cdo.setQueryFieldNameEndAppDate("QUERY_REQ_EDATE");
		cdo.setQueryFieldNameFlowStatus("r_status");

		cdo.setQueryFieldValueEmpid(getValue("QUERY_EMP_ID"));
		cdo.setQueryFieldValueBillId(getValue("QUERY_PNO"));
		cdo.setQueryFieldValueStartAppDate(getValue("QUERY_REQ_SDATE"));
		cdo.setQueryFieldValueEndAppDate(getValue("QUERY_REQ_EDATE"));
		cdo.setQueryFieldValueFlowStatus(getValue("r_status"));
		cdo.setQueryFieldValueSubFlowStatus(getValue("r_sub_status"));
		cdo.setFunctionName(getFunctionName());
	}

	public void doQuery() throws Throwable {
		// TODO 執行時出現條件為NULL
		// 申請人工號 table欄位名稱=APPLICANT DoQuery.getAdvancedCondition()<--用來組SQL用
		
		String[][] list = DoQuery.getQueryResult(cdo);
		if (list.length != 0) {
			ArrayList<String> flist = new ArrayList<String>();
			flist.add("PNO");
			flist.add("APPLICANT");
			flist.add("APP_TYPE");
			flist.add("URGENCY");
			flist.add("APP_DATE");
			flist.add("'簽核狀態'");
			flist.add("'明細'");
			flist.add("'簽核紀錄'");

			setTableData("QUERY_LIST", getQueryResultAfterProcess(list, flist));
		} else {
			setTableData("QUERY_LIST", list);
			message("查無紀錄");
		}

	}

	public void save() throws Throwable {
		Map<String, Map<String, String>> fieldMap = new HashMap<String, Map<String, String>>();

		fieldMap.put("APPLICANT", AddUtil.addValidateField(getValue("APPLICANT").trim(), "申請人"));
		fieldMap.put("APP_TYPE", AddUtil.addValidateField(getValue("APP_TYPE").trim(), "申請類型"));
		fieldMap.put("APPLICANT", AddUtil.addValidateField(getValue("RECEIPT_UNIT").trim(), "受理單位"));

		ArrayList<String> ret = AddUtil.emptyCheck(fieldMap);
		if (ret != null && ret.size() > 0) {
			message("以下欄位請選擇或輸入:" + ret);
		} else {
			int result = showConfirmDialog("確定送出表單？", "溫馨提醒", 0);
			if (result != 1) {
				// 產生單號
				String uuid = new AddUtil().getUUID(cdo, getTalk());

				// 內建新增功能 需將資料塞進去表單才行
				setValue(cdo.getTablePKName(), uuid);
				// confirm = true 控制是否真的送出
				if (confirm) {
					// 觸發Dmaker內建的新增鈕來送出表單
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	public void detailPage() throws SQLException, Exception {

		// 塞入主檔資料
		String key = getValue("QUERY_LIST.PNO");

		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(key);
		String[][] allColumns = cdo.getTableAllColumn();
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], ret[0][i]);
			}
		} else {
			message("發生錯誤，找不到此表單資料！");
		}
	}

	public void buildSysFormObj() throws SQLException, Exception {

		if (this.cdo == null || getTalk() == null) {
			this.cdo = new CommonDataObj(getUser(), getTalk(), "SAMPLE_EVALUATION", "PNO", "APPLICANT");
		}

	}

	public String getActionName(String Name) {

		Name = Name.toUpperCase();
		if ("[FORM INIT] ".equals(Name) || "[FORM INIT] QUERYPAGE".equals(Name)) {
			return "QUERY_PAGE_INIT";
		} else if ("[FORM INIT] ADDPAGE".equals(Name)) {
			return "ADD_PAGE_INIT";
		} else if (Name.startsWith("[FORM INIT] [FLOW]")) {

			if ("[FORM INIT] [FLOW].待處理".equals(Name)) {
				return "PENING_PAGE_INIT";
			} else {
				return "FLOW_PAGE_INIT";
			}

		} else if (Name.startsWith("[FORM INIT] DETAILPAGE")) {
			return "DETAIL_PAGE_INIT";
		} else if (Name.startsWith("[FORM INIT] SIGNFLOWHISTORY")) {

			return "SIGN_FLOW_HISTORY_PAGE_INIT";
		}
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	public void UIHide() {
		// 隱藏Dmaker底層新增按鈕
		addScript(UIHidderString.hideDmakerAddButton());

		// 隱藏上方標籤
		addScript(UIHidderString.hideDmakerFlowPanel());
		// 隱藏詳細詳細列表功能
		addScript(UIHidderString.hideFlowButtonDetail());

	}

	/**
	 * 載入有時間差問題 無法直接用addScript使用Jquery 直覺用法 在UI拉字符將引用標籤貼上後, 方法寫在按鈕內,如有onLoad事件,
	 * 一樣寫在字符讓表單讀取到就自動執行.
	 */
	public void importJquery() {
		addScript("var script = document.createElement('script');"
				+ "script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';"
				+ "script.type = 'text/javascript';" + "document.getElementsByTagName('head')[0].appendChild(script);");

	}

	public String arrayToString(String[] a) {
		return Arrays.toString(a).replace("[", "").replace("]", "");

	}

	public String getCheckFlowStatus(String ownPno) {
		String sql = "SELECT F_INP_STAT FROM SAMPLE_EVALUATION_CHECK_FLOWC WHERE OWN_PNO='" + ownPno + "'";
		String[][] ret = null;
		try {
			ret = getTalk().queryFromPool(sql);
			if (ret.length == 0) {
				// 如果沒有請驗流程視為請驗已結案
				return "未進行";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret[0][0];
	}

	/**
	 * 對簽核狀態做顯示加工
	 * 
	 * @param queryResults          查詢結果 String[][]
	 * @param viewFieldOfResultList 查詢結果顯示欄位ArrayList
	 * @param c                     Controller
	 * @return
	 * @throws Throwable
	 */
	public String[][] getQueryResultAfterProcess(String[][] queryResults, ArrayList<String> viewFieldOfResultList)
			throws Throwable {
		// 找出UUID與簽核狀態的Index
		int uuid_index = -1;// UUID的Index
		int sign_flow_status_index = -1;// 簽核狀態的Index
		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
			if (viewFieldOfResultList.get(i).toUpperCase().equals(cdo.getTablePKName())) {
				uuid_index = i;
			} else if (viewFieldOfResultList.get(i).contains("'簽核狀態'")) {
				sign_flow_status_index = i;
			}
		}

		for (int i = 0; i < queryResults.length; i++) {
			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();
			String mainFlowStatus = queryResults[i][sign_flow_status_index].trim();
			// 子流程和主流程都結案 在查詢結果表格的簽核狀態才顯示"已生效"
			if ((mainFlowStatus.equals("結案") || mainFlowStatus.equals("歸檔")) && checkFlowStatus.equals("結案")) {
				queryResults[i][sign_flow_status_index] = "<font color=blue>(已生效)</font>";
			} else {
				// 子或主流程有一方還未結案的顯示邏輯

				// 主流程簽核人取得
				Vector<String> people = getApprovablePeople(getFunctionName(),
						"a." + viewFieldOfResultList.get(uuid_index) + "='" + queryResults[i][0] + "'");
				StringBuffer sb = new StringBuffer();
				if (people != null) {
					if (people.size() != 0) {
						sb.append("-(");
						for (int j = 0; j < people.size(); j++) {
							if (j != 0)
								sb.append(",");
							String id1 = (String) people.elementAt(j);
							String name1 = getName(id1);
							sb.append(name1 + "-" + id1);
						}
						sb.append(")");
					}
				}
				people = null;
				// 子流程簽核人取得
				people = getApprovablePeople(getFunctionName() + "_請驗流程",
						"a." + "OWN_" + viewFieldOfResultList.get(uuid_index) + "='" + queryResults[i][0] + "CHECK'");
				StringBuffer subsb = new StringBuffer();
				if (people != null) {
					if (people.size() != 0) {
						subsb.append("-(");
						for (int j = 0; j < people.size(); j++) {
							if (j != 0)
								subsb.append(",");
							String id1 = (String) people.elementAt(j);
							String name1 = getName(id1);
							subsb.append(name1 + "-" + id1);
						}
						subsb.append(")");
					}
				}

				queryResults[i][sign_flow_status_index] = "<font color=red>簽核中</font>【主流程:" + mainFlowStatus
						+ sb.toString() + "】" + "【請驗流程:" + checkFlowStatus + subsb.toString() + "】";
			}
		}

		return queryResults;
	}

}
