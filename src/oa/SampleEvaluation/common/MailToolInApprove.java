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

		return " 料號:" + service.getValue("SAP_CODE") + "/ 品名:" + service.getValue("MATERIAL") + " / 製造商:"
				+ service.getValue("MFR") + " / QR號碼:" + service.getValue("QR_NO") + " / 評估結果:"
				+ (service.getValue("EVALUATION_RESULT").equals("Y") ? "適用" : "不適用") + " / 供應商:"
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
		if (service.getMemo().startsWith("[退簽]")) {
			title = "退簽通知：";
		} else {
			title = "簽核通知：";
		}

		// 表單名稱+料號+品名+製造商+QR NO.+評估結果+供應商名稱.
		return title + service.getFunctionName() + "( 單號：" + service.getValue("PNO") + " )";
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

		// 內容
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

		// 內容
		EmailNotify en = new EmailNotify();
		en.setService(service);
		String content = en.buildContent(dto);
		content = content.substring(content.indexOf("=="));
		new MailMan(mailService).send(usr, new MailBody(title, content));
	}
}
