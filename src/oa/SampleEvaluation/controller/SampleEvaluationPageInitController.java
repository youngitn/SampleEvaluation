package oa.SampleEvaluation.controller;

import java.sql.SQLException;

import com.ysp.service.BaseService;

import oa.SampleEvaluation.enums.*;
import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.UIHidderString;

/**
 * �P�_�����W�٨é���J�����
 * 
 * @author u52116
 *
 */
public class SampleEvaluationPageInitController extends Controller {

	private CommonDataObj cdo;
	BaseService service;

	@Override
	public String action(String arg0) throws Throwable {
		// �����J��B�z
		// �U�������J�B�z�����O����@

		service = new BaseService(this);
		FormInitUtil init = new FormInitUtil(this);
		setValue("receiveNowPage", getName());
		this.cdo = new CommonDataObj(getTalk(), getTableName(), "PNO", "APPLICANT");

		String actionObjName = getActionName(getName()).trim();

		switch (PageInitType.valueOf(actionObjName)) {
		case QUERY_PAGE_INIT:
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
	private void showRejectWarning(String pno) throws Exception {
		String sql = "select f_inp_info from " + getTableName() + "_flowc where PNO = '" + pno + "'";
		String[][] ret = getTalk().queryFromPool(sql);
		if (ret.length > 0) {
			String memo = ret[0][0];
			if (memo.startsWith("[�hñ]")) {
				addScript("callRejectWarning();");
			}
		}
	}

	private String getActionName(String name) {

		name = name.toUpperCase();
		if ("[FORM INIT] ".equals(name) || "[FORM INIT] QUERYPAGE".equals(name)) {
			return "QUERY_PAGE_INIT";
		} else if ("[FORM INIT] ADDPAGE".equals(name)) {
			return "ADD_PAGE_INIT";
		} else if (name.startsWith("[FORM INIT] [FLOW]")) {

			if ("[FORM INIT] [FLOW].�ݳB�z".equals(name)) {
				return "PENING_PAGE_INIT";
			} else {
				return "FLOW_PAGE_INIT";
			}

		} else if (name.startsWith("[FORM INIT] FLOWPAGE")) {
			return "DETAIL_PAGE_INIT";
		} else if (name.startsWith("[FORM INIT] SIGNFLOWHISTORY")) {

			return "SIGN_FLOW_HISTORY_PAGE_INIT";
		}
		if (name.contains(".")) {
			return name.substring(name.indexOf('.') + 1);
		}
		return name.toUpperCase();

	}

}
