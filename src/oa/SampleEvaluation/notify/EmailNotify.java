package oa.SampleEvaluation.notify;

import java.io.IOException;
import java.sql.SQLException;

import jcx.util.convert;
import oa.SampleEvaluation.enums.AppTypeEnum;
import oa.SampleEvaluation.enums.UrgencyEnum;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.MailString;
import oa.global.UserData;

/**
 * The Class EmailNotify.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class EmailNotify extends BaseEmailNotify {

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.notify.BaseEmailNotify#buildContent(oa.SampleEvaluation.model.SampleEvaluationPO)
	 */
	@Override
	public String buildContent(SampleEvaluationPO se) throws SQLException, Exception {
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
		if (!this.isLastGate) {
			content += "您有一筆 " + service.getFunctionName() + " 等待簽核；" + MailString.HTML_LINE_BREAK;
			content += "請進入 Emaker 應用服務系統 " + MailString.getOaSystemUrl() + " 簽核。" + MailString.HTML_LINE_BREAK;
		}

		content += MailString.HTML_LINE_BREAK;
		content += MailString.MAIL_CONTENT_LINE_WORD + MailString.HTML_LINE_BREAK;
		content += "單號：" + se.getPno() + MailString.HTML_LINE_BREAK;
		content += "申請日期：" + convert.FormatedDate(se.getAppDate(), "/") + MailString.HTML_LINE_BREAK;
		content += "申請人：" + depName + " " + name + "(" + appUser.getEmpid() + ")" + MailString.HTML_LINE_BREAK;
		content += "申請類型：" + AppTypeEnum.getAppType(se.getAppType()) + MailString.HTML_LINE_BREAK;
		content += "急迫性：" + UrgencyEnum.getUrgency(se.getUrgency()) + MailString.HTML_LINE_BREAK;
		content += "物料名稱：" + se.getMaterial() + MailString.HTML_LINE_BREAK;

		content += "受理單位：" + emailUtil.getDepName(se.getReceiptUnit()) + MailString.HTML_LINE_BREAK;

		content += "計畫代號：" + se.getProjectCode() + MailString.HTML_LINE_BREAK;

		String projectLeaderLine = "";
		if (projectLeaderUserDate != null) {
			projectLeaderLine = projectLeaderUserDate.getDepName() + " " + projectLeaderUserDate.getHecname() + " ("
					+ projectLeaderUserDate.getEmpid() + ") ";
		}

		content += "計畫主持人：" + projectLeaderLine + MailString.HTML_LINE_BREAK;

		// content += "<br>" + buildApproveConfirmMsgStr(se) +
		// MailString.HTML_LINE_BREAK;
		content += "==========================" + MailString.HTML_LINE_BREAK;
		content += "此郵件由系統自動發出，請勿回信，謝謝!!" + MailString.HTML_LINE_BREAK;
		content += "意見記錄：" + MailString.HTML_LINE_BREAK;

		return content;
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.notify.BaseEmailNotify#setIsLastGate()
	 */
	@Override
	protected void setIsLastGate() {
		this.isLastGate = false;
	}

	/**
	 * Builds the approve confirm msg str.
	 *
	 * @param se [SampleEvaluationPO]
	 * @return  [String]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected String buildApproveConfirmMsgStr(SampleEvaluationPO se) throws IOException {
		String alertStr = "";
		String isCheckStr = se.getIsCheck();
		String isTrialPrdStr = se.getIsTrialProduction();
		if (isCheckStr.equals("0") || isCheckStr.equals("")) {

			alertStr += "未進行請驗流程;<br>";
		} else {

			alertStr += "將進行請驗流程;<br>";
		}
		if (isTrialPrdStr.equals("0") || isTrialPrdStr.equals("")) {

			alertStr += "未進行試製評估流程;<br>";
		} else {

			alertStr += "將進行試製評估流程;<br>";
		}

		return alertStr;
	}

	
	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.notify.BaseEmailNotify#changeMailToUsr()
	 */
	@Override
	protected void changeMailToUsr() {
		// TODO Auto-generated method stub

	}

}