package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import jcx.db.talk;

/**
 * 不依賴hproc
 * 
 * @author u52116
 *
 */
public class AddUtil {

	/**
	 * 
	 * @param table
	 *            資料表名稱
	 * @param user
	 *            員工編號
	 * @param tablePKName
	 *            資料表pk名稱
	 * @param t
	 *            hproc的talk
	 * @return 單號=empid+流水號
	 * @throws SQLException
	 * @throws Exception
	 */
	public  String getUUID(String table, String user, String tablePKName,
			talk t) throws SQLException, Exception {
		// talk t = c.getTalk();

		String uuid = "";

		String sql = "select max(" + tablePKName + ") from " + table
				+ " where " + tablePKName + " like '" + user + "%'";
		String[][] ret = t.queryFromPool(sql);

		if ("".equals(ret[0][0])) {
			uuid = user + "00001";
		} else {
			String m_uuid = ret[0][0].trim();
			uuid = user
					+ String.format("%05d",
							(Long.parseLong(m_uuid.replace(user, "")) + 1));
		}

		return uuid;
	}

	/**
	 * <font size='3'><b>檢查欄位是否空白</b></font><br>
	 * key = 欄位名稱 <br>
	 * value = 帶有欄位值(key="value")和欄位標題(key="title")的MAP<br>
	 * <br>
	 * 傳入：要檢查的欄位(Map<String, Map<String, String>>)<br>
	 * 回傳：必輸未輸的欄位名稱 (ArrayLlist)<br>
	 * 說明：欄位檢核，只會檢查是否空白，回傳所有空白欄位標題<br>
	 * 開發人：52116
	 */
	public static ArrayList<String> emptyCheck(
			Map<String, Map<String, String>> fieldMap) {

		ArrayList<String> ret = new ArrayList<String>();
		Iterator<Entry<String, Map<String, String>>> entries = fieldMap
				.entrySet().iterator();

		while (entries.hasNext()) {
			Entry<String, Map<String, String>> entry = entries.next();
			Map<String, String> fieldInfo = entry.getValue();

			if (fieldInfo.get("value") == null
					|| fieldInfo.get("value").isEmpty()) {
				ret.add(fieldInfo.get("title"));
			}
			// ...
		}

		return ret;
	}

	/**
	 * 回傳帶有欄位值(key="value")和欄位標題(key="title")的MAP
	 * @param value
	 * @param title
	 * @return
	 */
	public static Map<String, String> addValidateField(String value,
			String title) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("value", value.trim());
		m.put("title", title.trim());

		return m;
	}

	public String getUUID(CommonDataObj cdo,talk t) throws SQLException, Exception {
		// talk t = c.getTalk();

		String uuid = "";
		String tablePKName = cdo.getTablePKName();
		String table = cdo.getTableName();
		String user = cdo.getUserdata().getEmpid();
		String sql = "select max(" + tablePKName + ") from " + table
				+ " where " + tablePKName + " like '" + user + "%'";
		String[][] ret = t.queryFromPool(sql);

		if ("".equals(ret[0][0])) {
			uuid = user + "00001";
		} else {
			String m_uuid = ret[0][0].trim();
			uuid = user
					+ String.format("%05d",
							(Long.parseLong(m_uuid.replace(user, "")) + 1));
		}

		return uuid;
	}

}
