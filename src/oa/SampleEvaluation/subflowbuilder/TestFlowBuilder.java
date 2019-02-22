package oa.SampleEvaluation.subflowbuilder;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestFlowcHisService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestFlowcService;
import oa.SampleEvaluationTest.dao.SampleEvaluationTestService;
import oa.SampleEvaluationTest.dto.SampleEvaluationTest;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowc;
import oa.SampleEvaluationTest.dto.SampleEvaluationTestFlowcHis;
import oa.global.BaseDao;

public class TestFlowBuilder extends SubFlowBuilder {

	@Override
	public void setAndInsertFlowData() {
		Flowc secf = new SampleEvaluationTestFlowc();
		secf = (SampleEvaluationTestFlowc) setFlowData(secf, gateName, DateTimeUtil.getApproveAddSeconds(0));
		Flowc secfh = new SampleEvaluationTestFlowcHis();
		secfh = (SampleEvaluationTestFlowcHis) setFlowData(secfh, secf.getfInpStat(), secf.getfInpTime());

		try {
			BaseDao service = new SampleEvaluationTestFlowcService(t);
			service.upsert(secf);
			service = new SampleEvaluationTestFlowcHisService(t);
			service.upsert(secfh);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void insertSubMainData() throws Exception {
		SampleEvaluationTestService secs = new SampleEvaluationTestService(t);
		SampleEvaluationTest seTestDto = (SampleEvaluationTest) this.se;
		seTestDto.setOwnPno(seTestDto.getPno() + this.ownPnoType);
		// insert Test主檔
		secs.upsert(this.se);

	}

	@Override
	public void setStartGateName() {
		this.gateName = "配合人員";

	}

	@Override
	public void setSubFlowOwnPnoType() {
		this.ownPnoType = "TEST";

	}

}
