package oa.SampleEvaluation.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import oa.SampleEvaluation.common.global.FormInitUtil;
import oa.SampleEvaluation.common.global.UIHidderString;
import oa.SampleEvaluation.enums.FlowState;
import oa.SampleEvaluation.enums.PageInitType;

/**
 * �P�_�����W�٨é���J�����
 * 
 * @author u52116
 *
 */
public class SampleEvaluationPageInitController extends HprocImpl {

	@Override
	public String action(String arg0) throws Throwable {
		// �����J��B�z
		// �U�������J�B�z�����O����@

		FormInitUtil init = new FormInitUtil(this);

		String actionObjName = getActionName(getName()).trim();
		try {
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
				setDeadLine();
				break;

			case DETAIL_PAGE_INIT:// �i�J���ӵe��
				init.doDetailPageProcess();
				setDeadLine();
				break;
			case FLOW_PAGE_INIT:// �i�J�y�{ñ�ֵe��
				init.doPendingPageProcess();
				setDeadLine();
				// �Ҧ���줣�i�s��
				setAllFieldUneditable();
				// �������i�s��
				setAllFileUploadFieldEditable();
				// ñ�֧�����ܥD����
				String pno = getValue("PNO").trim();
				if (pno.length() <= 0) {
					changeForm(getFunctionName());
				} else {
					showRejectWarning(pno);
				}
				switchByStateForFlowInit(FlowState.valueOf(getState().trim()));
				// �p�G�a�X����� �ջs�ﶵ������ �N��ܵ����H��
				if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")) {
					setVisible("ASSESSOR", true);
				}
				break;
			default:

				break;
			}
		} catch (EnumConstantNotPresentException e) {
			message("enum:PageInitType.clss �o�͵L�k���Ѫ��N�~");
		}
		return null;

	}

	private void switchByStateForFlowInit(FlowState en) {

		switch (en) {
		case �ժ�:

			setEditable("IS_CHECK", true);
			setEditable("IS_TRIAL_PRODUCTION", true);
			setEditable("ASSESSOR", true);
			setEditable("NOTE", true);
			setEditable("LAB_EXE", true);
			setEditable("QR_NO", true);
			setEditable("DOC_CTRLER", true);
			break;
		case ���z���D�ޤ���:
			setEditable("DESIGNEE", true);
			break;
		default:
			break;
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

	private void setDeadLine() {
		Calendar c = Calendar.getInstance();
		int addDaysNum = 0;
		String value = getValue("URGENCY");
		if (!value.isEmpty()) {
			if (value.equals("A")) {
				addDaysNum = 100;
			} else if (value.equals("B")) {
				addDaysNum = 110;
			} else if (value.equals("C")) {
				addDaysNum = 130;
			}
			c.add(Calendar.DATE, addDaysNum);
			Date d = c.getTime();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
			String dldate = sdFormat.format(d);

			setValue("DL", dldate);
		}
	}

}
