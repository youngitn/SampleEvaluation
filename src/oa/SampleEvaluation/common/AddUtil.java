package oa.SampleEvaluation.common;

import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ysp.service.BaseService;

import jcx.db.talk;

/**
 * 不依賴hproc
 * 
 * @author u52116
 *
 */
public class AddUtil {

	private BaseService service;

	public AddUtil() {

	}

	public AddUtil(BaseService service) {
		this.service = service;
	}

	/**
	 * 
	 * @param table       資料表名稱
	 * @param user        員工編號
	 * @param tablePKName 資料表pk名稱
	 * @param t           hproc的talk
	 * @return 單號=empid+流水號
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getUUID(String table, String user, String tablePKName, talk t) throws Exception {

		String uuid = "";

		String sql = "select max(" + tablePKName + ") from " + table + " where " + tablePKName + " like '" + user
				+ "%'";
		String[][] ret = t.queryFromPool(sql);

		if ("".equals(ret[0][0])) {
			uuid = user + "00001";
		} else {
			String m_uuid = ret[0][0].trim();
			uuid = user + String.format("%05d", (Long.parseLong(m_uuid.replace(user, "")) + 1));
		}

		return uuid;
	}

	/**
	 * 
	 * 欄位檢核，只會檢查是否空白，回傳所有空白欄位標題<br>
	 * 
	 */
	public List<String> emptyCheck(Map<String, String> fieldMap) {

		ArrayList<String> ret = new ArrayList<String>();

		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
			String value = service.getValue(entry.getKey()).trim();
			if (value != null && value.equals("")) {
				ret.add(fieldMap.get(entry.getKey()));
			}

		}
		return ret;
	}

	/**
	 * 回傳帶有欄位值(key="value")和欄位標題(key="title")的MAP
	 * 
	 * @param value
	 * @param title
	 * @return
	 */
	public static Map<String, String> addValidateField(String value, String title) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("value", value.trim());
		m.put("title", title.trim());

		return m;
	}

	public String getUUID(CommonDataObj cdo) throws Exception {

		String uuid = "";
		String tablePKName = cdo.getTablePKName();
		String table = cdo.getTableName();
		String user = service.getValue(cdo.getTableApplicantFieldName());
		String sql = "select max(" + tablePKName + ") from " + table + " where " + tablePKName + " like '" + user
				+ "%'";
		String[][] ret = service.getTalk().queryFromPool(sql);

		if ("".equals(ret[0][0])) {
			uuid = user + "00001";
		} else {
			String m_uuid = ret[0][0].trim().replace(user, "");
			uuid = user + String.format("%05d", (Long.parseLong(m_uuid) + 1));
		}

		return uuid;
	}

}
