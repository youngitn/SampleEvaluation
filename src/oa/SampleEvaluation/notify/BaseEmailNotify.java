package oa.SampleEvaluation.notify;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ysp.field.Mail;
import com.ysp.service.BaseService; // 1.MailService  
import com.ysp.service.MailService; // 1.MailService  

import jcx.db.talk;
import jcx.jform.bNotify;
import oa.SampleEvaluation.common.MailBody;
import oa.SampleEvaluation.common.MailMan;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.EmailUtil;

public abstract class BaseEmailNotify extends bNotify {

	protected MailService mailService;
	protected ArrayList<String> usr = new ArrayList<String>();
	protected boolean isLastGate;
	protected EmailUtil emailUtil;
	protected BaseService service;
	protected talk t;
	protected SampleEvaluationPO se;
	protected String content;

	public void initialization() {
		service = new BaseService(this);
		emailUtil = new EmailUtil(service);
		mailService = new MailService(service);
		setIsLastGate();
		se = new SampleEvaluationPO();
		se.getFormData(this);
		this.t = this.getTalk();
		if (this.t == null) {
			this.t = getTalk();
		}
	}

	public void actionPerformed(String value) throws Throwable {

		// try {
		initialization();
		// 建立內容
		String content = buildContent(se);
		content += "" + emailUtil.getHisOpinion() + Mail.HTML_LINE_BREAK;

		// 判斷是否結案通知
		String title = MailToolInApprove.emailTitleBuilder(service);

		if (isLastGate) {
			// 取得簽核過的所有人
			usr = emailUtil.getAllSignedPeopleEmailForLastGateToSend();
			title = emailTitleBuilderForFinalGate();
		} else {
			String[] arrUsr = mailService.getMailAddresseeByEngagedPeople();
			for (String string : arrUsr) {
				usr.add(string);
			}
		}
		changeMailToUsr();
		MailBody mBody = new MailBody(title, content);
		MailMan m = new MailMan(mailService);
		String[] mailTo = new String[usr.size()];
		m.send(usr.toArray(mailTo), mBody);

	}

	protected abstract void changeMailToUsr();

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

	public abstract String buildContent(SampleEvaluationPO se) throws SQLException, Exception;

	protected String emailTitleBuilderForFinalGate() {

		return "結案通知：" + getFunctionName() + MailToolInApprove.titleText(service);

	}

	@Override
	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}
}
