package oa.SampleEvaluation;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;

import com.ysp.service.BaseService;

import jcx.jform.hproc;
import oa.SampleEvaluation.enums.*;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.UIHidderString;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl; 

/**
 * ���եi���ռg�ksh
 * 
 * @author u52116
 *
 */
public class SampleEvaluationPageInitController extends hproc {

	public boolean confirm = true;
	public CommonDataObj cdo;
	BaseService service;

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// �����J��B�z
		// �U�������J�B�z�����O����@
		// importJquery();
		service = new BaseService(this);
		FormInitUtil init = new FormInitUtil(this);
		setValue("NOW_INIT", getName());
		this.cdo = new CommonDataObj(service, "PNO", "APPLICANT");

		String actionObjName = getActionName(getName());
		File saveFile = new File("Data.txt");
		try {
			FileWriter fwriter = new FileWriter(saveFile);
			fwriter.write(actionObjName);
			fwriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		// ���s�ʧ@�B�z�i�J�I
		switch (PageInitType.valueOf(actionObjName.trim())) {
		case QUERY_PAGE_INIT:// �i�J�d�ߵe��
			addScript(UIHidderString.hideDmakerAddButton());
			init.doQueryPageProcess();
			break;

		case ADD_PAGE_INIT:// �i�J�s�W�e��
			addScript(UIHidderString.hideDmakerAddButton());
			init.doAddPageProcess();
			break;

		case PENING_PAGE_INIT:// �i�J�ݳB�z�e��

			init.doPendingPageProcess();
			break;

		case DETAIL_PAGE_INIT:// �i�J���ӵe��
			init.doDetailPageProcess();
			break;
		case FLOW_PAGE_INIT:// �i�J�y�{ñ�ֵe��
			init.doPendingPageProcess();
			// ñ�֧�����ܥD����
			String pno = getValue("PNO").trim();
			if (pno.length() <= 0) {
				changeForm(getFunctionName());
			} else {
				showRejectWarning(pno);
			}
			// �p�G�a�X����� �ջs�ﶵ������ �N��ܵ����H��
			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")) {
				setVisible("ASSESSOR", true);
			}
			if (getState().trim().equals("�ժ�")) {
				setEditable("IS_CHECK", true);
				setEditable("IS_TRIAL_PRODUCTION", true);

				setEditable("ASSESSOR", true);
				setEditable("LAB_EXE", true);
				setEditable("QR_NO", true);
				setEditable("DOC_CTRLER", true);

			}
			if (getState().trim().equals("�ջs�渹��g")) {
				setEditable("NOTIFY_NO_TRIAL_PROD", true);
			}
			if (getState().trim().equals("���z���D�ޤ���")) {
				setEditable("DESIGNEE", true);
			}
			if (getState().trim().equals("�ݳB�z")) {
				// �����B�z�g�bUI���I���϶�ADD_BACKGROUND��
				// �]���o�䪺�޿� �b�ݳB�z���d�Y����
			}

			break;
		default:

			break;
		}
		return null;

	}

	/**
	 * @param pno
	 * @throws SQLException
	 * @throws Exception
	 */
	private void showRejectWarning(String pno) throws SQLException, Exception {
		String sql = "select f_inp_info from " + getTableName() + "_flowc where PNO = '" + pno + "'";
		String[][] ret = getTalk().queryFromPool(sql);
		if (ret.length > 0) {
			String memo = ret[0][0];
			if (memo.startsWith("[�hñ]")) {
				addScript("callRejectWarning();");
			}
		}
	}

	public void detailPage() throws SQLException, Exception {

		// ��J�D�ɸ��
		String key = getValue("QUERY_LIST.PNO");

		String[][] ret = new SampleEvaluationDaoImpl(getTalk()).findArrayById(key);
		String[][] allColumns = cdo.getTableAllColumn();
		if (allColumns.length > 0) {
			for (int i = 0; i < allColumns.length; i++) {
				setValue(allColumns[i][0], ret[0][i]);
			}
		} else {
			message("�o�Ϳ��~�A�䤣�즹����ơI");
		}
	}

	public String getActionName(String Name) {

		Name = Name.toUpperCase();
		if ("[FORM INIT] ".equals(Name) || "[FORM INIT] QUERYPAGE".equals(Name)) {
			return "QUERY_PAGE_INIT";
		} else if ("[FORM INIT] ADDPAGE".equals(Name)) {
			return "ADD_PAGE_INIT";
		} else if (Name.startsWith("[FORM INIT] [FLOW]")) {

			if ("[FORM INIT] [FLOW].�ݳB�z".equals(Name)) {
				return "PENING_PAGE_INIT";
			} else {
				return "FLOW_PAGE_INIT";
			}

		} else if (Name.startsWith("[FORM INIT] DETAILPAGE")) {
			return "DETAIL_PAGE_INIT";
		} else if (Name.startsWith("[FORM INIT] SIGNFLOWHISTORY")) {

			return "SIGN_FLOW_HISTORY_PAGE_INIT";
		}
		if (Name.contains(".")) {
			return Name.substring(Name.indexOf(".") + 1);
		}
		return Name.toUpperCase();

	}

	public void UIHide() {
		// ����Dmaker���h�s�W���s
		addScript(UIHidderString.hideDmakerAddButton());

		// ���äW�����
		addScript(UIHidderString.hideDmakerFlowPanel());
		// ���øԲӸԲӦC��\��
		addScript(UIHidderString.hideFlowButtonDetail());

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
