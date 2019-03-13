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

public class GGG extends hproc {
	public String action(String value) throws Throwable {
		SampleEvaluationPO s = new SampleEvaluationPO();
		s.getFormData(this);
		String mailTitle = "Ã±®Ö³qª¾¡G" + this.getFunctionName();
		MailToolInApprove.sendSubFlowMail(new BaseService(this), s.getApplicant(), s, mailTitle);
		return value;
	}

	public String getInformation() {
		return "---------------button5(button5).html_action()----------------";
	}
}
