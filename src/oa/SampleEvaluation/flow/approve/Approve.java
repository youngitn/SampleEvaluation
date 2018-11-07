package oa.SampleEvaluation.flow.approve;

import jcx.jform.bProcFlow;
import oa.SampleEvaluation.flow.approve.gateEnum.*;

import java.lang.reflect.Constructor;
import java.sql.SQLException;

import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import com.ysp.service.MailService;
import com.ysp.util.DateTimeUtil;

import oa.SampleEvaluation.notify.*;
import oa.SampleEvaluation.dao.AbstractGenericDao;
import oa.SampleEvaluation.dao.AbstractGenericFlowcDao;
import oa.SampleEvaluation.dao.AbstractGenericFlowcHisDao;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.AbstractGenericFlowcDto;
import oa.SampleEvaluation.dto.AbstractGenericFlowcHisDto;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
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
		String alertStr = "";
		BaseService service = new BaseService(this);
		String labExe = getValue("LAB_EXE").trim();
		String lassessor = getValue("ASSESSOR").trim();
		String docCtrler = getValue("DOC_CTRLER").trim();
		String designee = getValue("DESIGNEE").trim();
		designee.trim().split(" ");
		getValue("PNO");
		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();

		switch (FlowState.valueOf(nowState)) {
		case �ժ�:

			if ((isCheckValue.equals("1") || isTrialProdValue.equals("1")) && labExe.equals("")) {
				message("�п�ܹ���Ǹg��H��");
				return false;
			}

			if (isTrialProdValue.equals("1") && lassessor.equals("")) {
				message("�п�ܸջs�����H��");
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
			if (doReminder("")) {
				// ��s�D�����M�ջs�����Ŀ����
				// ��s�D�� �����H���M����Ǹg��
				s = new SampleEvaluation();
				s.setAllValue(service);
				new SampleEvaluationDaoImpl(t).update(s);

				// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��
				if (isCheckValue.equals("1")) {

					SampleEvaluationSubBaseDto secDto = new SampleEvaluationCheck();
					secDto.setAllValue(service);
					goSubFlow("Check", secDto);
					String title = "ñ�ֳq���G" + this.getFunctionName() + "_����y�{" + "( �渹�G" + getValue("PNO") + " )";
					// ������y�{ �H�X�q���H
					sendSubFlowMail(service, getValue("DOC_CTRLER"), secDto, title);

				}
				if (isTrialProdValue.equals("1")) {

					SampleEvaluationSubBaseDto setDto = new SampleEvaluationTp();
					setDto.setAllValue(service);
					goSubFlow("Tp", setDto);
					String title = "ñ�ֳq���G" + this.getFunctionName() + "_�ջs�y�{" + "( �渹�G" + getValue("PNO") + " )";
					// ���ջs�y�{ �H�X�q���H
					sendSubFlowMail(service, getValue("ASSESSOR"), setDto, title);
				}
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

			return doReminder(alertStr);
		case �ݳB�z:
		case ���ʸg��T�{:
			// ��s�D����פH���
			s = new SampleEvaluation();
			s.setAllValue(service);
			new SampleEvaluationDaoImpl(t).update(s);
			return doReminder(alertStr);

		case ���ʸg��:
			if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("�������G�P�䧨�ɤ��o����");
				return false;
			} else if (doReminder(alertStr)) {
				s = new SampleEvaluation();
				s.setAllValue(service);
				new SampleEvaluationDaoImpl(t).update(s);
				return true;
			}
		default:
			break;
		}
		return true;
	}

	private void sendSubFlowMail(BaseService service, String mailTo, SampleEvaluationSubBaseDto dto, String title)
			throws Exception {
		MailService mailService = new MailService(service);
		// Mail to
		String[] ret = mailTo.trim().split(" ");
		String[] usr = { getEmail(ret[0]) };

		// ���e
		EmailNotify en = new EmailNotify();
		en.setService(service);
		String content = en.getContent(dto);

		mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);
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

	private void goSubFlow(String type, SampleEvaluationSubBaseDto s)
			throws ClassNotFoundException, SQLException, Exception {

		Class<?> subMainDao = Class.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "DaoImpl");
		Constructor<?> DaoCon = subMainDao.getConstructor(talk.class);
		AbstractGenericDao<SampleEvaluationSubBaseDto> Dao = (AbstractGenericDao<SampleEvaluationSubBaseDto>) DaoCon
				.newInstance(t);

		if (Dao.findById(s.getOwnPno()) != null) {
			Dao.update(s);
		} else {
			// insert�@���l�y�{�D��
			Dao.add(s);

			AbstractGenericFlowcDto<?> Dto = (AbstractGenericFlowcDto<?>) Class
					.forName("oa.SampleEvaluation" + type + ".dto.SampleEvaluation" + type + "Flowc").newInstance();

			Dto.setOwnPno(s.getOwnPno());
			String time = DateTimeUtil.getApproveAddSeconds(0);

			Dto.setF_INP_ID(s.getApplicant());
			String gateName = "��g����渹";
			if (type.equals("Tp")) {
				gateName = "�����H��";
			}
			Dto.setF_INP_STAT(gateName);
			Dto.setF_INP_TIME(time);

			AbstractGenericFlowcDao<AbstractGenericFlowcDto<?>> secfDao = (AbstractGenericFlowcDao<AbstractGenericFlowcDto<?>>) Class
					.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "FlowcDaoImpl")
					.newInstance();

			secfDao.create(t.getConnectionFromPool(), Dto);

			// �إߤl�y�{FLOWC_HIS ���� ������ñ�־��v
			time = DateTimeUtil.getApproveAddSeconds(0);

			AbstractGenericFlowcHisDto<?> his = (AbstractGenericFlowcHisDto<?>) Class
					.forName("oa.SampleEvaluation" + type + ".dto.SampleEvaluation" + type + "FlowcHis").newInstance();

			his.setF_INP_STAT(Dto.getF_INP_STAT());
			his.setOwnPno(s.getOwnPno());
			his.setF_INP_TIME(time);
			his.setF_INP_ID(s.getApplicant());
			AbstractGenericFlowcHisDao<AbstractGenericFlowcHisDto<?>> secfhDao = (AbstractGenericFlowcHisDao<AbstractGenericFlowcHisDto<?>>) Class
					.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "FlowcHisDaoImpl")
					.newInstance();

			secfhDao.create(t.getConnectionFromPool(), his);

		}

	}

}
