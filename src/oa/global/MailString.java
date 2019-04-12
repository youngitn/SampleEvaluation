package oa.global;

import com.ysp.system.SystemConfig;

/**
 * The Class MailString.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class MailString {

	/** The Constant LINE_BREAK. */
	public static final String LINE_BREAK = "\r\n";
	
	/** The Constant TAB_SPACE. */
	public static final String TAB_SPACE = "　　";
	
	/** The Constant HTML_LINE_BREAK. */
	public static final String HTML_LINE_BREAK = "<BR>";
	
	/** The Constant ATTATCHMENTS_FOLDER. */
	public static final String ATTATCHMENTS_FOLDER = "babylon\\files\\";
	
	/** The Constant MAIL_SIGN_REMIND_WORD. */
	public static final String MAIL_SIGN_REMIND_WORD = "，請進入 eHR 系統簽核";
	
	/** The Constant MAIL_FEEECT. */
	public static final String MAIL_FEEECT = "已核准";
	
	/** The Constant MAIL_END. */
	public static final String MAIL_END = "已歸檔";
	
	/** The Constant MAIL_VOID. */
	public static final String MAIL_VOID = "已作廢";
	
	/** The Constant MAIL_CONTENT_LINE_WORD. */
	public static final String MAIL_CONTENT_LINE_WORD = "=========內容摘要=========";
	
	/** The Constant MAIL_CONTENT_TYPE. */
	public static final String MAIL_CONTENT_TYPE = "text/plain";
	
	/** The Constant MAIL_HTML_CONTENT_TYPE. */
	public static final String MAIL_HTML_CONTENT_TYPE = "text/html";
	
	/** The Constant OA_SENDER. */
	public static final String OA_SENDER = "ehr@yungshingroup.com";
	
	/** The Constant SMTP. */
	public static final String SMTP = "10.1.1.59,25,ehr,ehr123";
	
	/** The Constant OA_SYSTEM_URL_UAT. */
	static final String OA_SYSTEM_URL_UAT = "http://10.1.1.41/servlet/jform?em_step=0&file=hrm4w_yspt.dat,hrm4w_c.dat,yspOA.dat,hrm4w_yspt_out3.dat,ysphr.dat,PAYMENT.dat,funds.dat,SalesPeople.dat,yspOaNO1.dat";
	
	/** The Constant OA_SYSTEM_URL_PRD. */
	static final String OA_SYSTEM_URL_PRD = "http://ehr.yungshingroup.com";
	
	/** The Constant OA_SYSTEM_INTERNET_URL_PRD. */
	static final String OA_SYSTEM_INTERNET_URL_PRD = "http://ehr.yungshingroup.com";

	/**
	 * Gets the OaSystemUrl.
	 *
	 * @return [String]
	 */
	public static String getOaSystemUrl() {
		if (SystemConfig.getProduction()) {
			return OA_SYSTEM_URL_PRD;
		} else {
			return OA_SYSTEM_URL_UAT;
		}
	}

	/**
	 * Gets the OaSystemInternetUrl.
	 *
	 * @return [String]
	 */
	public static String getOaSystemInternetUrl() {
		return OA_SYSTEM_INTERNET_URL_PRD;
	}
}
