package oa.SampleEvaluation.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import jcx.db.talk;

/**
 * ���̿�hproc
 * 
 * @author u52116
 *
 */
public class AddUtil {

	/**
	 * 
	 * @param table
	 *            ��ƪ�W��
	 * @param user
	 *            ���u�s��
	 * @param tablePKName
	 *            ��ƪ�pk�W��
	 * @param t
	 *            hproc��talk
	 * @return �渹=empid+�y����
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
	 * <font size='3'><b>�ˬd���O�_�ť�</b></font><br>
	 * key = ���W�� <br>
	 * value = �a������(key="value")�M�����D(key="title")��MAP<br>
	 * <br>
	 * �ǤJ�G�n�ˬd�����(Map<String, Map<String, String>>)<br>
	 * �^�ǡG���饼�骺���W�� (ArrayLlist)<br>
	 * �����G����ˮ֡A�u�|�ˬd�O�_�ťաA�^�ǩҦ��ť������D<br>
	 * �}�o�H�G52116
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
	 * �^�Ǳa������(key="value")�M�����D(key="title")��MAP
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
