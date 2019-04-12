package oa.SampleEvaluation.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.controller.HprocImpl;

/**
 * �_��ɷ|�Ψ쪺�ʧ@.
 *
 * @author u52116
 */
public class AddUtil {

	/** The service. */
	private HprocImpl service;

	/**
	 * Instantiates a new adds the util.
	 */
	public AddUtil() {

	}

	/**
	 * Instantiates a new adds the util.
	 *
	 * @param service [HprocImpl]
	 */
	public AddUtil(HprocImpl service) {
		this.service = service;
	}

	/**
	 * �ˬd�������&�x�s���<br>.
	 *
	 * @param fieldMap [Map<String,String>]
	 * @return �^��List
	 * @throws Exception the exception
	 */
	public void doAdd(Map<String, String> fieldMap) throws Exception {

		ArrayList<String> ret = getValidateEmptyField(fieldMap);

		if (ret != null && ret.size() > 0) {
			service.message("�H�U���а���ܩο�J:" + ret);
		} else {
			int result = service.showConfirmDialog("�T�w�e�X���H", "���ɴ���", 0);
			if (result != 1) {
				// ���ͳ渹
				String uuid = getUUID(service.getTableName());

				// DMAKER ����ADD�\�� �ݱN��ƶ�i�h������~�Y����
				service.setValue("PNO", uuid);
				service.fileItemSetChecker();
				
				// confirm = true ����O�_�u���e�X
				service.addScript("document.getElementById('em_add_button-box').click();");

			}

		}
	}

	/**
	 * Gets the ValidateEmptyField.
	 *
	 * @param fieldMap [Map<String,String>]
	 * @return [ArrayList<String>]
	 */
	public ArrayList<String> getValidateEmptyField(Map<String, String> fieldMap) {
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

	/**
	 * Gets the UUID.
	 *
	 * @param tableName [String]
	 * @return [String]
	 * @throws Exception the exception
	 */
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
