package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcHisPO;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;

public class TpFlowBuilder extends SubFlowBuilder {

	public TpFlowBuilder() {
		this.startGateName = "試製人員";
	}

	@Override
	public void setAndInsertFlowData() throws SQLException, Exception {
		insertFlowData(new SampleEvaluationTpFlowcPO());
		insertFlowData(new SampleEvaluationTpFlowcHisPO());
	}

	@Override
	public void insertSubMainData() throws Exception {
		SampleEvaluationTpService secs = new SampleEvaluationTpService(t);
		SampleEvaluationTpPO seTpDto = (SampleEvaluationTpPO) this.se;
		seTpDto.setOwnPno(seTpDto.getOwnPno());
		// insert check主檔
		secs.upsert(this.se);
		this.ownPno = seTpDto.getOwnPno();

	}

	public boolean isReady(Object form) {

		if (form instanceof hproc) {
			checkEmpty(((hproc) form).getValue("IS_TRIAL_PRODUCTION").trim());
			checkEmpty(((hproc) form).getValue("DOC_CTRLER_TP").trim());
			checkEmpty(((hproc) form).getValue("ASSESSOR").trim());
			checkEmpty(((hproc) form).getValue("LAB_EXE").trim());
		}
		if (form instanceof bProcFlow) {
			checkEmpty(((bProcFlow) form).getValue("IS_TRIAL_PRODUCTION").trim());
			checkEmpty(((bProcFlow) form).getValue("DOC_CTRLER_TP").trim());
			checkEmpty(((bProcFlow) form).getValue("ASSESSOR").trim());
			checkEmpty(((bProcFlow) form).getValue("LAB_EXE").trim());
		}
		if (al.indexOf(false) == -1) {
			return true;
		} else {
			return false;
		}

	}

}
