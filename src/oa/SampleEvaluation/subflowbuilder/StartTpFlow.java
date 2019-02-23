package oa.SampleEvaluation.subflowbuilder;

// oa/SampleEvaluation/subflowbuilder/StartTpFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.common.SyncData;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class StartTpFlow extends HprocImpl {
	String isTrialProdValue;
	String docCtrlerTp;
	String assessor;
	String labExe;
	talk t;

	@Override
	public String action(String arg0) throws Throwable {
		setNeedValue();
		BaseDao dao = new SampleEvaluationTpService(t);
		SampleEvaluationTp tp = (SampleEvaluationTp) dao.findById(getValue("PNO") + "TP");
		if (tp != null) {
			message("�w����L�ջs�y�{");
			return arg0;
		}
		if ("1".equals(isTrialProdValue)) {
			if ("".equals(docCtrlerTp) || "".equals(assessor) || "".equals(labExe)) {
				message("�ջs�y�{������ޤH��,�ջs�H��,����H�����Ҥ��o����");
				return arg0;
			}
		}
		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataIntoDto(new SampleEvaluation(), this);
		daoservice.update(se);
		SubFlowBuilder sfb = null;
		String mailTitle = "ñ�ֳq���G" + this.getFunctionName();

		tp = (SampleEvaluationTp) DtoUtil.setFormDataIntoDto(new SampleEvaluationTp(), this);
		tp.setOwnPno(tp.getPno() + "TP");
		sfb = new TpFlowBuilder();
		sfb.setMainDto(tp);
		sfb.setTalk(t);
		sfb.construct();
		SyncData.subFlowSync(t, this);
		// ������y�{ �H�X�q���H
		String title = mailTitle + "_�ջs�y�{";
		MailToolInApprove.sendSubFlowMail(new BaseService(this), docCtrlerTp, tp, title);
		message("�w����");
		setValue("START_TP_FLOW_TEXT", "�w����!");
		setEditable("START_TP_FLOW", false);
		setEditable("IS_TRIAL_PRODUCTION", false);
		return arg0;
	}

	public void setNeedValue() throws Throwable {
		isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();
		docCtrlerTp = getValue("DOC_CTRLER_TP").trim();
		assessor = getValue("ASSESSOR").trim();
		labExe = getValue("LAB_EXE").trim();
		t = getTalk();
	}

}
