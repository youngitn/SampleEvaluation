package oa.SampleEvaluation.subflowbuilder.builder;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowcHis;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestFlowcHisService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestFlowcService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTest.dto.SampleEvaluationTest;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowc;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowcHis;
import oa.global.BaseDao;

public class TestFlowBuilder extends SubFlowBuilder {
	public TestFlowBuilder() {
		this.startGateName = "配合人員";
	}

	@Override
	public void setAndInsertFlowData() throws SQLException, Exception {
		insertFlowData(new SampleEvaluationTestFlowc());
		insertFlowData(new SampleEvaluationTestFlowcHis());
	}
//	public void setAndInsertFlowData() {
//		Flowc secf = new SampleEvaluationTestFlowc();
//		secf = (SampleEvaluationTestFlowc) setFlowData(secf, gateName, DateTimeUtil.getApproveAddSeconds(0));
//		Flowc secfh = new SampleEvaluationTestFlowcHis();
//		secfh = (SampleEvaluationTestFlowcHis) setFlowData(secfh, secf.getfInpStat(), secf.getfInpTime());
//
//		try {
//			BaseDao service = new SampleEvaluationTestFlowcService(t);
//			service.upsert(secf);
//			service = new SampleEvaluationTestFlowcHisService(t);
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
		SampleEvaluationTestService secs = new SampleEvaluationTestService(t);
		SampleEvaluationTest seTestDto = (SampleEvaluationTest) this.se;
		seTestDto.setOwnPno(seTestDto.getOwnPno());
		// insert Test主檔
		secs.upsert(this.se);
		this.ownPno = seTestDto.getOwnPno();

	}

	public boolean isReady(Object form) {

		if (form instanceof hproc) {

			checkEmpty(((hproc) form).getValue("COORDINATOR").trim());
			checkEmpty(((hproc) form).getValue("IS_TEST").trim());

		}
		if (form instanceof bProcFlow) {

			checkEmpty(((bProcFlow) form).getValue("COORDINATOR").trim());
			checkEmpty(((bProcFlow) form).getValue("IS_TEST").trim());
		}
		if (al.indexOf(false) == -1) {
			return true;
		} else {
			return false;
		}

	}
}
