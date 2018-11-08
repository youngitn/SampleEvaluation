package oa.SampleEvaluationTp.flow.approve;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckDaoImpl;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpDaoImpl;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

import com.ysp.service.BaseService;

import jcx.jform.bProcFlow;

public class Approve extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"
		BaseService service = new BaseService(this);
		SampleEvaluationTpDaoImpl tpDao = new SampleEvaluationTpDaoImpl(getTalk());
		SampleEvaluationTp tp = new SampleEvaluationTp();
		tp.setAllValue(service);
		tpDao.update(tp);
		// message(tp.getFile1());

		SampleEvaluationCheckDaoImpl ckDao = new SampleEvaluationCheckDaoImpl(getTalk());
		SampleEvaluationCheck ck = new SampleEvaluationCheck();
		ck.setAllValue(service);
		ckDao.update(ck);

		SampleEvaluationDaoImpl seDao = new SampleEvaluationDaoImpl(getTalk());
		SampleEvaluation se = new SampleEvaluation();
		se.setAllValue(service);
		seDao.update(se);
		return true;

	}

}
