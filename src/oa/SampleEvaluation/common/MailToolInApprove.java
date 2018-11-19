package oa.SampleEvaluation.common;

import com.ysp.service.BaseService;
import com.ysp.service.MailService;

import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluation.notify.EmailNotify;

public class MailToolInApprove {

	public static String titleText(BaseService service) {

		return " �Ƹ�:" + service.getValue("SAP_CODE") + "/ �~�W:" + service.getValue("MATERIAL") + " / �s�y��:"
				+ service.getValue("MFR") + " / QR���X:" + service.getValue("QR_NO") + " / �������G:"
				+ (service.getValue("EVALUATION_RESULT").equals("Y") ? "�A��" : "���A��") + " / ������:"
				+ service.getValue("SUPPLIER");
	}

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

	public static void sendSubFlowMail(BaseService service, String mailTo, SampleEvaluationSubBaseDto dto, String title)
			throws Exception {
		MailService mailService = new MailService(service);
		// Mail to
		String[] ret = mailTo.trim().split(" ");
		String[] usr = { service.getEmail(ret[0]) };

		// ���e
		EmailNotify en = new EmailNotify();
		en.setService(service);
		String content = en.getContent(dto);
		new MailMan(mailService).send(usr, new MailBody(title, content));
	}
}
