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
		// 當表單進入流程狀態直屬主管時,會執行本段程式
		// 可用以寄發Email通知等等與資料庫無關的做業
		// 如果要異動資料庫，建議放在流程預處理程序中，用 addToTransaction(sql); 以確保資料庫異動一致.
		// MailService

		// 大部分表單動作皆委派給service
		try {
			service = new BaseService(this);
			emailUtil = new EmailUtil(service);

			mailService = new MailService(service);

			setIsLastGate();

			// 取得信件內容標題
			String title = emailTitleBuilder();

			// 取得待簽核人
			usr = mailService.getMailAddresseeByEngagedPeople();

			// 判斷是否結案通知
			if (isLastGate) {
				// 取得簽核過的所有人
				usr = emailUtil.getAllSignedPeopleEmailForLastGateToSend();
				title = emailTitleBuilderForFinalGate();
			}

			// 建立內容
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
			return "退簽通知：" + this.getFunctionName() + "( 單號：" + getValue("PNO") + " )";
		} else {
			return "簽核通知：" + this.getFunctionName() + "( 單號：" + getValue("PNO") + " )";
		}
	}

	protected String emailTitleBuilderForFinalGate() {

		return "結案通知：" + this.getFunctionName() + "( 單號：" + getValue("PNO") + " )";

	}

	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}
}
