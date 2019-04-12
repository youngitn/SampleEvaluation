package oa.SampleEvaluation.common;

import java.sql.SQLException;

import com.ysp.service.BaseService;
import com.ysp.service.MailService;

import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.notify.EmailNotify;
import oa.global.MailString;

/**
 * The Class MailToolInApprove.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class MailToolInApprove {

	/**
	 * Title text.
	 *
	 * @param service [BaseService]
	 * @return  [String]
	 */
	public static String titleText(BaseService service) {

		return " �Ƹ�:" + service.getValue("SAP_CODE") + "/ �~�W:" + service.getValue("MATERIAL") + " / �s�y��:"
				+ service.getValue("MFR") + " / QR���X:" + service.getValue("QR_NO") + " / �������G:"
				+ (service.getValue("EVALUATION_RESULT").equals("Y") ? "�A��" : "���A��") + " / ������:"
				+ service.getValue("SUPPLIER");
	}

	/**
	 * Email title builder.
	 *
	 * @param service [BaseService]
	 * @return  [String]
	 */
	public static String emailTitleBuilder(BaseService service) {
		String title = "";
		if (service.getMemo().startsWith("[�hñ]")) {
			title = "�hñ�q���G";
		} else {
			title = "ñ�ֳq���G";
		}

		// ���W��+�Ƹ�+�~�W+�s�y��+QR NO.+�������G+�����ӦW��.
		return title + service.getFunctionName() + "( �渹�G" + service.getValue("PNO") + " )";
	}

	/**
	 * Send sub flow mail.
	 *
	 * @param service [BaseService]
	 * @param mailTo [String]
	 * @param dto [SampleEvaluationPO]
	 * @param title [String]
	 * @throws Exception the exception
	 */
	public static void sendSubFlowMail(BaseService service, String mailTo, SampleEvaluationPO dto, String title)
			throws Exception {
		MailService mailService = new MailService(service);
		// Mail to
		String[] ret = mailTo.trim().split(" ");
		String[] usr = { service.getEmail(ret[0]) };

		// ���e
		EmailNotify en = new EmailNotify();
		en.setService(service);
		String content = en.buildContent(dto);
		new MailMan(mailService).send(usr, new MailBody(title, content));
	}

	/**
	 * Send notify to applicant.
	 *
	 * @param service [BaseService]
	 * @param mailTo [String]
	 * @param dto [SampleEvaluationPO]
	 * @param title [String]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public static void sendNotifyToApplicant(BaseService service, String mailTo, SampleEvaluationPO dto, String title)
			throws SQLException, Exception {
		MailService mailService = new MailService(service);
		// Mail to
		String[] ret = mailTo.trim().split(" ");
		String[] usr = { service.getEmail(ret[0]) };

		// ���e
		EmailNotify en = new EmailNotify();
		en.setService(service);
		String content = en.buildContent(dto);
		content = content.substring(content.indexOf("=="));
		new MailMan(mailService).send(usr, new MailBody(title, content));
	}
}
