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

/**
 * The Class BaseEmailNotify.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public abstract class BaseEmailNotify extends bNotify {

	/** The mail service. */
	protected MailService mailService;
	
	/** The usr. */
	protected ArrayList<String> usr = new ArrayList<String>();
	
	/** The is last gate. */
	protected boolean isLastGate;
	
	/** The email util. */
	protected EmailUtil emailUtil;
	
	/** The service. */
	protected BaseService service;
	
	/** The t. */
	protected talk t;
	
	/** The se. */
	protected SampleEvaluationPO se;
	
	/** The content. */
	protected String content;

	/**
	 * Initialization.
	 */
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

	/* (non-Javadoc)
	 * @see jcx.jform.bNotify#actionPerformed(java.lang.String)
	 */
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

	/**
	 * Change mail to usr.
	 */
	protected abstract void changeMailToUsr();

	/**
	 * Sets the Service.
	 *
	 * @param service  void
	 */
	public void setService(BaseService service) {
		this.service = service;
		this.emailUtil = new EmailUtil(service);
		this.t = service.getTalk();
	}

	/**
	 * Sets the BaseService.
	 *
	 * @param service  void
	 */
	public void setBaseService(BaseService service) {
		this.service = service;
		this.t = service.getTalk();

	}

	/**
	 * Sets the EmailUtil.
	 *
	 * @param eu  void
	 */
	public void setEmailUtil(EmailUtil eu) {
		this.emailUtil = eu;

	}

	/**
	 * Sets the Talk.
	 *
	 * @param t  void
	 */
	public void setTalk(talk t) {
		this.t = t;

	}

	/**
	 * Sets the is last gate.
	 */
	protected void setIsLastGate() {
		isLastGate = false;
	}

	/**
	 * Builds the content.
	 *
	 * @param se [SampleEvaluationPO]
	 * @return  [String]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public abstract String buildContent(SampleEvaluationPO se) throws SQLException, Exception;

	/**
	 * Email title builder for final gate.
	 *
	 * @return  [String]
	 */
	protected String emailTitleBuilderForFinalGate() {

		return "結案通知：" + getFunctionName() + MailToolInApprove.titleText(service);

	}

	/* (non-Javadoc)
	 * @see jcx.jform.bBase#getInformation()
	 */
	@Override
	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}
}
