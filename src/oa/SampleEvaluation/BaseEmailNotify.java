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
		// ����i�J�y�{���A���ݥD�ޮ�,�|���楻�q�{��
		// �i�ΥH�H�oEmail�q�������P��Ʈw�L�������~
		// �p�G�n���ʸ�Ʈw�A��ĳ��b�y�{�w�B�z�{�Ǥ��A�� addToTransaction(sql); �H�T�O��Ʈw���ʤ@�P.
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

		// ���e
		String content = buildContent();
		content += "" + getHisOpinion() + Mail.HTML_LINE_BREAK;
		mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);

	}

	public abstract void setSelf();

	protected void setIsLastGate() {
		isLastGate = false;
	}

	protected abstract String buildContent();

	// �N���O��
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

	// �γ��N�X������W��
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
			return "�hñ�q���G" + this.getFunctionName() + "( �渹�G" + getValue("PNO") + " )";
		} else {
			return "ñ�ֳq���G" + this.getFunctionName() + "( �渹�G" + getValue("PNO") + " )";
		}
	}

	protected String emailTitleBuilderForFinalGate() {

		return "���׳q���G" + this.getFunctionName() + "( �渹�G" + getValue("PNO") + " )";

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
