package oa.SampleEvaluation.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.controller.HprocImpl;

/**
 * 起單時會用到的動作
 * 
 * @author u52116
 *
 */
public class AddUtil {

	private HprocImpl service;

	public AddUtil() {

	}

	public AddUtil(HprocImpl service) {
		this.service = service;
	}

	/**
	 * 欄位檢核，只會檢查是否空白，回傳所有空白欄位標題<br>
	 * 
	 * @param fieldMap
	 * @return 
	 * @return 回傳List
	 * @throws Exception
	 */
	public void emptyCheck(Map<String, String> fieldMap) throws Exception {

		ArrayList<String> ret = new ArrayList<String>();
		String value = null;
		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
			value = service.getValue(entry.getKey()).trim();
			if (value != null && value.equals("")) {
				ret.add(fieldMap.get(entry.getKey()));
			}

		}
		if (ret != null && ret.size() > 0) {
			service.message("以下欄位請做選擇或輸入:" + ret);
		} else {
			int result = service.showConfirmDialog("確定送出表單？", "溫馨提醒", 0);
			if (result != 1) {
				// 產生單號
				String uuid = getUUID(service.getTableName());

				// DMAKER 內建ADD功能 需將資料塞進去表單欄位才吃的到
				service.setValue("PNO", uuid);
				service.fileItemSetChecker();
				// confirm = true 控制是否真的送出
				service.addScript("document.getElementById('em_add_button-box').click();");

			}

		}
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
