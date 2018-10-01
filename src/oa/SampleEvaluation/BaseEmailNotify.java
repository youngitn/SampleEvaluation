package oa.SampleEvaluation;

import jcx.jform.bNotify;
import jcx.jform.bProcFlow;

import java.sql.SQLException;

import java.util.*;

import org.junit.internal.Throwables;

import jcx.util.*;

import com.ysp.field.Mail;
import com.ysp.service.MailService; // 1.MailService  
import com.ysp.service.BaseService; // 1.MailService  

public abstract class BaseEmailNotify extends bNotify {

	protected MailService mailService;
	protected String[] usr;
	protected boolean isLastGate;
	protected bNotify bn = null;
	protected bProcFlow bpf = null;

	public void actionPerformed(String value) throws Throwable {
		// 當表單進入流程狀態直屬主管時,會執行本段程式
		// 可用以寄發Email通知等等與資料庫無關的做業
		// 如果要異動資料庫，建議放在流程預處理程序中，用 addToTransaction(sql); 以確保資料庫異動一致.
		// MailService
		setSelf();

		if (getbNotify() == null && getbProcFlow() != null) {
			ServiceGo(bpf);
		} else if (getbNotify() != null && getbProcFlow() == null) {
			ServiceGo(bn);
		}

		setIsLastGate();
		String title = emailTitleBuilder();
		// Mail to
		if (isLastGate) {
			usr = getAllSignedPeopleEmailForLastGateToSend();
			title = emailTitleBuilderForFinalGate();
		} else {
			usr = mailService.getMailAddresseeByEngagedPeople();
			title = emailTitleBuilder();
		}

		// 內容
		String content = buildContent();
		content += "" + getHisOpinion() + Mail.HTML_LINE_BREAK;
		mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);

	}

	public abstract void setSelf();

	protected void setIsLastGate() {
		isLastGate = false;
	}

	protected abstract String buildContent();

	// 意見記錄
	protected String getHisOpinion() {
		String[][] his = getFlowHistory();
		String value = "";
		for (int i = 1; i < his.length; i++) {
			if (!"AUTO".equals(his[i][3])) {
				value += getName(his[i][1]) + "(" + convert.FormatedDate(his[i][2].substring(0, 9), "/") + ":"
						+ his[i][3] + ");" + Mail.HTML_LINE_BREAK;
			} else {
			}
		}
		return value;
	}

	// 用單位代碼抓→單位名稱
	protected String getDepName(String dep_no) throws SQLException, Exception {
		String sql = "select DEP_NAME from DEP_ACTIVE_VIEW where DEP_NO = '" + dep_no + "'";
		String rec[][] = getTalk().queryFromPool(sql);
		if (rec.length > 0) {
			return rec[0][0];
		} else {
			return dep_no;
		}
	}

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

	protected void ServiceGo(bNotify bn) {
		BaseService service = new BaseService(bn);
		mailService = new MailService(service);
	}

	protected void ServiceGo(bProcFlow bpf) {
		BaseService service = new BaseService(bpf);
		mailService = new MailService(service);
	}

	public void setbNotify(bNotify bn) {
		this.bn = bn;

	}

	public void setbProcFlow(bProcFlow bpf) {
		this.bpf = bpf;

	}

	public bNotify getbNotify() {
		return bn;

	}

	public bProcFlow getbProcFlow() {
		return bpf;

	}

	protected String[] getAllSignedPeopleEmailForLastGateToSend() throws SQLException, Exception {
		String[][] vid = getFlowHistory();
		String ausr[] = new String[vid.length];
		for (int i = 0; i < vid.length; i++) {
			ausr[i] = vid[i][1].trim();
		}
		HashSet set = new HashSet();
		set.addAll(Arrays.asList(ausr));
		String usr[] = (String[]) set.toArray(new String[0]);

		Vector V2 = new Vector();
		for (int i = 0; i < usr.length; i++) {

			V2.addElement(getEmail(usr[i]));
		}

		return (String[]) V2.toArray(new String[0]);
	}

	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}
}
