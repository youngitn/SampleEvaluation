package oa.global;

import com.ysp.system.SystemConfig;

public class MailString {

	public static final String LINE_BREAK = "\r\n";
	public static final String TAB_SPACE = "　　";
	public static final String HTML_LINE_BREAK = "<BR>";
	public static final String ATTATCHMENTS_FOLDER = "babylon\\files\\";
	public static final String MAIL_SIGN_REMIND_WORD = "，請進入 eHR 系統簽核";
	public static final String MAIL_FEEECT = "已核准";
	public static final String MAIL_END = "已歸檔";
	public static final String MAIL_VOID = "已作廢";
	public static final String MAIL_CONTENT_LINE_WORD = "=========內容摘要=========";
	public static final String MAIL_CONTENT_TYPE = "text/plain";
	public static final String MAIL_HTML_CONTENT_TYPE = "text/html";
	public static final String OA_SENDER = "ehr@yungshingroup.com";
	public static final String SMTP = "10.1.1.59,25,ehr,ehr123";
	static final String OA_SYSTEM_URL_UAT = "http://10.1.1.41/servlet/jform?em_step=0&file=hrm4w_yspt.dat,hrm4w_c.dat,yspOA.dat,hrm4w_yspt_out3.dat,ysphr.dat,PAYMENT.dat,funds.dat,SalesPeople.dat,yspOaNO1.dat";
	static final String OA_SYSTEM_URL_PRD = "http://ehr.yungshingroup.com";
	static final String OA_SYSTEM_INTERNET_URL_PRD = "http://ehr.yungshingroup.com";

	public static String getOaSystemUrl() {
		if (SystemConfig.getProduction()) {
			return OA_SYSTEM_URL_PRD;
		} else {
			return OA_SYSTEM_URL_UAT;
		}
	}

	public static String getOaSystemInternetUrl() {
		return OA_SYSTEM_INTERNET_URL_PRD;
	}
}
