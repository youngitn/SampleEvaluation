package oa.SampleEvaluation;

import jcx.jform.bNotify;
import jcx.jform.bProcFlow;

import oa.SampleEvaluation.common.EmailUtil;

import java.io.FileWriter;
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
		// ����i�J�y�{���A���ݥD�ޮ�,�|���楻�q�{��
		// �i�ΥH�H�oEmail�q�������P��Ʈw�L�������~
		// �p�G�n���ʸ�Ʈw�A��ĳ��b�y�{�w�B�z�{�Ǥ��A�� addToTransaction(sql); �H�T�O��Ʈw���ʤ@�P.
		// MailService

		// �j�������ʧ@�ҩe����service
		try {
			service = new BaseService(this);
			emailUtil = new EmailUtil(service);

			mailService = new MailService(service);

			setIsLastGate();

			// ���o�H�󤺮e���D
			String title = emailTitleBuilder();

			// ���o��ñ�֤H
			usr = mailService.getMailAddresseeByEngagedPeople();

			// �P�_�O�_���׳q��
			if (isLastGate) {
				// ���oñ�ֹL���Ҧ��H
				usr = emailUtil.getAllSignedPeopleEmailForLastGateToSend();
				title = emailTitleBuilderForFinalGate();
			}

			// �إߤ��e
			String content = buildContent();
			content += "" + emailUtil.getHisOpinion() + Mail.HTML_LINE_BREAK;
			mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);
		} catch (NullPointerException e) {
			// TODO: handle exception
			FileWriter fw = new FileWriter("test5566.txt");
			e.printStackTrace();
			fw.write(e.toString());
			fw.flush();
			fw.close();
		}

	}

	protected void setIsLastGate() {
		isLastGate = false;
	}

	protected abstract String buildContent() throws SQLException, Exception;

	protected String emailTitleBuilder() {

		if (mailService.isReject()) {
			return "�hñ�q���G" + this.getFunctionName() + "( �渹�G" + getValue("PNO") + " )";
		} else {
			return "ñ�ֳq���G" + this.getFunctionName() + "( �渹�G" + getValue("PNO") + " )";
		}
	}

	protected String emailTitleBuilderForFinalGate() {

		return "���׳q���G" + this.getFunctionName() + "( �渹�G" + getValue("PNO") + " )";

	}

	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}
}
