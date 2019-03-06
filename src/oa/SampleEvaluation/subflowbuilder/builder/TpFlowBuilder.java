package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowcHis;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowc;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowcHis;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcHisService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowc;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowcHis;
import oa.global.BaseDao;

public class TpFlowBuilder extends SubFlowBuilder {

	public TpFlowBuilder() {
		this.startGateName = "試製人員";
	}

	@Override
	public void setAndInsertFlowData() throws SQLException, Exception {
		insertFlowData(new SampleEvaluationTpFlowc());
		insertFlowData(new SampleEvaluationTpFlowcHis());
	}
//	public void setAndInsertFlowData() {
//		Flowc secf = new SampleEvaluationTpFlowc();
//		secf = (SampleEvaluationTpFlowc) setFlowData(secf, gateName, DateTimeUtil.getApproveAddSeconds(0));
//		Flowc secfh = new SampleEvaluationTpFlowcHis();
//		secfh = (SampleEvaluationTpFlowcHis) setFlowData(secfh, secf.getfInpStat(), secf.getfInpTime());
//
//		try {
//			BaseDao service = new SampleEvaluationTpFlowcService(t);
//			service.upsert(secf);
//			service = new SampleEvaluationTpFlowcHisService(t);
//			service.upsert(secfh);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	@Override
	public void insertSubMainData() throws Exception {
		SampleEvaluationTpService secs = new SampleEvaluationTpService(t);
		SampleEvaluationTp seTpDto = (SampleEvaluationTp) this.se;
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
