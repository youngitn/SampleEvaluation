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
				throw new ArithmeticException("talk 為空");
			}
		} catch (ArithmeticException e) {
			System.out.println("例外內容: " + e.getMessage());
		}
		// 內容
		UserData appUser = new UserData(se.getApplicant(), t);
		UserData projectLeaderUserDate = null;
		if (!se.getProjectLeader().equals("")) {
			projectLeaderUserDate = new UserData(se.getProjectLeader(), service.getTalk());
		}
		String name = appUser.getHecname();
		String depName = appUser.getDepName();
		String content = "";
		content += "您有一筆 " + service.getFunctionName() + " 等待簽核；" + Mail.HTML_LINE_BREAK;
		content += "請進入 Emaker 應用服務系統 " + Mail.getOaSystemUrl() + " 簽核。" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "單號：" + se.getPno() + Mail.HTML_LINE_BREAK;
		content += "申請日期：" + convert.FormatedDate(se.getAppDate(), "/") + Mail.HTML_LINE_BREAK;
		content += "申請人：" + depName + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "申請類型：" + AppType.getAppType(se.getAppType()) + Mail.HTML_LINE_BREAK;
		content += "急迫性：" + Urgency.getUrgency(se.getUrgency()) + Mail.HTML_LINE_BREAK;
		content += "物料名稱：" + se.getMaterial() + Mail.HTML_LINE_BREAK;

		content += "受理單位：" + emailUtil.getDepName(se.getReceiptUnit()) + Mail.HTML_LINE_BREAK;

		content += "計畫代號：" + se.getProjectCode() + Mail.HTML_LINE_BREAK;

		String projectLeaderLine = "";
		if (projectLeaderUserDate != null) {
			projectLeaderLine = projectLeaderUserDate.getDepName() + " " + projectLeaderUserDate.getHecname() + " ("
					+ projectLeaderUserDate.getEmpid() + ") ";
		}

		content += "計畫主持人：" + projectLeaderLine + Mail.HTML_LINE_BREAK;

		content += "是否進行請驗/試製流程：<br>" + buildApproveConfirmMsgStr(se) + Mail.HTML_LINE_BREAK;
		content += "==========================" + Mail.HTML_LINE_BREAK;
		content += "此郵件由系統自動發出，請勿回信，謝謝!!" + Mail.HTML_LINE_BREAK;
		content += "意見記錄：" + Mail.HTML_LINE_BREAK;

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

	public String getContent(SampleEvaluation dto) throws SQLException, Exception {

		return buildContent(dto);
	}

}