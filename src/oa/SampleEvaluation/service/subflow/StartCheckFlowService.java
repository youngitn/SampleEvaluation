package oa.SampleEvaluation.service.subflow;

// oa/SampleEvaluation/subflowbuilder/StartCheckFlow
import com.ysp.service.BaseService;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.CheckFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcHisPO;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcPO;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

/**
 * The Class StartCheckFlowService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class StartCheckFlowService extends HprocImpl {

	String isCheckValue;
	String docCtrlerCheck;
	String qcBoss;
	talk t;

	@Override
	public String action(String arg0) throws Throwable {

		SubFlowBuilder sfb = null;
		sfb = new CheckFlowBuilder();
		if (!sfb.isReady(this)) {
			message("����y�{������ޤH���P�~�O�Ҫ����Ҥ��o����");
			return arg0;
		}
		// ���n������
		setNeedValue();
		// �T�{�O�_�w�g����L�l�y�{
		BaseDao dao = new SampleEvaluationCheckService(t);
		SampleEvaluationCheckPO ck = (SampleEvaluationCheckPO) dao.findById(getValue("PNO") + "CHECK");
		if (ck != null) {
			message("�w����L����y�{");
			return arg0;
		}

		SyncDataService.mainFlowSync(t, this);

		String mailTitle = "ñ�ֳq���G" + this.getFunctionName();

		ck = (SampleEvaluationCheckPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationCheckPO(), this);
		ck.setOwnPno(ck.getPno() + "CHECK");
		Flowc flowc = new SampleEvaluationCheckFlowcPO();
		Flowc flowcHis = new SampleEvaluationCheckFlowcHisPO();
		sfb.setMainDto(ck);
		sfb.setTalk(t);
		sfb.construct(flowc, flowcHis);
		SyncDataService.subFlowSync(t, this);
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
