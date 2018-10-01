package oa.SampleEvaluation;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import oa.SampleEvaluation.enums.*;
import com.ysp.service.BaseService;
import com.ysp.service.MailService;

import jcx.jform.hproc;
import jcx.util.convert;
import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.MainQuery;
import oa.SampleEvaluation.common.UIHidderString;
import oa.SampleEvaluation.common.UserData;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import com.ysp.field.Mail;

/**
 * 嘗試可測試寫法
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	public String functionName = "樣品評估申請作業";
	public boolean isTest = false;

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// importJquery();
		this.cdo = new CommonDataObj(getUser(), getTalk(), "SAMPLE_EVALUATION", "PNO", "APPLICANT");
		String actionObjName = getActionName(getName());

		// 按鈕動作處理進入點
		switch (Actions.valueOf(actionObjName.trim())) {

		case QUERY_CLICK:
			setCommonObiQueryData();
			MainQuery mquery = new MainQuery(cdo);
			String[][] list = mquery.getQueryResult();
			if (list.length > 0) {
				list = getQueryResultAfterProcess(list, cdo.getQueryResultShowTableFieldList());
			} else {
				message("查無紀錄");
			}
			setTableData("QUERY_LIST", list);

			break;

		case SAVE_CLICK:
			doSave();

			break;

		case SHOW_DETAIL_CLICK:// 這個動作比較尷尬 屬於載入畫面但卻是按鈕發動

			detailPage();
			break;

		default:

			break;
		}
		return null;

	}

	// CommonObi
	public void setCommonObiQueryData() {
		cdo.setTableApplicantFieldName("APPLICANT");
		cdo.setTableAppDateFieldName("APP_DATE");

		cdo.setQueryFieldNameEmpid("QUERY_EMP_ID");
		cdo.setQueryFieldNameBillId("QUERY_PNO");
		cdo.setQueryFieldNameStartAppDate("QUERY_REQ_SDATE");
		cdo.setQueryFieldNameEndAppDate("QUERY_REQ_EDATE");
		cdo.setQueryFieldNameFlowStatus("r_status");

		cdo.setQueryFieldValueEmpid(getValue("QUERY_EMP_ID"));
		cdo.setQueryFieldValueBillId(getValue("QUERY_PNO"));
		cdo.setQueryFieldValueStartAppDate(getValue("QUERY_REQ_SDATE"));
		cdo.setQueryFieldValueEndAppDate(getValue("QUERY_REQ_EDATE"));
		cdo.setQueryFieldValueFlowStatus(getValue("r_status"));

		cdo.setFunctionName(getFunctionName());

		ArrayList<String> flist = new ArrayList<String>();
		flist.add("PNO");
		flist.add("APPLICANT");
		flist.add("APP_TYPE");
		flist.add("URGENCY");
		flist.add("APP_DATE");
		flist.add("'簽核狀態'");
		flist.add("'明細'");
		flist.add("'簽核紀錄'");

		cdo.setQueryResultShowTableFieldList(flist);
	}

	public void doSave() throws Throwable {
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
					// TODO 寄給誰處理一下
					// sendMail();
					// 觸發Dmaker內建的新增鈕來送出表單
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	/**
	 * 目前為免洗方法
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */

	public void detailPage() throws SQLException, Exception {

		// 塞入主檔資料
		String key = getValue("QUERY_LIST.PNO");

		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(key);
		String[][] allColumns = cdo.getTableAllColumn();
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], ret[0][i]);
			}
			FormInitUtil init = new FormInitUtil(this);
			init.doDetailPageProcess();
			addScript(UIHidderString.hideDmakerAddButton());
		} else {
			message("發生錯誤，找不到此表單資料！");
		}
	}

	public String getActionName(String Name) {

		Name = Name.toUpperCase();
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

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

	public String getCheckFlowStatus(String ownPno) {
		String sql = "SELECT F_INP_STAT FROM SAMPLE_EVALUATION_CHECK_FLOWC WHERE OWN_PNO='" + ownPno + "'";
		String[][] ret = null;
		try {
			ret = getTalk().queryFromPool(sql);
			if (ret.length == 0) {
				// 如果沒有請驗流程視為請驗已結案
				return "無";
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

		int sign_flow_status_index = -1;// 簽核狀態的Index
		for (int i = 0; i < viewFieldOfResultList.size(); i++) {
			if (viewFieldOfResultList.get(i).contains("'簽核狀態'")) {
				sign_flow_status_index = i;
			}
		}

		for (int i = 0; i < queryResults.length; i++) {
			// 取得子流程目前簽核狀態
			String checkFlowStatus = getCheckFlowStatus(queryResults[i][0] + "CHECK").trim();

			// 取得主流程目前簽核狀態
			String mainFlowStatus = queryResults[i][sign_flow_status_index].trim();

			// 如果子流程和主流程都結案 在查詢結果表格的簽核狀態才顯示"已生效"
			if ((mainFlowStatus.equals("結案") || mainFlowStatus.equals("歸檔"))
					&& (checkFlowStatus.equals("結案") || checkFlowStatus.equals("無"))) {
				queryResults[i][sign_flow_status_index] = "<font color=blue>(已生效)</font>";
			}
			// 如果子流程和主流程有一方未結案 則進行加工處理
			else {
				queryResults[i][sign_flow_status_index] = "<font color=red>簽核中</font>" + "【主流程:" + mainFlowStatus
						+ getCurrentFlowGateAndApprover(cdo.getTablePKName(), queryResults[i][0]) + "】" + "【請驗流程:"
						+ checkFlowStatus
						+ getCurrentFlowGateAndApprover("OWN_" + cdo.getTablePKName(), queryResults[i][0] + "CHECK")
						+ "】";
			}
		}

		return queryResults;
	}

	/**
	 * 取得目前簽核關卡名稱與簽核人員資料字串 EX:"-(關卡名稱-簽核人1,簽核人2..)"
	 * 
	 * @param pkName  資料表pk欄位名稱
	 * @param pkValue 資料表pk值
	 * @return
	 */
	public String getCurrentFlowGateAndApprover(String pkName, String pkValue) {
		Vector<String> people = getApprovablePeople(getFunctionName(), "a." + pkName + "='" + pkValue + "'");
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

		return sb.toString();
	}

}
