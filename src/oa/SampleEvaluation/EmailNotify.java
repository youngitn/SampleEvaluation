package oa.SampleEvaluation;

import oa.SampleEvaluation.enums.*;

import java.sql.SQLException;

import jcx.jform.bNotify;
import jcx.jform.bProcFlow;
import jcx.util.*;
import oa.SampleEvaluation.common.UserData;

import com.ysp.field.Mail;

public class EmailNotify extends BaseEmailNotify {

	@Override
	protected String buildContent() {
		// TODO Auto-generated method stub
		// 內容
		UserData appUser = null;
		try {
			// TODO for TEST
			appUser = new UserData(getValue("APPLICANT"), getTalk());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String name = appUser.getHecname();
		String dep_name = appUser.getDep_name();
		String content = "";
		content += "您有一筆 " + getFunctionName() + " 等待簽核；" + Mail.HTML_LINE_BREAK;
		content += "請進入 Emaker 應用服務系統 " + Mail.getOaSystemUrl() + " 簽核。" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "單號：" + getValue("PNO") + Mail.HTML_LINE_BREAK;
		content += "申請日期：" + convert.FormatedDate(getValue("APP_DATE"), "/") + Mail.HTML_LINE_BREAK;
		content += "申請人：" + dep_name + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "申請類型：" + AppType.getAppType(getValue("APP_TYPE")) + Mail.HTML_LINE_BREAK;
		content += "急迫性：" + Urgency.getUrgency(getValue("URGENCY")) + Mail.HTML_LINE_BREAK;
		content += "物料名稱：" + getValue("MATERIAL") + Mail.HTML_LINE_BREAK;
		try {
			content += "受理單位：" + getDepName(getValue("RECEIPT_UNIT")) + Mail.HTML_LINE_BREAK;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		content += "計畫代號：" + getValue("PROJECT_CODE") + Mail.HTML_LINE_BREAK;
		String projectLeaderLine = "";
		if (!getValue("PROJECT_LEADER").trim().equals("")) {
			UserData u = null;
			try {
				u = new UserData(getValue("PROJECT_LEADER").trim(), getTalk());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			projectLeaderLine = u.getDep_name() + u.getHecname() + "(" + u.getEmpid() + ") ";
		}

		content += "計畫主持人：" + projectLeaderLine + Mail.HTML_LINE_BREAK;

		content += "是否進行請驗/試製流程：<br>" + buildApproveConfirmMsgStr() + Mail.HTML_LINE_BREAK;
		content += "==========================" + Mail.HTML_LINE_BREAK;
		content += "此郵件由系統自動發出，請勿回信，謝謝!!" + Mail.HTML_LINE_BREAK;
		content += "意見記錄：" + Mail.HTML_LINE_BREAK;

		return content;
	}

	@Override
	protected void setIsLastGate() {
		this.isLastGate = false;
	}

	@Override
	public void setSelf() {
		// TODO Auto-generated method stub
		this.bn = this;

	}

	protected String buildApproveConfirmMsgStr() {
		String alertStr = "";
		if (getValue("IS_CHECK").equals("0")) {

			alertStr += "將不會進行請驗流程;<br>";
		} else {

			alertStr += "將進行請驗流程;<br>";
		}
		if (getValue("IS_TRIAL_PRODUCTION").equals("0")) {

			alertStr += "將不會進行試製評估流程;<br>";
		} else {

			alertStr += "將進行試製評估流程;<br>";
		}

		return alertStr;
	}

}