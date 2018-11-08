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
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"
		if (getValue("IS_CHECK").equals("0")) {

			BaseService service = new BaseService(this);
			SampleEvaluationSubBaseDto secDto = new SampleEvaluationCheck();
			secDto.setAllValue(service);
			Approve.goSubFlow("Check", secDto, getTalk());
			setValue("IS_CHECK", "1");
			String title = "簽核通知：" + this.getFunctionName() + "_請驗流程" + "( 單號：" + getValue("PNO") + " )";
			// 有請驗流程 寄出通知信
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
			message("該試製品已經請驗過了");
			return false;
		}

		return true;

	}

}
