package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisService;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcService;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowcHis;
import oa.global.BaseDao;

public class CheckFlowBuilder extends SubFlowBuilder {

	public CheckFlowBuilder() {
		this.startGateName = "文管人員";

	}

	@Override
	public void setAndInsertFlowData() throws SQLException, Exception {
		insertFlowData(new SampleEvaluationCheckFlowc());
		insertFlowData(new SampleEvaluationCheckFlowcHis());
	}

	@Override
	public void insertSubMainData() throws Exception {

		SampleEvaluationCheckService secs = new SampleEvaluationCheckService(t);
		SampleEvaluationCheck seCheckDto = (SampleEvaluationCheck) this.se;
		seCheckDto.setOwnPno(seCheckDto.getOwnPno());
		// insert check主檔
		secs.upsert(this.se);
		this.ownPno = seCheckDto.getOwnPno();

	}

	public boolean isReady(Object form) {

		if (form instanceof hproc) {
			checkEmpty(((hproc) form).getValue("IS_CHECK").trim());
			checkEmpty(((hproc) form).getValue("DOC_CTRLER_CHECK").trim());
			checkEmpty(((hproc) form).getValue("QC_BOSS").trim());

		}
		if (form instanceof bProcFlow) {
			checkEmpty(((bProcFlow) form).getValue("IS_CHECK").trim());
			checkEmpty(((bProcFlow) form).getValue("DOC_CTRLER_CHECK").trim());
			checkEmpty(((bProcFlow) form).getValue("QC_BOSS").trim());
		}
		if (al.indexOf(false) == -1) {
			return true;
		} else {
			return false;
		}

	}

}
