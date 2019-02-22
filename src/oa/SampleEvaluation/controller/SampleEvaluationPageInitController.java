package oa.SampleEvaluation.controller;

import java.sql.SQLException;

import oa.SampleEvaluation.enums.PageInitType;
import oa.SampleEvaluation.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.global.BaseDao;
import oa.global.FormInitUtil;
import oa.global.UIHidderString;

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
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));

				break;

			case FLOW_PAGE_INIT:// �i�J�y�{ñ�ֵe��
				init.doPendingPageProcess();
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));
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

				// �y�{�e���b�U���d����l��switch�B�z��k
				switchByStateForFlowInit(FlowState.valueOf(getState().trim()));

				// �ھڤĿ諸�l�y�{�N����������
				if ("1".equals(getValue("IS_CHECK").trim())) {
					setVisible("SUB_FLOW_TAB_CHECK", true);
					setTextAndCheckWithSubFlow(new SampleEvaluationCheckService(getTalk()), getValue("PNO") + "CHECK");

				}
				if ("1".equals(getValue("IS_TRIAL_PRODUCTION").trim())) {
					setVisible("SUB_FLOW_TAB_TP", true);
					setTextAndCheckWithSubFlow(new SampleEvaluationTpService(getTalk()), getValue("PNO") + "TP");
				}
				if ("1".equals(getValue("IS_TEST").trim())) {
					setVisible("SUB_FLOW_TAB_TEST", true);
					setTextAndCheckWithSubFlow(new SampleEvaluationTestService(getTalk()), getValue("PNO") + "TEST");
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

	protected void setTextAndCheckWithSubFlow(BaseDao dao, String pno) {

		try {
			if (dao.findById(pno) != null) {
				if (pno.contains("CHECK")) {
					setValue("START_CHECK_FLOW_TEXT", "�w����!");
					setEditable("START_CHECK_FLOW", false);
					setEditable("IS_CHECK", false);
				}
				if (pno.contains("TEST")) {
					setValue("START_TEST_FLOW_TEXT", "�w����!");
					setEditable("START_TEST_FLOW", false);
					setEditable("IS_TEST", false);
				}
				if (pno.contains("TP")) {
					setValue("START_TP_FLOW_TEXT", "�w����!");
					setEditable("START_TP_FLOW", false);
					setEditable("IS_TRIAL_PRODUCTION", false);
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void switchByStateForFlowInit(FlowState en) {

		switch (en) {
		case �ժ�:

			setEditable("IS_CHECK", true);
			setEditable("IS_TEST", true);
			setEditable("IS_TRIAL_PRODUCTION", true);
			setEditable("ASSESSOR", true);
			setEditable("NOTE", true);
			setEditable("LAB_EXE", true);
			setEditable("QR_NO", true);
			setEditable("DOC_CTRLER_TP", true);
			setEditable("DOC_CTRLER_CHECK", true);
			setEditable("QC_BOSS", true);
			setEditable("COORDINATOR", true);

			setEditable("START_CHECK_FLOW", true);
			setEditable("START_TP_FLOW", true);
			setEditable("START_TEST_FLOW", true);
			break;
		case ���z���D�ޤ���:
			setEditable("DESIGNEE", true);
			break;
		case ���ʸg��:
			setEditable("EVALUATION_RESULT", true);
			setEditable("FILE_EVALUATION_RESULT", true);
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

}
