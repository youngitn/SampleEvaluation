package oa.SampleEvaluation.notify;

import jcx.jform.bNotify; 
import jcx.jform.bProcFlow;

import oa.SampleEvaluation.common.EmailUtil;
import oa.SampleEvaluation.common.MailBody;

import oa.SampleEvaluation.common.MailMan;
import java.sql.SQLException;

import com.ysp.field.Mail;
import com.ysp.service.MailService; // 1.MailService  
import com.ysp.service.BaseService; // 1.MailService  

public abstract class BaseEmailNotify extends bNotify {

	protected MailService mailService;
	protected String[] usr;
	protected boolean isLastGate;
	protected bNotify bn = null;
	protected bProcFlow bpf = null;
	protected EmailUtil emailUtil;
	protected BaseService service;

	public void actionPerformed(String value) throws Throwable {
		// ������i�J�y�{���A���ݥD�ޮ�,�|���楻�q�{��
		// �i�ΥH�H�oEmail�q�������P��Ʈw�L�������~
		// �p�G�n���ʸ�Ʈw�A��ĳ��b�y�{�w�B�z�{�Ǥ��A�� addToTransaction(sql); �H�T�O��Ʈw���ʤ@�P.
		// MailService

		// �j��������ʧ@�ҩe����service

		service = new BaseService(this);
		emailUtil = new EmailUtil(service);
		mailService = new MailService(service);

		// �إߤ��e
		String content = buildContent();
		content += "" + emailUtil.getHisOpinion() + Mail.HTML_LINE_BREAK;

		// ���o��ñ�֤H
		usr = mailService.getMailAddresseeByEngagedPeople();
		// �P�_�O�_���׳q��
		setIsLastGate();
		String title = emailTitleBuilder();
		setIsLastGate();
		if (isLastGate) {
			// ���oñ�ֹL���Ҧ��H
			usr = emailUtil.getAllSignedPeopleEmailForLastGateToSend();
			title = emailTitleBuilderForFinalGate();
		}
		MailBody mBody = new MailBody(title, content);
		MailMan m = new MailMan(mailService);
		m.send(usr, mBody);

	}

	protected void setIsLastGate() {
		isLastGate = false;
	}

	protected abstract String buildContent() throws SQLException, Exception;

	protected String emailTitleBuilder() {
		String title = "";
		if (mailService.isReject()) {
			title = "�hñ�q���G";
		} else {
			title = "ñ�ֳq���G";
		}
		return title + this.getFunctionName() + "( �渹�G" + getValue("PNO") + " )";
	}

	protected String emailTitleBuilderForFinalGate() {

		return "���׳q���G" + this.getFunctionName() + "( �渹�G" + getValue("PNO") + " )";

	}

	@Override
	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}
}