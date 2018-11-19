package oa.SampleEvaluation.common;

import com.ysp.field.Mail;
import com.ysp.service.MailService;
import jcx.jform.bNotify;

public class MailMan extends bNotify {
	MailService mService;
	String[] mailTo;

	public MailMan(MailService service) {
		this.mService = service;
	}

	@Override
	public void actionPerformed(String arg0) throws Throwable {
		// TODO Auto-generated method stub
	}

	public void send(String[] mailTo, MailBody mbody) throws Exception {
		// TODO Auto-generated method stub

		mService.sendMailbccUTF8(mailTo, mbody.getSubject(), mbody.getMessage(), null, "", Mail.MAIL_HTML_CONTENT_TYPE);
	}
}
