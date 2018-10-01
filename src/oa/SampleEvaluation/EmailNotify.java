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
		// ���e
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
		content += "�z���@�� " + getFunctionName() + " ����ñ�֡F" + Mail.HTML_LINE_BREAK;
		content += "�жi�J Emaker ���ΪA�Ȩt�� " + Mail.getOaSystemUrl() + " ñ�֡C" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "�渹�G" + getValue("PNO") + Mail.HTML_LINE_BREAK;
		content += "�ӽФ���G" + convert.FormatedDate(getValue("APP_DATE"), "/") + Mail.HTML_LINE_BREAK;
		content += "�ӽФH�G" + dep_name + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "�ӽ������G" + AppType.getAppType(getValue("APP_TYPE")) + Mail.HTML_LINE_BREAK;
		content += "�止�ʡG" + Urgency.getUrgency(getValue("URGENCY")) + Mail.HTML_LINE_BREAK;
		content += "���ƦW�١G" + getValue("MATERIAL") + Mail.HTML_LINE_BREAK;
		try {
			content += "���z���G" + getDepName(getValue("RECEIPT_UNIT")) + Mail.HTML_LINE_BREAK;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		content += "�p�e�N���G" + getValue("PROJECT_CODE") + Mail.HTML_LINE_BREAK;
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

		content += "�p�e�D���H�G" + projectLeaderLine + Mail.HTML_LINE_BREAK;

		content += "�O�_�i�����/�ջs�y�{�G<br>" + buildApproveConfirmMsgStr() + Mail.HTML_LINE_BREAK;
		content += "==========================" + Mail.HTML_LINE_BREAK;
		content += "���l��Ѩt�Φ۰ʵo�X�A�ФŦ^�H�A����!!" + Mail.HTML_LINE_BREAK;
		content += "�N���O���G" + Mail.HTML_LINE_BREAK;

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

			alertStr += "�N���|�i�����y�{;<br>";
		} else {

			alertStr += "�N�i�����y�{;<br>";
		}
		if (getValue("IS_TRIAL_PRODUCTION").equals("0")) {

			alertStr += "�N���|�i��ջs�����y�{;<br>";
		} else {

			alertStr += "�N�i��ջs�����y�{;<br>";
		}

		return alertStr;
	}

}