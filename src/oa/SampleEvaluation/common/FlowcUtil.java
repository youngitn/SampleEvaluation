package oa.SampleEvaluation.common;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import jcx.db.talk;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisService;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowcHis;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcHisService;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowc;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowcHis;

public class FlowcUtil {
	
	private FlowcUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void goCheckSubFlow(String ownPno, String applicant, String gateName, talk t)
			throws SQLException, Exception {

		SampleEvaluationCheckFlowc secf = new SampleEvaluationCheckFlowc();
		secf.setOwnPno(ownPno);
		secf.setfInpId(applicant);
		secf.setfInpStat(gateName);
		secf.setfInpTime(DateTimeUtil.getApproveAddSeconds(0));
		SampleEvaluationCheckFlowcHis secfh = new SampleEvaluationCheckFlowcHis();
		secfh.setOwnPno(ownPno);
		secfh.setfInpId(applicant);
		secfh.setfInpStat(gateName);
		secfh.setfInpTime(secf.getfInpTime());

		BaseDao service = new SampleEvaluationCheckFlowcService(t);
		service.add(secf);
		service = new SampleEvaluationCheckFlowcHisService(t);
		service.add(secfh);

	}

	public static void goTpSubFlow(String ownPno, String applicant, String gateName, talk t)
			throws SQLException, Exception {

		SampleEvaluationTpFlowc secf = new SampleEvaluationTpFlowc();
		secf.setOwnPno(ownPno);
		secf.setfInpId(applicant);
		secf.setfInpStat(gateName);
		secf.setfInpTime(DateTimeUtil.getApproveAddSeconds(0));
		SampleEvaluationTpFlowcHis secfh = new SampleEvaluationTpFlowcHis();
		secfh.setOwnPno(ownPno);
		secfh.setfInpId(applicant);
		secfh.setfInpStat(gateName);
		secfh.setfInpTime(secf.getfInpTime());
		BaseDao service = new SampleEvaluationTpFlowcService(t);
		service.add(secfh);
		service = new SampleEvaluationTpFlowcHisService(t);
		service.add(secf);
	}

}
