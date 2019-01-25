package oa.SampleEvaluation.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ysp.service.BaseService;

/**
 * ���̿�hproc
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
	 * ����ˮ֡A�u�|�ˬd�O�_�ťաA�^�ǩҦ��ť������D<br>
	 * 
	 */
	public List<String> emptyCheck(Map<String, String> fieldMap) {

		ArrayList<String> ret = new ArrayList<String>();
		String value = null;
		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
			value = service.getValue(entry.getKey()).trim();
			if (value != null && value.equals("")) {
				ret.add(fieldMap.get(entry.getKey()));
			}

		}
		return ret;
	}

	public String getUUID(String tableName) throws Exception {

		String uuid = "";
		String appdate = service.getValue("APP_DATE").trim().substring(0, 4);
		String table = tableName;
		String sql = "select max(PNO) from " + table + " WHERE PNO like '" + appdate + "%'";
		String[][] ret = service.getTalk().queryFromPool(sql);

		if ("".equals(ret[0][0])) {
			uuid = appdate + "0001";
		} else {
			uuid = String.valueOf(Long.parseLong(ret[0][0].trim()) + 1);
		}

		return uuid;
	}

}
