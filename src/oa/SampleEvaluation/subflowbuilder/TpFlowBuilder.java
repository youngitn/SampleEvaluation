package oa.SampleEvaluation.subflowbuilder;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import oa.SampleEvaluation.common.Flowc;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcHisService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
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
	public void insertSubMainData() throws Exception {
		SampleEvaluationTpService secs = new SampleEvaluationTpService(t);
		SampleEvaluationTp seTpDto = (SampleEvaluationTp) this.se;
		seTpDto.setOwnPno(seTpDto.getPno() + this.ownPnoType);
		// insert check主檔
		secs.upsert(this.se);

	}

	@Override
	public void setStartGateName(String gateName) {
		this.gateName = "試製人員";

	}

	@Override
	public void setSubFlowOwnPnoType() {
		this.ownPnoType = "TP";

	}

}
