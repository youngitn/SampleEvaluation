package oa.SampleEvaluation.common;

import com.ysp.field.Mail;
import com.ysp.service.MailService;

/**
 * The Class MailMan.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class MailMan  {
	
	/** The m service. */
	MailService mService;
	
	/** The mail to. */
	String[] mailTo;

	/**
	 * Instantiates a new mail man.
	 *
	 * @param service [MailService]
	 */
	public MailMan(MailService service) {
		this.mService = service;
	}


	/**
	 * Send.
	 *
	 * @param mailTo [String[]]
	 * @param mbody [MailBody]
	 * @throws Exception the exception
	 */
	public void send(String[] mailTo, MailBody mbody) throws Exception {

		mService.sendMailbccUTF8(mailTo, mbody.getSubject(), mbody.getMessage(), null, "", Mail.MAIL_HTML_CONTENT_TYPE);
	}
}
