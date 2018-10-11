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
		// 內容
		UserData appUser = new UserData(service.getValue("APPLICANT"), service.getTalk());
		UserData projectLeaderUserDate = null;
		if (!service.getValue("PROJECT_LEADER").trim().equals("")) {
			projectLeaderUserDate = new UserData(service.getValue("PROJECT_LEADER").trim(), service.getTalk());
		}
		String name = appUser.getHecname();
		String depName = appUser.getDepName();
		String content = "";
		content += "您有一筆 " + service.getFunctionName() + " 等待簽核；" + Mail.HTML_LINE_BREAK;
		content += "請進入 Emaker 應用服務系統 " + Mail.getOaSystemUrl() + " 簽核。" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "單號：" + service.getValue("PNO") + Mail.HTML_LINE_BREAK;
		content += "申請日期：" + convert.FormatedDate(service.getValue("APP_DATE"), "/") + Mail.HTML_LINE_BREAK;
		content += "申請人：" + depName + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "申請類型：" + AppType.getAppType(service.getValue("APP_TYPE")) + Mail.HTML_LINE_BREAK;
		content += "急迫性：" + Urgency.getUrgency(service.getValue("URGENCY")) + Mail.HTML_LINE_BREAK;
		content += "物料名稱：" + service.getValue("MATERIAL") + Mail.HTML_LINE_BREAK;
		String appConfMsg = "";

		content += "受理單位：" + emailUtil.getDepName(service.getValue("RECEIPT_UNIT")) + Mail.HTML_LINE_BREAK;
		appConfMsg = buildApproveConfirmMsgStr();

		content += "計畫代號：" + service.getValue("PROJECT_CODE") + Mail.HTML_LINE_BREAK;

		String projectLeaderLine = "";
		if (projectLeaderUserDate != null) {
			projectLeaderLine = projectLeaderUserDate.getDepName() + " " + projectLeaderUserDate.getHecname() + " ("
					+ projectLeaderUserDate.getEmpid() + ") ";
		}

		content += "計畫主持人：" + projectLeaderLine + Mail.HTML_LINE_BREAK;

		content += "是否進行請驗/試製流程：<br>" + appConfMsg + Mail.HTML_LINE_BREAK;
		content += "==========================" + Mail.HTML_LINE_BREAK;
		content += "此郵件由系統自動發出，請勿回信，謝謝!!" + Mail.HTML_LINE_BREAK;
		content += "意見記錄：" + Mail.HTML_LINE_BREAK;

		return content;
	}

	// 開放 外部取用 信件內容建立功能
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

			alertStr += "將不會進行請驗流程;<br>";
		} else {

			alertStr += "將進行請驗流程;<br>";
		}
		if (isTrialPrdStr.equals("0") || isTrialPrdStr.equals("")) {

			alertStr += "將不會進行試製評估流程;<br>";
		} else {

			alertStr += "將進行試製評估流程;<br>";
		}

		return alertStr;
	}

}