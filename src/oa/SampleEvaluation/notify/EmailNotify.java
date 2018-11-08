package oa.SampleEvaluation.notify;

import oa.SampleEvaluation.enums.*;
import java.io.IOException;
import java.sql.SQLException;
import jcx.util.*;
import oa.SampleEvaluation.common.global.UserData;
import oa.SampleEvaluation.dto.SampleEvaluation;
import com.ysp.field.Mail;

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
		content += "�z���@�� " + service.getFunctionName() + " ����ñ�֡F" + Mail.HTML_LINE_BREAK;
		content += "�жi�J Emaker ���ΪA�Ȩt�� " + Mail.getOaSystemUrl() + " ñ�֡C" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "�渹�G" + se.getPno() + Mail.HTML_LINE_BREAK;
		content += "�ӽФ���G" + convert.FormatedDate(se.getAppDate(), "/") + Mail.HTML_LINE_BREAK;
		content += "�ӽФH�G" + depName + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "�ӽ������G" + AppType.getAppType(se.getAppType()) + Mail.HTML_LINE_BREAK;
		content += "�止�ʡG" + Urgency.getUrgency(se.getUrgency()) + Mail.HTML_LINE_BREAK;
		content += "���ƦW�١G" + se.getMaterial() + Mail.HTML_LINE_BREAK;

		content += "���z���G" + emailUtil.getDepName(se.getReceiptUnit()) + Mail.HTML_LINE_BREAK;

		content += "�p�e�N���G" + se.getProjectCode() + Mail.HTML_LINE_BREAK;

		String projectLeaderLine = "";
		if (projectLeaderUserDate != null) {
			projectLeaderLine = projectLeaderUserDate.getDepName() + " " + projectLeaderUserDate.getHecname() + " ("
					+ projectLeaderUserDate.getEmpid() + ") ";
		}

		content += "�p�e�D���H�G" + projectLeaderLine + Mail.HTML_LINE_BREAK;

		content += "�O�_�i�����/�ջs�y�{�G<br>" + buildApproveConfirmMsgStr(se) + Mail.HTML_LINE_BREAK;
		content += "==========================" + Mail.HTML_LINE_BREAK;
		content += "���l��Ѩt�Φ۰ʵo�X�A�ФŦ^�H�A����!!" + Mail.HTML_LINE_BREAK;
		content += "�N���O���G" + Mail.HTML_LINE_BREAK;

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

			alertStr += "�N���|�i�����y�{;<br>";
		} else {

			alertStr += "�N�i�����y�{;<br>";
		}
		if (isTrialPrdStr.equals("0") || isTrialPrdStr.equals("")) {

			alertStr += "�N���|�i��ջs�����y�{;<br>";
		} else {

			alertStr += "�N�i��ջs�����y�{;<br>";
		}

		return alertStr;
	}

	public String getContent(SampleEvaluation dto) throws SQLException, Exception {

		return buildContent(dto);
	}

}