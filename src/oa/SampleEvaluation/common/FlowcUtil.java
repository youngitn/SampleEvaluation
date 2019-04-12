package oa.SampleEvaluation.common;

import java.sql.SQLException;

import com.ysp.util.DateTimeUtil;

import jcx.db.talk;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcPO;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckFlowcHisService;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckFlowcService;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckFlowcHisPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcPO;
import oa.SampleEvaluationTp.model.SampleEvaluationTpFlowcHisPO;
import oa.SampleEvaluationTp.service.SampleEvaluationTpFlowcHisService;
import oa.SampleEvaluationTp.service.SampleEvaluationTpFlowcService;
import oa.global.BaseDao;

/**
 * The Class FlowcUtil.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class FlowcUtil {

	/**
	 * Instantiates a new flowc util.
	 */
	private FlowcUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Go check sub flow.
	 *
	 * @param ownPno [String]
	 * @param applicant [String]
	 * @param gateName [String]
	 * @param t [talk]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public static void goCheckSubFlow(String ownPno, String applicant, String gateName, talk t)
			throws SQLException, Exception {

		SampleEvaluationCheckFlowcPO secf = new SampleEvaluationCheckFlowcPO();
		secf.setOwnPno(ownPno);
		secf.setfInpId(applicant);
		secf.setfInpStat(gateName);
		secf.setfInpTime(DateTimeUtil.getApproveAddSeconds(0));
		SampleEvaluationCheckFlowcHisPO secfh = new SampleEvaluationCheckFlowcHisPO();
		secfh.setOwnPno(ownPno);
		secfh.setfInpId(applicant);
		secfh.setfInpStat(gateName);
		secfh.setfInpTime(secf.getfInpTime());

		BaseDao service = new SampleEvaluationCheckFlowcService(t);
		service.upsert(secf);
		service = new SampleEvaluationCheckFlowcHisService(t);
		service.upsert(secfh);

	}

	/**
	 * Go tp sub flow.
	 *
	 * @param ownPno [String]
	 * @param applicant [String]
	 * @param gateName [String]
	 * @param t [talk]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public static void goTpSubFlow(String ownPno, String applicant, String gateName, talk t)
			throws SQLException, Exception {

		SampleEvaluationTpFlowcPO secf = new SampleEvaluationTpFlowcPO();
		secf.setOwnPno(ownPno);
		secf.setfInpId(applicant);
		secf.setfInpStat(gateName);
		secf.setfInpTime(DateTimeUtil.getApproveAddSeconds(0));

		SampleEvaluationTpFlowcHisPO secfh = new SampleEvaluationTpFlowcHisPO();
		secfh.setOwnPno(ownPno);
		secfh.setfInpId(applicant);
		secfh.setfInpStat(gateName);
		secfh.setfInpTime(secf.getfInpTime());

		BaseDao service = new SampleEvaluationTpFlowcService(t);
		service.upsert(secf);
		service = new SampleEvaluationTpFlowcHisService(t);
		service.upsert(secfh);
	}

}
