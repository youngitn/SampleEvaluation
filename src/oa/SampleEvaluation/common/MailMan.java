package oa.SampleEvaluation.common;

import com.ysp.field.Mail;
import com.ysp.service.MailService;

public class MailMan  {
	MailService mService;
	String[] mailTo;

	public MailMan(MailService service) {
		this.mService = service;
	}


	public void send(String[] mailTo, MailBody mbody) throws Exception {

		mService.sendMailbccUTF8(mailTo, mbody.getSubject(), mbody.getMessage(), null, "", Mail.MAIL_HTML_CONTENT_TYPE);
	}
}
