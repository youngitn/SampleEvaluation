package oa.SampleEvaluation.subflowbuilder;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import oa.SampleEvaluation.common.Flowc;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcHisService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowc;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowcHis;

public class TpFlowBuilder extends SubFlowBuilder {

	@Override
	public void setAndInsertFlowData() {
		Flowc secf = new SampleEvaluationTpFlowc();
		secf = (SampleEvaluationTpFlowc) setFlowData(secf, gateName, DateTimeUtil.getApproveAddSeconds(0));
		Flowc secfh = new SampleEvaluationTpFlowcHis();
		secfh = (SampleEvaluationTpFlowcHis) setFlowData(secfh, secf.getfInpStat(), secf.getfInpTime());

		try {
			BaseDao service = new SampleEvaluationTpFlowcService(t);
			service.upsert(secf);
			service = new SampleEvaluationTpFlowcHisService(t);
			service.upsert(secfh);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setOwnPno() {
		this.se.setOwnPno(this.se.getPno() + "TP");

	}

	@Override
	public void insertSubMainData() throws Exception {
		SampleEvaluationTpService secs = new SampleEvaluationTpService(t);
		// insert check•D¿…
		secs.upsert(this.se);

	}

}
