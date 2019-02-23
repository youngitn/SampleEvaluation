package oa.SampleEvaluation.controller;

import oa.SampleEvaluation.common.FormInitUtil;
import oa.SampleEvaluation.enums.PageInitType;
import oa.SampleEvaluation.flow.approve.gateEnum.FlowState;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
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
		addScript(UIHidderString.hideDmakerAddButton());
		String actionObjName = getActionName(getName()).trim();
		try {
			switch (PageInitType.valueOf(actionObjName)) {
			case QUERY_PAGE_INIT:
				init.doQueryPageProcess();
				break;

			case ADD_PAGE_INIT:// �i�J�s�W�e��
				init.doAddPageProcess();
				break;

			case PENING_PAGE_INIT:// �i�J�ݳB�z�e��
				init.setBillOtherData();
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));

				break;

			case FLOW_PAGE_INIT:// �i�J�y�{ñ�ֵe��
				init.setBillOtherData();
				setValue("DL", getDeadLine(getValue("APP_DATE"), getValue("URGENCY")));
				// �Ҧ���줣�i�s��
				setAllFieldUneditable();
				
				// ñ�֧�����ܥD����
				String pno = getValue("PNO").trim();
				if (pno.length() <= 0) {
					changeForm(getFunctionName());
				} else {
					showRejectWarning(pno);
				}

				// �y�{�e���b�U���d����l��switch�B�z��k
				switchByStateForFlowInit(FlowState.valueOf(getState().trim()));

				setTextAndCheckIsSubFlowRunning();

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
		case �ҥD��:
			break;
		case �ժ�:
			// �������i�s��
			setAllFileUploadFieldEditable();
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
