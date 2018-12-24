package oa.SampleEvaluationTp.flow.approve;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class Approve extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"
		talk t = getTalk();

		BaseDao service = new SampleEvaluationTpService(t);
		SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
		tp.setOwnPno(tp.getPno() + "TP");
		service.update(tp);

		service = new SampleEvaluationCheckService(t);
		SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(), this);
		ck.setOwnPno(ck.getPno() + "CHECK");
		service.update(ck);

		service = new SampleEvaluationService(t);
		SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
		service.update(se);
		return true;

	}

}
