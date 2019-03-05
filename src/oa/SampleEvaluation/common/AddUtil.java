package oa.SampleEvaluation.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.controller.HprocImpl;

/**
 * �_��ɷ|�Ψ쪺�ʧ@
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
	 * ����ˮ֡A�u�|�ˬd�O�_�ťաA�^�ǩҦ��ť������D<br>
	 * 
	 * @param fieldMap
	 * @return 
	 * @return �^��List
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
