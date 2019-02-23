package oa.SampleEvaluation.common;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTest.dto.SampleEvaluationTest;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class SyncData {
	public static boolean subFlowSync(talk t, Object h) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"
		String isTp = "";
		String isTest = "";
		String isCheck = "";
		if (h instanceof hproc) {
			isTp = ((hproc) h).getValue("IS_TRIAL_PRODUCTION").trim();
			isTest = ((hproc) h).getValue("IS_TEST").trim();
			isCheck = ((hproc) h).getValue("IS_CHECK").trim();
		}
		if (h instanceof bProcFlow) {
			isTp = ((bProcFlow) h).getValue("IS_TRIAL_PRODUCTION").trim();
			isTest = ((bProcFlow) h).getValue("IS_TEST").trim();
			isCheck = ((bProcFlow) h).getValue("IS_CHECK").trim();
		}
		BaseDao service = null;
		if ("1".equals(isTp)) {
			service = new SampleEvaluationTpService(t);
			SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataIntoDto(new SampleEvaluationTp(), h);
			tp.setOwnPno(tp.getPno() + "TP");
			if (service.findById(tp.getPno() + "TP") != null) {
				service.update(tp);
			}
		}
		if ("1".equals(isCheck)) {
			service = new SampleEvaluationCheckService(t);
			SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataIntoDto(new SampleEvaluationCheck(), h);
			ck.setOwnPno(ck.getPno() + "CHECK");
			if (service.findById(ck.getPno() + "CHECK") != null) {
				service.update(ck);
			}

		}

		if ("1".equals(isTest)) {
			service = new SampleEvaluationTestService(t);
			SampleEvaluationTest test = (SampleEvaluationTest) DtoUtil.setFormDataIntoDto(new SampleEvaluationTest(), h);
			test.setOwnPno(test.getPno() + "TEST");
			if (service.findById(test.getPno() + "TEST") != null) {
				service.update(test);
			}
		}

		service = new SampleEvaluationService(t);
		SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataIntoDto(new SampleEvaluation(), h);
		service.update(se);

		return true;

	}
}
