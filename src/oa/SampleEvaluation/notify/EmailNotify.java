package oa.SampleEvaluation.notify;

import java.io.IOException;
import java.sql.SQLException;

import jcx.util.convert;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.enums.AppType;
import oa.SampleEvaluation.enums.Urgency;
import oa.global.MailString;
import oa.global.UserData;

public class EmailNotify extends BaseEmailNotify {

	@Override
	public String buildContent(SampleEvaluation se) throws SQLException, Exception {
		try {
			if (t == null) {
				throw new ArithmeticException("talk ����");
			}
		} catch (ArithmeticException e) {
			System.out.println("�ҥ~���e: " + e.getMessage());
		}
		// ���e
		UserData appUser = new UserData(se.getApplicant(), t);
		UserData projectLeaderUserDate = null;
		if (!se.getProjectLeader().equals("")) {
			projectLeaderUserDate = new UserData(se.getProjectLeader(), service.getTalk());
		}
		String name = appUser.getHecname();
		String depName = appUser.getDepName();
		String content = "";
		if (!this.isLastGate) {
			content += "�z���@�� " + service.getFunctionName() + " ����ñ�֡F" + MailString.HTML_LINE_BREAK;
			content += "�жi�J Emaker ���ΪA�Ȩt�� " + MailString.getOaSystemUrl() + " ñ�֡C" + MailString.HTML_LINE_BREAK;
		}

		content += MailString.HTML_LINE_BREAK;
		content += MailString.MAIL_CONTENT_LINE_WORD + MailString.HTML_LINE_BREAK;
		content += "�渹�G" + se.getPno() + MailString.HTML_LINE_BREAK;
		content += "�ӽФ���G" + convert.FormatedDate(se.getAppDate(), "/") + MailString.HTML_LINE_BREAK;
		content += "�ӽФH�G" + depName + " " + name + "(" + appUser.getEmpid() + ")" + MailString.HTML_LINE_BREAK;
		content += "�ӽ������G" + AppType.getAppType(se.getAppType()) + MailString.HTML_LINE_BREAK;
		content += "�止�ʡG" + Urgency.getUrgency(se.getUrgency()) + MailString.HTML_LINE_BREAK;
		content += "���ƦW�١G" + se.getMaterial() + MailString.HTML_LINE_BREAK;

		content += "���z���G" + emailUtil.getDepName(se.getReceiptUnit()) + MailString.HTML_LINE_BREAK;

		content += "�p�e�N���G" + se.getProjectCode() + MailString.HTML_LINE_BREAK;

		String projectLeaderLine = "";
		if (projectLeaderUserDate != null) {
			projectLeaderLine = projectLeaderUserDate.getDepName() + " " + projectLeaderUserDate.getHecname() + " ("
					+ projectLeaderUserDate.getEmpid() + ") ";
		}

		content += "�p�e�D���H�G" + projectLeaderLine + MailString.HTML_LINE_BREAK;

		// content += "<br>" + buildApproveConfirmMsgStr(se) +
		// MailString.HTML_LINE_BREAK;
		content += "==========================" + MailString.HTML_LINE_BREAK;
		content += "���l��Ѩt�Φ۰ʵo�X�A�ФŦ^�H�A����!!" + MailString.HTML_LINE_BREAK;
		content += "�N���O���G" + MailString.HTML_LINE_BREAK;

		return content;
	}

	@Override
	protected void setIsLastGate() {
		this.isLastGate = false;
	}

	protected String buildApproveConfirmMsgStr(SampleEvaluation se) throws IOException {
		String alertStr = "";
		String isCheckStr = se.getIsCheck();
		String isTrialPrdStr = se.getIsTrialProduction();
		if (isCheckStr.equals("0") || isCheckStr.equals("")) {

			alertStr += "���i�����y�{;<br>";
		} else {

			alertStr += "�N�i�����y�{;<br>";
		}
		if (isTrialPrdStr.equals("0") || isTrialPrdStr.equals("")) {

			alertStr += "���i��ջs�����y�{;<br>";
		} else {

			alertStr += "�N�i��ջs�����y�{;<br>";
		}

		return alertStr;
	}

	public String getContent(SampleEvaluation dto) throws SQLException, Exception {

		return buildContent(dto);
	}

	@Override
	protected void changeMailToUsr() {
		// TODO Auto-generated method stub

	}

}