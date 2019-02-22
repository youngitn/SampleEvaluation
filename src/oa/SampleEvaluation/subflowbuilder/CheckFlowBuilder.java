package oa.SampleEvaluation.subflowbuilder;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import oa.SampleEvaluation.i.Flowc;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisService;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcService;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowcHis;
import oa.global.BaseDao;

public class CheckFlowBuilder extends SubFlowBuilder {

	@Override
	public void setAndInsertFlowData() {
		Flowc secf = new SampleEvaluationCheckFlowc();
		secf = (SampleEvaluationCheckFlowc) setFlowData(secf, gateName, DateTimeUtil.getApproveAddSeconds(0));
		Flowc secfh = new SampleEvaluationCheckFlowcHis();
		secfh = (SampleEvaluationCheckFlowcHis) setFlowData(secfh, secf.getfInpStat(), secf.getfInpTime());

		try {
			BaseDao service = new SampleEvaluationCheckFlowcService(t);
			service.upsert(secf);
			service = new SampleEvaluationCheckFlowcHisService(t);
			service.upsert(secfh);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void insertSubMainData() throws Exception {
		SampleEvaluationCheckService secs = new SampleEvaluationCheckService(t);
		SampleEvaluationCheck seCheckDto = (SampleEvaluationCheck) this.se;
		seCheckDto.setOwnPno(this.se.getPno() + this.ownPnoType);
		// insert check主檔
		secs.upsert(this.se);

	}

	@Override
	public void setStartGateName() {
		this.gateName = "文管人員";

	}

	@Override
	public void setSubFlowOwnPnoType() {
		this.ownPnoType = "CHECK";

	}

}
