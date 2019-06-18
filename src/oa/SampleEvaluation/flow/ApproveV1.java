package oa.SampleEvaluation.flow;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.enums.FlowStateEnum;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;
import oa.global.BaseDao;

/**
 * The Class ApproveV1.
 * 20190612 �ݭ��c �l�y�{�_�泡���L�׳]�p ���z�� �{�b�Ӭݫܨ��W.
 * @author YoungCheng(u52116) 2019/3/19
 */
public class ApproveV1 extends bProcFlow {

	/** The now state. */
	String nowState;

	/** The t. */
	talk t;

	/** The is check value. */
	String isCheckValue;

	/** The is trial prod value. */
	String isTrialProdValue;

	/** The is test value. */
	String isTestValue;

	String isElse;

	ArrayList<String> errorMsgList = new ArrayList<String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see jcx.jform.bProcFlow#action(java.lang.String)
	 */
	public boolean action(String value) throws Throwable {
		fileItemSetChecked();
		nowState = getState();
		t = getTalk();
		String designee = getValue("DESIGNEE").trim();

		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		this.isTestValue = getValue("IS_TEST").trim();
		this.isElse = getValue("IS_ELSE").trim();
		boolean ret = doReminder("");

		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluationPO se = new SampleEvaluationPO();
		se.getDataFromForm(this);

		switch (FlowStateEnum.valueOf(nowState)) {
		case �ҥD��:
			String[][] doc = getTalk()
					.queryFromPool("SELECT DOC_CTRLER_CHECK FROM SAMPLE_EVALUATION_SUB_FLOW_SIGN_MAP WHERE DEPNO='"
							+ getValue("RECEIPT_UNIT") + "'");
			if (doc.length > 0 && !"".equals(doc[0][0])) {
				daoservice.update(se);
				// �q���g��
				String mailTitle = "�z�ӽЪ� " + this.getFunctionName() + " �D�ޤw�֭� ( �渹�G" + se.getPno() + ") ";
				MailToolInApprove.sendNotifyToApplicant(new BaseService(this), se.getApplicant(), se, mailTitle);
			} else {
				ret = false;
				message("�֭㥢��,�䤣��U�@���dñ�֤H,�Ш��z��촣�Ѥ�ޤH����Ƥ���T���");
			}
			break;
		case �ժ�:
			SyncDataService.subFlowSync(t, this);
			String pno = getValue("PNO").trim();
			String subFlowBuilderPath = "oa.SampleEvaluation.subflowbuilder.builder.";
			String servicePath = "oa.SampleEvaluationTp.service.";
			checkSubflowNecessaryInformation(this.isCheckValue, subFlowBuilderPath + "CheckFlowBuilder",
					new SampleEvaluationCheckService(t), "����", pno + "CHECK");
			checkSubflowNecessaryInformation(this.isTrialProdValue, subFlowBuilderPath + "TpFlowBuilder",
					new SampleEvaluationTpService(t), "�ջs", pno + "TP");
			checkSubflowNecessaryInformation(this.isTestValue, subFlowBuilderPath + "TestFlowBuilder",
					new SampleEvaluationTestService(t), "�ը�", pno + "TEST");

			String errorMage = "";
			if (errorMsgList.size() > 0) {
				ret = false;
				for (String strings : errorMsgList) {
					errorMage += strings + "<BR>";
				}
				message(errorMage);
			}
			break;
		case ���z���D�ޤ���:

			if (ret && !designee.equals("")) {
				daoservice.update(se);
			} else {
				message("���D�ޫ����H��(�ժ�)���i�ť�");
				ret = false;
			}
			break;
		case ��ޤH��:
			if (getValue("QR_NO") == null || "".equals(getValue("QR_NO").trim())) {
				message("QR���X���i�ť�");
				ret = false;
			}
			if (ret) {
				daoservice.update(se);
			}
			break;
		case �ݳB�z:

			if (ret) {
				daoservice.update(se);
			}
			break;
		case ���ʸg��:

			if ("0".equals(this.isCheckValue) && "0".equals(this.isTrialProdValue) && "0".equals(this.isTestValue)) {
				ret = true;

			} else if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("�������G�P���ɤ��i�ť�");
				ret = false;
			} else if (ret) {

				SyncDataService.subFlowSync(t, this);
				ret = true;
			}
			break;

		default:
			break;
		}
		return ret;
	}

	/**
	 * File item set checked.
	 */
	private void fileItemSetChecked() {

		if (!getValue("FILE_SPEC").equals("")) {
			setValue("PROVIDE_SPEC", "1");
		}
		if (!getValue("FILE_COA").equals("")) {
			setValue("PROVIDE_COA", "1");
		}
		if (!getValue("FILE_SDS").equals("")) {
			setValue("PROVIDE_SDS", "1");
		}
		if (!getValue("FILE_OTHERS").equals("")) {
			setValue("PROVIDE_OTHERS", "1");
		}
		if (!getValue("FILE_TEST_METHOD").equals("")) {
			setValue("PROVIDE_TEST_METHOD", "1");
		}
	}

	/**
	 * Do reminder.
	 *
	 * @param addStr [String]
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "�T�w�e�X?", "���ɴ���", 0);
		if (result == 1) {
			message("�����e�X!");
			return false;
		}
		StringBuilder space = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			space.append("&emsp;");
		}
		percent(100, space.toString() + "�B�z��<font color=white>");
		message("�w�e�X");
		return true;
	}

	private void checkSubflowNecessaryInformation(String isChecked, String builderClassName, BaseDao serviceClass,
			String subFlowTypeName, String subpno) throws SQLException, Exception {
		boolean ret = true;
		if ("1".equals(isChecked)) {
			SubFlowBuilder sfb = (SubFlowBuilder) createObject(builderClassName);
			try {
				// ���祲�n��쥼��
				if (!sfb.isReady(this)) {
					errorMsgList.add(subFlowTypeName + "�l�y�{����ñ�֤H��줣�i�ť�");
					// ret = false;
				}
			} catch (NullPointerException e) {
				e.getMessage();
				message("SubFlowBuilder�i�ରnull,�гq��IT�H��");
			}
			// �����o������l�y�{�N�e�X
			// BaseDao Service = (BaseDao) createObject(serviceClassName);

			if (serviceClass.findById(subpno) == null) {
				errorMsgList.add("�Ы��U''����" + subFlowTypeName + "�y�{''���s�H�i�����y�{");
				// ret = false;
			}
		}

		// return ret;

	}

	private Object createObject(String className) {
		Object object = null;
		try {
			Class classDefinition = Class.forName(className);
			object = classDefinition.newInstance();
		} catch (InstantiationException e) {
			System.out.println(e);
		} catch (IllegalAccessException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return object;
	}

}
