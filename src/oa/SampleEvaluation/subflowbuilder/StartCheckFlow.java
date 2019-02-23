package oa.SampleEvaluation.subflowbuilder;

// oa/SampleEvaluation/subflowbuilder/StartCheckFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.common.SyncData;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class StartCheckFlow extends HprocImpl {
	String isCheckValue;
	String docCtrlerCheck;
	String qcBoss;

	talk t;

	@Override
	public String action(String arg0) throws Throwable {
		setNeedValue();
		BaseDao dao = new SampleEvaluationCheckService(t);
		SampleEvaluationCheck ck = (SampleEvaluationCheck) dao.findById(getValue("PNO") + "CHECK");
		if (ck != null) {
			message("�w����L����y�{");
			return arg0;
		}
		if ("1".equals(isCheckValue)) {
			if ("".equals(docCtrlerCheck) || "".equals(qcBoss)) {
				message("����y�{������ޤH���P�~�O�Ҫ����Ҥ��o����");
				return arg0;
			}
		}
		// update main data
		SampleEvaluationService daoservice = new SampleEvaluationService(t);
		SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataIntoDto(new SampleEvaluation(), this);
		daoservice.update(se);
		// start sub flow
		SubFlowBuilder sfb = null;
		String mailTitle = "ñ�ֳq���G" + this.getFunctionName();

		ck = (SampleEvaluationCheck) DtoUtil.setFormDataIntoDto(new SampleEvaluationCheck(), this);
		ck.setOwnPno(ck.getPno() + "CHECK");
		sfb = new CheckFlowBuilder();
		sfb.setMainDto(ck);
		sfb.setTalk(t);
		sfb.construct();
		SyncData.subFlowSync(t, this);
		// ������y�{ �H�X�q���H
		String title = mailTitle + "_����y�{";
		MailToolInApprove.sendSubFlowMail(new BaseService(this), docCtrlerCheck, ck, title);
		message("�w����");
		setValue("START_CHECK_FLOW_TEXT", "�w����!");
		setEditable("START_CHECK_FLOW", false);
		setEditable("IS_CHECK", false);
		return arg0;
	}

	public void setNeedValue() throws Throwable {
		isCheckValue = getValue("IS_CHECK").trim();
		docCtrlerCheck = getValue("DOC_CTRLER_CHECK").trim();
		qcBoss = getValue("QC_BOSS").trim();
		t = getTalk();
	}

}
