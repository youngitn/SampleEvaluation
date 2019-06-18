package oa.SampleEvaluation.test;

import jcx.jform.hproc;
import java.io.*;
import java.util.*;

import com.ysp.service.BaseService;

import jcx.util.*;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import jcx.html.*;
import jcx.db.*;

/**
 * The Class GGG.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class TestSendMail extends hproc {
	
	/* 以當前案件建立通知信並寄出.
	 * 需於dmaker頁面中建立按鈕執行.
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	public String action(String value) throws Throwable {
		SampleEvaluationPO s = new SampleEvaluationPO();
		s.getDataFromForm(this);
		String mailTitle = "簽核通知：" + this.getFunctionName();
		MailToolInApprove.sendSubFlowMail(new BaseService(this), s.getApplicant(), s, mailTitle);
		return value;
	}

	/**
	 * Gets the Information.
	 *
	 * @return [String]
	 */
	public String getInformation() {
		return "---------------button5(button5).html_action()----------------";
	}
}
