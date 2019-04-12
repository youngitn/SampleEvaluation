package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcHisPO;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcPO;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;

/**
 * The Class CheckFlowBuilder.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class CheckFlowBuilder extends SubFlowBuilder {

	boolean isChecked = false;

	/**
	 * Instantiates a new check flow builder.
	 */
	public CheckFlowBuilder() {
		this.startGateName = "��ޤH��";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder#
	 * setAndInsertFlowData()
	 */
	@Override
	public void setAndInsertFlowData() throws SQLException, Exception {
		insertFlowData(new SampleEvaluationCheckFlowcPO());
		insertFlowData(new SampleEvaluationCheckFlowcHisPO());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder#insertSubMainData()
	 */
	@Override
	public void insertSubMainData() throws Exception {

		SampleEvaluationCheckService secs = new SampleEvaluationCheckService(t);
		SampleEvaluationCheckPO seCheckDto = (SampleEvaluationCheckPO) this.se;
		seCheckDto.setOwnPno(seCheckDto.getOwnPno());
		// insert check�D��
		secs.upsert(this.se);
		this.ownPno = seCheckDto.getOwnPno();

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

			checkEmpty(((hproc) form).getValue("IS_CHECK").trim());
			checkEmpty(((hproc) form).getValue("DOC_CTRLER_CHECK").trim());
			//�l�y�{�p�Ŀ�'��L'�ﶵ�N²�Ƭy�{,
			//�e�X�ɤ��ˬdQC�D�����,
			//�d�ťծ�ñ�֪��ܷ|��ñ�O��²��
			if (!((hproc) form).getValue("IS_ELSE").equals("1"))
				checkEmpty(((hproc) form).getValue("QC_BOSS").trim());

		}
		if (form instanceof bProcFlow) {

			checkEmpty(((bProcFlow) form).getValue("IS_CHECK").trim());
			checkEmpty(((bProcFlow) form).getValue("DOC_CTRLER_CHECK").trim());
			//�l�y�{�p�Ŀ�'��L'�ﶵ�N²�Ƭy�{,
			//�e�X�ɤ��ˬdQC�D�����,
			//�d�ťծ�ñ�֪��ܷ|��ñ�O��²��
			if (!((bProcFlow) form).getValue("IS_ELSE").equals("1"))
				checkEmpty(((bProcFlow) form).getValue("QC_BOSS").trim());
		}
		if (al.indexOf(false) == -1) {
			return true;
		} else {
			return false;
		}

	}

}
