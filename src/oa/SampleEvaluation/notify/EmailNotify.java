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
		content += "�ӽ������G" + AppTypeEnum.getAppType(se.getAppType()) + MailString.HTML_LINE_BREAK;
		content += "�止�ʡG" + UrgencyEnum.getUrgency(se.getUrgency()) + MailString.HTML_LINE_BREAK;
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

	
	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.notify.BaseEmailNotify#changeMailToUsr()
	 */
	@Override
	protected void changeMailToUsr() {
		// TODO Auto-generated method stub

	}

}