package oa.SampleEvaluationTp.flow.approve;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluation.flow.approve.Approve;

import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpDaoImpl;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

import com.ysp.service.BaseService;

import jcx.jform.bProcFlow;

public class DoCheckFlow extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"
		if (getValue("IS_CHECK").equals("0")) {

			BaseService service = new BaseService(this);
			SampleEvaluationSubBaseDto secDto = new SampleEvaluationCheck();
			secDto.setAllValue(service);
			Approve.goSubFlow("Check", secDto, getTalk());
			setValue("IS_CHECK", "1");
			String title = "ñ�ֳq���G" + this.getFunctionName() + "_����y�{" + "( �渹�G" + getValue("PNO") + " )";
			// ������y�{ �H�X�q���H
			Approve.sendSubFlowMail(service, getValue("DOC_CTRLER"), secDto, title);

			SampleEvaluationTpDaoImpl tpDao = new SampleEvaluationTpDaoImpl(getTalk());
			SampleEvaluationTp tp = new SampleEvaluationTp();
			tp.setAllValue(service);
			tpDao.update(tp);

			SampleEvaluationDaoImpl seDao = new SampleEvaluationDaoImpl(getTalk());
			SampleEvaluation se = new SampleEvaluation();
			se.setAllValue(service);
			seDao.update(se);
		} else {
			message("�Ӹջs�~�w�g����L�F");
			return false;
		}

		return true;

	}

}
