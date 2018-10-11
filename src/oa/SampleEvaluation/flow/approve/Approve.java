package oa.SampleEvaluation.flow.approve;

import jcx.jform.bProcFlow;
import oa.SampleEvaluation.flow.approve.gateEnum.*;
import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import com.ysp.service.MailService;
import com.ysp.util.DateTimeUtil;

import oa.SampleEvaluation.notify.*;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.tableObject.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisDao;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowcHis;
import jcx.db.*;

public class Approve extends bProcFlow {

	String nowState;
	talk t;
	String isCheckValue;
	String isTrialProdValue;

	public boolean action(String value) throws Throwable {
		nowState = getState();
		t = getTalk();
		SampleEvaluation s = null;
		SampleEvaluationCheck sc = null;
		String alertStr = "";
		BaseService service = new BaseService(this);
		String labExe = getValue("LAB_EXE").trim();
		String lassessor = getValue("ASSESSOR").trim();
		String docCtrler = getValue("DOC_CTRLER").trim();
		String designee = getValue("DESIGNEE").trim();
		String designeEmpid = (designee.trim().split(" "))[0];
		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();

		switch (FlowState.valueOf(nowState)) {
		case �ժ�:

			// �i������P�_�ëإ߰e�X������ܪ�������r
			alertStr = buildApproveConfirmMsgStr();

			if (isCheckValue.equals("1") && isCheckValue.equals("")) {
				message("�п�ܹ���Ǹg��H��");
				return false;
			}

			if (isTrialProdValue.equals("1") && (labExe.equals("") || lassessor.equals(""))) {
				message("�п�ܸջs�����H���P����Ǹg��H��");
				return false;
			}

			if ((isTrialProdValue.equals("1") || isCheckValue.equals("1")) && docCtrler.equals("")) {
				message("�п�ܤ�ޤH��");
				return false;
			}
			if (getValue("QR_NO").trim().equals("")) {
				message("�ж�gQR���X");
				return false;
			}

			// ��s�D�����M�ջs�����Ŀ����
			// ��s�D�� �����H���M����Ǹg��
			s = new SampleEvaluation();
			s = s.setAllValue(s, service);
			new SampleEvaluationDaoImpl(t).update(s);

			// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��
			if (isCheckValue.equals("1")) {

				sc = new SampleEvaluationCheck();
				sc = sc.setAllValue(sc, service);
				SampleEvaluationCheckDao checkDao = new SampleEvaluationCheckDao(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update(sc);
				} else {
					// insert�@���l�y�{�D��
					checkDao.add(sc);

					SampleEvaluationCheckFlowc flowc = new SampleEvaluationCheckFlowc(sc.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					flowc.setF_INP_ID(designeEmpid);
					flowc.setF_INP_STAT("��g����渹");
					flowc.setF_INP_TIME(time);
					SampleEvaluationCheckFlowcDao secfDao = new SampleEvaluationCheckFlowcDao();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// �إߤl�y�{FLOWC_HIS ���� ������ñ�־��v
					time = DateTimeUtil.getApproveAddSeconds(0);
					SampleEvaluationCheckFlowcHis his = new SampleEvaluationCheckFlowcHis(sc.getOwnPno(),
							flowc.getF_INP_STAT(), time);

					his.setF_INP_ID(designeEmpid);
					SampleEvaluationCheckFlowcHisDao secfhDao = new SampleEvaluationCheckFlowcHisDao();
					secfhDao.create(getTalk().getConnectionFromPool(), his);

				}
				// ������y�{ �H�X�q���H

				MailService mailService = new MailService(service);
				String title = "ñ�ֳq���G" + this.getFunctionName() + "_����y�{" + "( �渹�G" + getValue("PNO") + " )";
				// Mail to
				String[] usr = { getEmail(designeEmpid) };

				// ���e
				EmailNotify en = new EmailNotify();
				en.setService(service);
				String content = en.getContent();

				mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);

			}

			break;
		case �ջs�渹��g:
			// ��s�D��ջs�渹���
			if (!getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				// ��s�D��ջs�渹���
				s = new SampleEvaluation();
				s = s.setAllValue(s, service);
				new SampleEvaluationDaoImpl(t).update(s);

				// ��s�l�y�{�D��ջs�渹���
				sc = new SampleEvaluationCheck();
				sc = sc.setAllValue(sc, service);
				new SampleEvaluationCheckDao(t).update(sc);
			} else {
				message("�п�J�ջs�q���渹");
				return false;
			}

			break;
		case ���z���D�ޤ���:
			// ��s�D����פH���
			if (!designee.equals("")) {
				t.execFromPool("UPDATE  sample_evaluation  SET DESIGNEE=?" + " where pno=?",
						new Object[] { designee, getValue("PNO") });
				t.execFromPool("UPDATE  sample_evaluation_check  SET DESIGNEE=?" + " where own_pno=?",
						new Object[] { designee, getValue("OWN_PNO") });
			} else {
				message("�п�� ���z�������H�� ���");
				return false;
			}

			break;
		case �ݳB�z:
			// ��s�D����פH���
			s = new SampleEvaluation();
			s = s.setAllValue(s, service);
			new SampleEvaluationDaoImpl(t).update(s);
			break;
		default:
			break;
		}
		return doReminder(alertStr);

	}

	private String buildApproveConfirmMsgStr() {
		String alertStr = "";
		if (isCheckValue.equals("0")) {

			alertStr += "�N���|�i�����y�{;<br>";
		} else {

			alertStr += "�N�i�����y�{;<br>";
		}
		if (isTrialProdValue.equals("0")) {

			alertStr += "�N���|�i��ջs�����y�{;<br>";
		} else {

			alertStr += "�N�i��ջs�����y�{;<br>";
		}
		if (isTrialProdValue.equals("0") && isCheckValue.equals("0")) {
			alertStr = "�ҥ��Ŀ�\"�O�_�i�����/�ջs�����y�{\"�������󶵥�<br> �e�X��N��������;<br>";
		}
		return alertStr;
	}

	/**
	 * ���ɴ��� ���ǤJ �^��true/false
	 */
	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "�T�w�e�X���H", "���ɴ���", 0);
		if (result == 1) {
			message("�w�����e�X���");
			return false;
		}
		String space = "";
		for (int i = 0; i < 16; i++) {
			space += "&emsp;";
		}
		percent(100, space + "���e�X���A�еy��...<font color=white>");
		message("ñ�֧���");
		return true;
	}

}
