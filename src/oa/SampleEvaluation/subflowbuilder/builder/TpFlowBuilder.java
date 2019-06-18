package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcHisPO;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;

/**
 * The Class TpFlowBuilder.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class TpFlowBuilder extends SubFlowBuilder {

	/**
	 * Instantiates a new tp flow builder.
	 */
	public TpFlowBuilder() {
		this.startGateName = "試製人員";
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder#insertSubMainData()
	 */
	@Override
	public void insertSubMainData() throws Exception {
		SampleEvaluationTpService secs = new SampleEvaluationTpService(t);
		SampleEvaluationTpPO seTpDto = (SampleEvaluationTpPO) this.se;
		seTpDto.setOwnPno(seTpDto.getOwnPno());
		// insert check主檔
		secs.upsert(this.se);
		this.ownPno = seTpDto.getOwnPno();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder#isReady(java.lang.
	 * Object)
	 */
	public boolean isReady(Object form) {

		if (form instanceof hproc) {
			checkEmpty(((hproc) form).getValue("IS_TRIAL_PRODUCTION").trim());
			checkEmpty(((hproc) form).getValue("DOC_CTRLER_TP").trim());
			checkEmpty(((hproc) form).getValue("ASSESSOR").trim());
			// 子流程如勾選'其他'選項將簡化流程,
			// 送出時不檢查檢驗人員欄位,
			// 留空白時簽核的話會跳簽是為簡化
			if (!((hproc) form).getValue("IS_ELSE").equals("1"))
				checkEmpty(((hproc) form).getValue("LAB_EXE").trim());

		}
		if (form instanceof bProcFlow) {
			checkEmpty(((bProcFlow) form).getValue("IS_TRIAL_PRODUCTION").trim());
			checkEmpty(((bProcFlow) form).getValue("DOC_CTRLER_TP").trim());
			checkEmpty(((bProcFlow) form).getValue("ASSESSOR").trim());
			// 子流程如勾選'其他'選項將簡化流程,
			// 送出時不檢查檢驗人員欄位,
			// 留空白時簽核的話會跳簽是為簡化
			if (!((bProcFlow) form).getValue("IS_ELSE").equals("1"))
				checkEmpty(((bProcFlow) form).getValue("LAB_EXE").trim());
		}
		if (al.indexOf(false) == -1) {
			return true;
		} else {
			return false;
		}

	}

}
