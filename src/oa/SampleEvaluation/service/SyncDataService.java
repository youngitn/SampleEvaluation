package oa.SampleEvaluation.service;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.model.SampleEvaluationTestPO;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

public class SyncDataService {
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
			SampleEvaluationTpPO tp = (SampleEvaluationTpPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationTpPO(), h);
			tp.setOwnPno(tp.getOwnPno());
			if (service.findById(tp.getOwnPno()) != null) {
				service.update(tp);
			}
		}
		if ("1".equals(isCheck)) {
			service = new SampleEvaluationCheckService(t);
			SampleEvaluationCheckPO ck = (SampleEvaluationCheckPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationCheckPO(),
					h);
			ck.setOwnPno(ck.getOwnPno());
			if (service.findById(ck.getOwnPno()) != null) {
				service.update(ck);
			}

		}

		if ("1".equals(isTest)) {
			service = new SampleEvaluationTestService(t);
			SampleEvaluationTestPO test = (SampleEvaluationTestPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationTestPO(),
					h);
			test.setOwnPno(test.getOwnPno());
			if (service.findById(test.getOwnPno()) != null) {
				service.update(test);
			}
		}

		service = new SampleEvaluationService(t);
		SampleEvaluationPO se = (SampleEvaluationPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationPO(), h);
		service.update(se);

		return true;

	}
}
