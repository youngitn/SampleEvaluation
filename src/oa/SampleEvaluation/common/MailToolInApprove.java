package oa.SampleEvaluation.common;

import com.ysp.service.BaseService;
import com.ysp.service.MailService;

import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluation.notify.EmailNotify;

public class MailToolInApprove {

	public static String titleText(BaseService service) {

		return " 料號:" + service.getValue("SAP_CODE") + "/ 品名:" + service.getValue("MATERIAL") + " / 製造商:"
				+ service.getValue("MFR") + " / QR號碼:" + service.getValue("QR_NO") + " / 評估結果:"
				+ (service.getValue("EVALUATION_RESULT").equals("Y") ? "適用" : "不適用") + " / 供應商:"
				+ service.getValue("SUPPLIER");
	}

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

	public static void sendSubFlowMail(BaseService service, String mailTo, SampleEvaluationSubBaseDto dto, String title)
			throws Exception {
		MailService mailService = new MailService(service);
		// Mail to
		String[] ret = mailTo.trim().split(" ");
		String[] usr = { service.getEmail(ret[0]) };

		// 內容
		EmailNotify en = new EmailNotify();
		en.setService(service);
		String content = en.getContent(dto);
		new MailMan(mailService).send(usr, new MailBody(title, content));
	}
}
