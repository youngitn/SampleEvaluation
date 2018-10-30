package oa.SampleEvaluation.notify;

import oa.SampleEvaluation.enums.*;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;

import java.io.IOException;
import java.sql.SQLException;
import jcx.util.*;
import oa.SampleEvaluation.common.DebugUtil;
import oa.SampleEvaluation.common.EmailUtil;
import oa.SampleEvaluation.common.UserData;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;

import com.ysp.field.Mail;
import com.ysp.service.BaseService;

public class EmailNotify extends BaseEmailNotify {

	SampleEvaluation sc;

	public void setService(BaseService service) {
		this.service = service;
		this.emailUtil = new EmailUtil(service);
	}

	@Override
	protected String buildContent() throws SQLException, Exception {
		// ���e
		UserData appUser = new UserData(sc.getApplicant(), service.getTalk());
		UserData projectLeaderUserDate = null;
		if (!sc.getProjectLeader().equals("")) {
			projectLeaderUserDate = new UserData(sc.getProjectLeader(), service.getTalk());
		}
		String name = appUser.getHecname();
		String depName = appUser.getDepName();
		String content = "";
		content += "�z���@�� " + service.getFunctionName() + " ����ñ�֡F" + Mail.HTML_LINE_BREAK;
		content += "�жi�J Emaker ���ΪA�Ȩt�� " + Mail.getOaSystemUrl() + " ñ�֡C" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "�渹�G" + sc.getPno() + Mail.HTML_LINE_BREAK;
		content += "�ӽФ���G" + convert.FormatedDate(sc.getAppDate(), "/") + Mail.HTML_LINE_BREAK;
		content += "�ӽФH�G" + depName + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "�ӽ������G" + AppType.getAppType(sc.getAppType()) + Mail.HTML_LINE_BREAK;
		content += "�止�ʡG" + Urgency.getUrgency(sc.getUrgency()) + Mail.HTML_LINE_BREAK;
		content += "���ƦW�١G" + sc.getMaterial() + Mail.HTML_LINE_BREAK;
		String appConfMsg = "";

		content += "���z���G" + emailUtil.getDepName(sc.getReceiptUnit()) + Mail.HTML_LINE_BREAK;
		appConfMsg = buildApproveConfirmMsgStr();

		content += "�p�e�N���G" + sc.getProjectCode() + Mail.HTML_LINE_BREAK;

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
		String isCheckStr = sc.getIsCheck();
		String isTrialPrdStr = sc.getIsTrialProduction();
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

	public void setTableObj(SampleEvaluationSubBaseDto sc) {
		this.sc = sc;
	}

}