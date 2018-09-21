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
 * �s���إߤ@�s���O�����~��<br>
 * ��@�U�������B�z�Y�i<br>
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
	 * <h1>�Ұʪ����J�۰ʤƳB�z</1> �d�߭����B�z��k doQueryPageProcess �����W�٩T�w��QueryPage<br>
	 * �s�W�����B�z��k doAddPageProcess �����W�٩T�w��AddPage<br>
	 * �ݳB�z�����B�z��k doPendingPageProcess �����W�٩T�w��PendingPage<br>
	 * ���ӭ����B�z��k doDetailPageProcess �����W�٩T�w��DetailPage<br>
	 * �y�{ñ�֭����B�z��k doFlowPageProcess �������w�T�w�W��<br>
	 * 
	 * @return
	 * @throws Throwable
	 */

	/**
	 * <b>����d�߭������J�᪺�B�z�{��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G����d�߭������J�᪺�B�z�{��<br>
	 * �}�o�H�G51884
	 */
	protected abstract void doQueryPageProcess() throws SQLException, Exception;

	/**
	 * <b>����s�W�������J�᪺�B�z�{��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G����s�W�������J�᪺�B�z�{��<br>
	 * �}�o�H�G51884
	 */
	protected abstract void doAddPageProcess() throws SQLException, Exception;

	/**
	 * <b>����y�{�������J�᪺�B�z�{��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G����y�{�������J�᪺�B�z�{��<br>
	 * �}�o�H�G51884
	 */
	protected abstract void doFlowPageProcess() throws SQLException, Exception;

	/**
	 * <b>����ݳB�z�������J�᪺�B�z�{��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G����ݳB�z�������J�᪺�B�z�{��<br>
	 * �}�o�H�G51884
	 */
	protected abstract void doPendingPageProcess() throws SQLException, Exception;

	/**
	 * <b>������ӭ������J�᪺�B�z�{��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G������ӭ������J�᪺�B�z�{��<br>
	 * �}�o�H�G51884
	 */
	protected abstract void doDetailPageProcess() throws SQLException, Exception;

	/**
	 * <b>�����L�������J�᪺�B�z�{��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G�����L�������J�᪺�B�z�{��<br>
	 * �}�o�H�G51884
	 */
	protected abstract void doOtherPageProcess() throws SQLException, Exception;

	/**
	 * <b>����q�|���s���L�Ӫ��e���B�z�{��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G����q�|���s���L�Ӫ��e���B�z�{��<br>
	 * �}�o�H�G51884
	 */
	protected abstract void doViewProcess() throws SQLException, Exception;

	/**
	 * <b>�N���]�w��ƶǤJ�O����</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G�N���]�w��ƶǤJ�O����Ѩ�L�{�����ΡA�ϥ�get(key)�N������<br>
	 * �}�o�H�G51884
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
	 * <b>��J�ӽФH�򥻸��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G��J�ӽФH���q�O�B�ӽФ���B���u�s���B���u�m�W�B���N�X�B����<br>
	 * ���W�٥����OCPNYID(REQ_CPNYID)�BREQ_DATE�BREQ_EMP_ID�BREQ_EMP_NAME�BREQ_DEP_NO�B
	 * REQ_DEP_NAME�BREQ_EMP_EXT<br>
	 * �}�o�H�G51884
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
	 * <b>��ܰhñ�q���T��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G��ܰhñ�q���T��<br>
	 * �}�o�H�G51884
	 */
	protected void showReturnedMessage() throws SQLException, Exception {
		c.addScript("try{showRejectMsg();}catch(e){}");
	}

	/**
	 * <b>�p�G���S����ñ�ֳ�ګh���^��歺��</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G�Q�εe���W���渹���P�_�O�_�w�gñ���Ҧ���ڡA�p�G��ñ���K���^��歺��<br>
	 * �}�o�H�G51884
	 */
	protected void toEntryView() {
		String uuid = c.getValue("UUID").trim();
		String functionName = c.getFunctionName();
		if ("".equals(uuid)) {
			c.changeForm(functionName);
		}
	}

	/**
	 * <b>��J�����</b><br>
	 * �ǤJ�G�L<br>
	 * �^�ǡG�L<br>
	 * �����G��J�����, ���W�٥����]�w����D��table���W�٤@�P(�j�p�g�S�t)<br>
	 * �}�o�H�G51884
	 */
	protected void setFieldsData(String uuid, String table) throws SQLException, Exception {
		talk t = c.getTalk();
		String sql = "select * from " + table + " where UUID = '" + uuid + "' ";
		String[][] ret = t.queryFromPool(sql);
		String sql_all_column = "select column_name from INFORMATION_SCHEMA.COLUMNS where table_name= '" + table + "'";
		String[][] ret_all_column = t.queryFromPool(sql_all_column);
		// ��J�D�ɸ��
		if (ret.length > 0) {
			for (int i = 0; i < ret_all_column.length; i++) {
				c.setValue(ret_all_column[i][0], ret[0][i]);
			}

		} else {
			c.message("�o�Ϳ��~�A�䤣�즹����ơI");
		}
	}

	/**
	 * <b>��J�����</b><br>
	 * �ǤJ�G���W��(String), �渹(String), ������W��(ArrayList), ��Ʈwtable�W��(String)<br>
	 * �^�ǡG�L<br>
	 * �����G�ھڳ渹�Ntable��ƶ�J���<br>
	 * �}�o�H�G51884
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
				c.message("�N��ƶ�J���ɵo�Ͱ��D<br>" + stacktrace);
			}
		} else {
			c.message("�N��ƶ�J���ɵo�Ͱ��D");
		}

	}

	/**
	 * <b>�N�qSQL��X�Ӫ���Ʀs�JHashMap��(�ȭ��@�����)</b><br>
	 * �ǤJ�GTable�W��(String), key���W��(String), key��(String)<br>
	 * �^�ǡG���(HashMap), �o�Ϳ��~�h�^��null<br>
	 * �����G<br>
	 * �}�o�H�G51884
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
	 * <b>�P�_�O�_���~�ԤH��</b><br>
	 * �ǤJ�G���s(String)<br>
	 * �^�ǡGtrue�O�~�ԤH��,false�D�~�ԤH��<br>
	 * �����G�P�_�O�_���~�ԤH��<br>
	 * �}�o�H�G51884
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
	 * <b>�]�w�E�J��J�b���w���W</b><br>
	 * �ǤJ�G���W��(String)<br>
	 * �^�ǡG�L<br>
	 * �����G�]�w�E�J��J�b���w���W�A��K�ϥΪ̶i�J�e����Y�i���D�n�b���i���J<br>
	 * �}�o�H�G51884
	 */
	protected void setFocus(String field) throws SQLException, Exception {
		c.addScript("try{document.getElementById('" + field + "-box-text').focus();}catch(e){}");
	}

	/**
	 * <b>�P�_�O�_���t�κ޲z��</b><br>
	 * �ǤJ�G���s(String)<br>
	 * �^�ǡGtrue�O�t�κ޲z��,false�D�t�κ޲z��<br>
	 * �����G�P�_�O�_���t�κ޲z��<br>
	 * �}�o�H�G51884
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
	 * <b>�]�w�����D�I���C��</b><br>
	 * �ǤJ�G���W��(String), �C��N�X�ΦW��(String)<br>
	 * �^�ǡG�L<br>
	 * �����G�]�w�����D�I���C��<br>
	 * �}�o�H�G51884
	 */
	protected void setCaptionColor(String field, String color) throws Exception {
		c.addScript(
				"try{document.getElementById('" + field + "-caption').style.background = '" + color + "';}catch(e){}");
	}

	/**
	 * <b>�ھڳ]�w���������۰ʸ��J���</b><br>
	 * �ǤJ�GKey�W��(String), Key��(String)<br>
	 * �^�ǡG�L<br>
	 * �����G��ʩI�s���h�����J��Ƥ�k�Ӹ��J�e�������<br>
	 * �}�o�H�G51884
	 */
	protected void loadDetailData(String key, String value) throws Exception {
		Hashtable<String, String> encodeKey = new Hashtable<String, String>();
		encodeKey.put(key, value);

		c.addScript("try{button_action_ajax('', '___emaker_query_list', '" + CommonUtil.encoding(encodeKey)
				+ "', 'TARGET');}catch(e){}");
	}

}
