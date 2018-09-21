package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.ysp.util.CommonUtil;

import jcx.db.talk;
import jcx.jform.hproc;
import jcx.util.datetime;

/**
 * 新表單建立一新類別直接繼承<br>
 * 實作各頁面的處理即可<br>
 * 
 * 
 *
 */
public abstract class BaseFormOnload {

	hproc c = null;

	public BaseFormOnload(hproc c) {
		this.c = c;
	}

	/**
	 * <h1>啟動表單載入自動化處理</1> 查詢頁面處理方法 doQueryPageProcess 頁面名稱固定為QueryPage<br>
	 * 新增頁面處理方法 doAddPageProcess 頁面名稱固定為AddPage<br>
	 * 待處理頁面處理方法 doPendingPageProcess 頁面名稱固定為PendingPage<br>
	 * 明細頁面處理方法 doDetailPageProcess 頁面名稱固定為DetailPage<br>
	 * 流程簽核頁面處理方法 doFlowPageProcess 不須指定固定名稱<br>
	 * 
	 * @return
	 * @throws Throwable
	 */

	/**
	 * <b>執行查詢頁面載入後的處理程序</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：執行查詢頁面載入後的處理程序<br>
	 * 開發人：51884
	 */
	protected abstract void doQueryPageProcess() throws SQLException, Exception;

	/**
	 * <b>執行新增頁面載入後的處理程序</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：執行新增頁面載入後的處理程序<br>
	 * 開發人：51884
	 */
	protected abstract void doAddPageProcess() throws SQLException, Exception;

	/**
	 * <b>執行流程頁面載入後的處理程序</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：執行流程頁面載入後的處理程序<br>
	 * 開發人：51884
	 */
	protected abstract void doFlowPageProcess() throws SQLException, Exception;

	/**
	 * <b>執行待處理頁面載入後的處理程序</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：執行待處理頁面載入後的處理程序<br>
	 * 開發人：51884
	 */
	protected abstract void doPendingPageProcess() throws SQLException, Exception;

	/**
	 * <b>執行明細頁面載入後的處理程序</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：執行明細頁面載入後的處理程序<br>
	 * 開發人：51884
	 */
	protected abstract void doDetailPageProcess() throws SQLException, Exception;

	/**
	 * <b>執行其他頁面載入後的處理程序</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：執行其他頁面載入後的處理程序<br>
	 * 開發人：51884
	 */
	protected abstract void doOtherPageProcess() throws SQLException, Exception;

	/**
	 * <b>執行從會辦單連結過來的畫面處理程序</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：執行從會辦單連結過來的畫面處理程序<br>
	 * 開發人：51884
	 */
	protected abstract void doViewProcess() throws SQLException, Exception;

	/**
	 * <b>將欄位設定資料傳入記憶體</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：將欄位設定資料傳入記憶體供其他程式取用，使用get(key)就能取到值<br>
	 * 開發人：51884
	 */
	protected void putFieldSettingIntoMemory() {
		Hashtable allclabels = c.getAllcLabels();
		Enumeration e = allclabels.keys();
		while (e.hasMoreElements()) {
			String key = e.nextElement() + "";
			c.put(key, allclabels.get(key));
		}
	}

	/**
	 * <b>填入申請人基本資料</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：填入申請人公司別、申請日期、員工編號、員工姓名、單位代碼、分機<br>
	 * 欄位名稱必須是CPNYID(REQ_CPNYID)、REQ_DATE、REQ_EMP_ID、REQ_EMP_NAME、REQ_DEP_NO、
	 * REQ_DEP_NAME、REQ_EMP_EXT<br>
	 * 開發人：51884
	 */
	protected void setBaseInfo(String empid) throws SQLException, Exception {
		talk t = c.getTalk();
		String sql = "select cpnyid, hecname, dep_no, ext, dep_name from user_info_view where empid = '" + empid + "' ";
		String[][] ret = t.queryFromPool(sql);
		if (ret.length > 0) {
			String cpnyid = ret[0][0];
			String hecname = ret[0][1];
			String dep_no = ret[0][2];
			String ext = ret[0][3];
			String dep_name = ret[0][4];
			String today = c.getToday("YYYYmmdd");

			c.setValue("REQ_CPNYID", cpnyid);
			c.setValue("CPNYID", cpnyid);
			c.setValue("REQ_EMP_ID", empid);
			c.setValue("REQ_EMP_NAME", hecname);
			c.setValue("REQ_DEP_NO", dep_no);
			c.setValue("REQ_DEP_NAME", dep_name);
			c.setValue("REQ_EMP_EXT", ext);
			c.setValue("REQ_DATE", today);
		}
	}

	/**
	 * <b>顯示退簽通知訊息</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：顯示退簽通知訊息<br>
	 * 開發人：51884
	 */
	protected void showReturnedMessage() throws SQLException, Exception {
		c.addScript("try{showRejectMsg();}catch(e){}");
	}

	/**
	 * <b>如果都沒有待簽核單據則跳回表單首頁</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：利用畫面上的單號欄位判斷是否已經簽完所有單據，如果都簽完便跳回表單首頁<br>
	 * 開發人：51884
	 */
	protected void toEntryView() {
		String uuid = c.getValue("UUID").trim();
		String functionName = c.getFunctionName();
		if ("".equals(uuid)) {
			c.changeForm(functionName);
		}
	}

