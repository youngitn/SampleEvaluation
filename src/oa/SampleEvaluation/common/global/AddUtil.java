package oa.SampleEvaluation.common.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ysp.service.BaseService;

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

	public String getUUID(CommonDataObj cdo) throws Exception {

		String uuid = "";
		String tablePKName = cdo.getTablePKName();
		String table = cdo.getTableName();
		String sql = "select max(" + tablePKName + ") from " + table;
		String[][] ret = service.getTalk().queryFromPool(sql);
		String appdate = service.getValue("APP_DATE").trim().substring(0, 4);
		if ("".equals(ret[0][0])) {
			uuid = appdate + "0001";
		} else {
			uuid = String.valueOf(Long.parseLong(ret[0][0].trim()) + 1);
		}

		return uuid;
	}

}
