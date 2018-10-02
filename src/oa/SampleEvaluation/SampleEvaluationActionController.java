package oa.SampleEvaluation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oa.SampleEvaluation.enums.*;
import com.ysp.service.BaseService;

import jcx.jform.hproc;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.MainQuery;
import oa.SampleEvaluation.common.UIHidderString;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;

/**
 * ���եi���ռg�k
 * 
 * @author u52116
 *
 */
public class SampleEvaluationActionController extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	public String functionName = "�˫~�����ӽЧ@�~";
	public boolean isTest = false;
	BaseService service;

	@Override
	public String action(String arg0) throws Throwable {

		// for enum switch
		String actionObjName = getActionName(getName());

		service = new BaseService(this);

		this.cdo = new CommonDataObj(service, "PNO", "APPLICANT");

		// ���s�ʧ@�B�z�i�J�I
		switch (Actions.valueOf(actionObjName.trim())) {
		case QUERY_CLICK:
			// go
			doQuery();
			break;
		case SAVE_CLICK:
			doSave();
			break;
		case SHOW_DETAIL_CLICK:// �o�Ӱʧ@������� �ݩ���J�e�����o�O���s�o��
			readAndFillData(getValue("QUERY_LIST.PNO"));
			break;
		default:
			break;
		}
		return null;

	}

	public void doSave() throws Throwable {

		// ��������� (���W,�����D)
		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("APPLICANT", "�ӽФH");
		fieldMap.put("APP_TYPE", "�ӽ�����");
		fieldMap.put("RECEIPT_UNIT", "���z���");
		fieldMap.put("URGENCY", "�止��");

		// �s�W����cdo���B�~��L���
		AddUtil addUtil = new AddUtil(service);
		ArrayList<String> ret = addUtil.emptyCheck(fieldMap);
		if (ret != null && ret.size() > 0) {
			message("�H�U���а���ܩο�J:" + ret);
		} else {
			int result = showConfirmDialog("�T�w�e�X���H", "���ɴ���", 0);
			if (result != 1) {
				// ���ͳ渹
				String uuid = addUtil.getUUID(cdo);

				// DMAKER ����ADD�\�� �ݱN��ƶ�i�h������~�Y����
				setValue(cdo.getTablePKName(), uuid);
				// confirm = true ����O�_�u���e�X
				if (confirm) {
					// Ĳ�oDmaker���ت��s�W�s�Ӱe�X���
					addScript("document.getElementById('em_add_button-box').click();");

				}
			}

		}

	}

	public void doQuery() throws Throwable {
		ArrayList<String> flist = new ArrayList<String>();
		flist.add("PNO");
		flist.add("APPLICANT");
		flist.add("APP_TYPE");
		flist.add("URGENCY");
		flist.add("APP_DATE");
		flist.add("'ñ�֪��A'");
		flist.add("'����'");
		flist.add("'ñ�֬���'");

		cdo.setQueryResultShowTableFieldList(flist);

		MainQuery mquery = new MainQuery(cdo);
		String[][] list = mquery.getQueryResult();
		// TODO ���ʨ�MainQuery��
		if (list == null || list.length <= 0) {
			message("�d�L����");
		}
		setTableData("QUERY_LIST", list);
	}

	/**
	 * �ثe���K�~��k TODO ,�渹 �J�Ѽƥi����
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */

	public void readAndFillData(String key) throws SQLException, Exception {

		// ��J�D�ɸ��
//		String key = getValue("QUERY_LIST.PNO");

		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(key);
		String[][] allColumns = cdo.getTableAllColumn();
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], ret[0][i]);
			}
			FormInitUtil init = new FormInitUtil(this);
			init.doDetailPageProcess();
			addScript(UIHidderString.hideDmakerAddButton());
		} else {
			message("�o�Ϳ��~�A�䤣�즹����ơI");
		}
	}

	public String getActionName(String Name) {

		Name = Name.toUpperCase();
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	/**
	 * ���J���ɶ��t���D �L�k������addScript�ϥ�Jquery ��ı�Ϊk �bUI�Ԧr�űN�ޥμ��ҶK�W��, ��k�g�b���s��,�p��onLoad�ƥ�,
	 * �@�˼g�b�r�������Ū����N�۰ʰ���.
	 */
	public void importJquery() {
		addScript("var script = document.createElement('script');"
				+ "script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';"
				+ "script.type = 'text/javascript';" + "document.getElementsByTagName('head')[0].appendChild(script);");

	}

}