	/**
	 * <b>填入欄位資料</b><br>
	 * 傳入：無<br>
	 * 回傳：無<br>
	 * 說明：填入欄位資料, 欄位名稱必須設定成跟主檔table欄位名稱一致(大小寫沒差)<br>
	 * 開發人：51884
	 */
	protected void setFieldsData(String uuid, String table) throws SQLException, Exception {
		talk t = c.getTalk();
		String sql = "select * from " + table + " where UUID = '" + uuid + "' ";
		String[][] ret = t.queryFromPool(sql);
		String sql_all_column = "select column_name from INFORMATION_SCHEMA.COLUMNS where table_name= '" + table + "'";
		String[][] ret_all_column = t.queryFromPool(sql_all_column);
		// 塞入主檔資料
		if (ret.length > 0) {
			for (int i = 0; i < ret_all_column.length; i++) {
				c.setValue(ret_all_column[i][0], ret[0][i]);
			}

		} else {
			c.message("發生錯誤，找不到此表單資料！");
		}
	}

	/**
	 * <b>填入表格資料</b><br>
	 * 傳入：表格名稱(String), 單號(String), 表格欄位名稱(ArrayList), 資料庫table名稱(String)<br>
	 * 回傳：無<br>
	 * 說明：根據單號將table資料填入表格<br>
	 * 開發人：51884
	 */
	protected void setTableData(String form, String uuid, ArrayList<String> form_fields, String table)
			throws SQLException, Exception {
		talk t = c.getTalk();
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		if (form_fields.size() > 0) {
			for (int i = 0; i < form_fields.size(); i++) {
				sql.append(form_fields.get(i));
				if (i != form_fields.size() - 1) {
					sql.append(", ");
				}
			}
			sql.append(" from " + table + " where uuid = '" + uuid + "' ");

			try {
				String req_list[][] = t.queryFromPool(sql.toString());
				c.setTableData(form, req_list);
			} catch (SQLException e) {
				String stacktrace = ExceptionUtils.getStackTrace(e);
				System.out.println(stacktrace);
				c.message("將資料填入表格時發生問題<br>" + stacktrace);
			}
		} else {
			c.message("將資料填入表格時發生問題");
		}

	}

	/**
	 * <b>將從SQL抓出來的資料存入HashMap中(僅限一筆資料)</b><br>
	 * 傳入：Table名稱(String), key欄位名稱(String), key值(String)<br>
	 * 回傳：資料(HashMap), 發生錯誤則回傳null<br>
	 * 說明：<br>
	 * 開發人：51884
	 */
	protected HashMap<String, String> queryToMap(String table_name, String key, String key_value)
			throws SQLException, Exception {
		talk t = c.getTalk();
		HashMap<String, String> result = new HashMap<String, String>();
		String sql_main_table = "select * from " + table_name + " where " + key + " = '" + key_value + "' ";
		String[][] ret_main_table = t.queryFromPool(sql_main_table);
		if (ret_main_table.length > 0) {
			String sql_colun_name = "select column_name from INFORMATION_SCHEMA.COLUMNS where table_name= '"
					+ table_name + "' ";
			String[][] ret_colun_name = t.queryFromPool(sql_colun_name);
			if (ret_colun_name.length > 0) {
				for (int i = 0; i < ret_colun_name.length; i++) {
					result.put(ret_colun_name[i][0], ret_main_table[0][i]);
				}
			} else {
				result.put(null, null);
			}
		} else {
			result.put(null, null);
		}

		return result;
	}

	/**
	 * <b>判斷是否為外勤人員</b><br>
	 * 傳入：員編(String)<br>
	 * 回傳：true是外勤人員,false非外勤人員<br>
	 * 說明：判斷是否為外勤人員<br>
	 * 開發人：51884
	 */
	protected boolean isSales(String user) throws SQLException, Exception {
		String sql = "select * from HRUSER where EMPID = '" + user + "' and UTYPE = 'B' ";
		String ret[][] = c.getTalk().queryFromPool(sql);
		if (ret.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * <b>設定聚焦輸入在指定欄位上</b><br>
	 * 傳入：欄位名稱(String)<br>
	 * 回傳：無<br>
	 * 說明：設定聚焦輸入在指定欄位上，方便使用者進入畫面後即可知道要在哪進行輸入<br>
	 * 開發人：51884
	 */
	protected void setFocus(String field) throws SQLException, Exception {
		c.addScript("try{document.getElementById('" + field + "-box-text').focus();}catch(e){}");
	}

	/**
	 * <b>判斷是否為系統管理員</b><br>
	 * 傳入：員編(String)<br>
	 * 回傳：true是系統管理員,false非系統管理員<br>
	 * 說明：判斷是否為系統管理員<br>
	 * 開發人：51884
	 */
	protected boolean isAdmin(String user) throws SQLException, Exception {
		String sql = "select ID from HRUSER_DEPT where DEP_NO = '1001' and ID = '" + user + "' ";
		String[][] ret = c.getTalk().queryFromPool(sql);
		if (ret.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * <b>設定欄位標題背景顏色</b><br>
	 * 傳入：欄位名稱(String), 顏色代碼或名稱(String)<br>
	 * 回傳：無<br>
	 * 說明：設定欄位標題背景顏色<br>
	 * 開發人：51884
	 */
	protected void setCaptionColor(String field, String color) throws Exception {
		c.addScript(
				"try{document.getElementById('" + field + "-caption').style.background = '" + color + "';}catch(e){}");
	}

	/**
	 * <b>根據設定的對應欄位自動載入資料</b><br>
	 * 傳入：Key名稱(String), Key值(String)<br>
	 * 回傳：無<br>
	 * 說明：手動呼叫底層的載入資料方法來載入畫面欄位資料<br>
	 * 開發人：51884
	 */
	protected void loadDetailData(String key, String value) throws Exception {
		Hashtable<String, String> encodeKey = new Hashtable<String, String>();
		encodeKey.put(key, value);

		c.addScript("try{button_action_ajax('', '___emaker_query_list', '" + CommonUtil.encoding(encodeKey)
				+ "', 'TARGET');}catch(e){}");
	}

}
