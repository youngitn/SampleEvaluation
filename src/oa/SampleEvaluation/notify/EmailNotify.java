package oa.SampleEvaluation.notify;

import oa.SampleEvaluation.enums.*;

import java.io.IOException;
import java.sql.SQLException;
import jcx.util.*;
import oa.SampleEvaluation.common.EmailUtil;
import oa.SampleEvaluation.common.UserData;

import com.ysp.field.Mail;
import com.ysp.service.BaseService;

public class EmailNotify extends BaseEmailNotify {

	public void setService(BaseService service) {
		this.service = service;
		this.emailUtil = new EmailUtil(service);
	}

	@Override
	protected String buildContent() throws SQLException, Exception {
		// ���e
		UserData appUser = new UserData(service.getValue("APPLICANT"), service.getTalk());
		UserData projectLeaderUserDate = null;
		if (!service.getValue("PROJECT_LEADER").trim().equals("")) {
			projectLeaderUserDate = new UserData(service.getValue("PROJECT_LEADER").trim(), service.getTalk());
		}
		String name = appUser.getHecname();
		String depName = appUser.getDepName();
		String content = "";
		content += "�z���@�� " + service.getFunctionName() + " ����ñ�֡F" + Mail.HTML_LINE_BREAK;
		content += "�жi�J Emaker ���ΪA�Ȩt�� " + Mail.getOaSystemUrl() + " ñ�֡C" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "�渹�G" + service.getValue("PNO") + Mail.HTML_LINE_BREAK;
		content += "�ӽФ���G" + convert.FormatedDate(service.getValue("APP_DATE"), "/") + Mail.HTML_LINE_BREAK;
		content += "�ӽФH�G" + depName + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "�ӽ������G" + AppType.getAppType(service.getValue("APP_TYPE")) + Mail.HTML_LINE_BREAK;
		content += "�止�ʡG" + Urgency.getUrgency(service.getValue("URGENCY")) + Mail.HTML_LINE_BREAK;
		content += "���ƦW�١G" + service.getValue("MATERIAL") + Mail.HTML_LINE_BREAK;
		String appConfMsg = "";

		content += "���z���G" + emailUtil.getDepName(service.getValue("RECEIPT_UNIT")) + Mail.HTML_LINE_BREAK;
		appConfMsg = buildApproveConfirmMsgStr();

		content += "�p�e�N���G" + service.getValue("PROJECT_CODE") + Mail.HTML_LINE_BREAK;

		String projectLeaderLine = "";
		if (projectLeaderUserDate != null) {
			projectLeaderLine = projectLeaderUserDate.getDepName() + " " + projectLeaderUserDate.getHecname() + " ("
					+ projectLeaderUserDate.getEmpid() + ") ";
		}

		content += "�p�e�D���H�G" + projectLeaderLine + Mail.HTML_LINE_BREAK;

		content += "�O�_�i�����/�ջs�y�{�G<br>" + appConfMsg + Mail.HTML_LINE_BREAK;
		content += "==========================" + Mail.HTML_LINE_BREAK;
		content += "���l��Ѩt�Φ۰ʵo�X�A�ФŦ^�H�A����!!" + Mail.HTML_LINE_BREAK;
		content += "�N���O���G" + Mail.HTML_LINE_BREAK;

		return content;
	}

	// �}�� �~������ �H�󤺮e�إߥ\��
	public String getContent() throws Exception {
		return buildContent();
	}

	@Override
	protected void setIsLastGate() {
		this.isLastGate = false;
	}

	protected String buildApproveConfirmMsgStr() throws IOException {
		String alertStr = "";
		String isCheckStr = getValue("IS_CHECK").trim();
		String isTrialPrdStr = getValue("IS_TRIAL_PRODUCTION").trim();
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

}