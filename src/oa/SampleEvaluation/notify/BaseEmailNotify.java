package oa.SampleEvaluation.notify;

import jcx.db.talk;
import jcx.jform.bNotify;
import jcx.jform.bProcFlow;

import oa.SampleEvaluation.common.EmailUtil;
import oa.SampleEvaluation.common.MailBody;

import oa.SampleEvaluation.common.MailMan;
import oa.SampleEvaluation.dto.SampleEvaluation;

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
	protected talk t;

	public void actionPerformed(String value) throws Throwable {
		// 當表單進入流程狀態直屬主管時,會執行本段程式
		// 可用以寄發Email通知等等與資料庫無關的做業
		// 如果要異動資料庫，建議放在流程預處理程序中，用 addToTransaction(sql); 以確保資料庫異動一致.
		// MailService

		// 大部分表單動作皆委派給service

		service = new BaseService(this);
		emailUtil = new EmailUtil(service);
		mailService = new MailService(service);
		SampleEvaluation se = new SampleEvaluation();
		se.setAllValue(service);
		this.t = service.getTalk();
		if(this.t == null) {
			this.t = getTalk();
		}
		// 建立內容
		String content = buildContent(se);
		content += "" + emailUtil.getHisOpinion() + Mail.HTML_LINE_BREAK;

		// 取得待簽核人
		usr = mailService.getMailAddresseeByEngagedPeople();
		// 判斷是否結案通知
		setIsLastGate();
		String title = emailTitleBuilder();
		setIsLastGate();
		if (isLastGate) {
			// 取得簽核過的所有人
			usr = emailUtil.getAllSignedPeopleEmailForLastGateToSend();
			title = emailTitleBuilderForFinalGate();
		}
		MailBody mBody = new MailBody(title, content);
		MailMan m = new MailMan(mailService);
		m.send(usr, mBody);

	}

	public void setService(BaseService service) {
		this.service = service;
		this.emailUtil = new EmailUtil(service);
		this.t = service.getTalk();
	}

	public void setBaseService(BaseService service) {
		this.service = service;
		this.t = service.getTalk();

	}

	public void setEmailUtil(EmailUtil eu) {
		this.emailUtil = eu;

	}

	public void setTalk(talk t) {
		this.t = t;

	}
	

	protected void setIsLastGate() {
		isLastGate = false;
	}

	public abstract String buildContent(SampleEvaluation se) throws SQLException, Exception;

	protected String emailTitleBuilder() {
		String title = "";
		if (mailService.isReject()) {
			title = "退簽通知：";
		} else {
			title = "簽核通知：";
		}
		return title + this.getFunctionName() + "( 單號：" + getValue("PNO") + " )";
	}

	protected String emailTitleBuilderForFinalGate() {

		return "結案通知：" + this.getFunctionName() + "( 單號：" + getValue("PNO") + " )";

	}

	@Override
	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}
}
