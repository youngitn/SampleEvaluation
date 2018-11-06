package oa.SampleEvaluationTp.flow.approve;

import oa.SampleEvaluation.dao.AbstractGenericFlowcDao;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpDaoImpl;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcDaoImpl;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcHisDaoImpl;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowc;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowcHis;
import oa.SampleEvaluationTp.flow.approve.gateEnum.*;
import jcx.jform.bProcFlow;

import com.ysp.service.BaseService;
import com.ysp.util.DateTimeUtil;

import jcx.db.*;

public class Approve extends bProcFlow {

	String table_name = "MIS_SERVICE";

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"

		String state = getState();
		talk t = getTalk();

		switch (FlowState.valueOf(state)) {
		case 填寫試製單號:

			if (getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				message("請填寫試製單號");
				return false;
			}
			// 更新主表試製單號欄位
			t.execFromPool("UPDATE  sample_evaluation  SET notify_no_check=?" + " where pno=?",
					new Object[] { getValue("NOTIFY_NO_TRIAL_PROD"), getValue("PNO") });

			// 更新子流程主表試製單號欄位
			t.execFromPool("UPDATE  sample_evaluation_tp  SET notify_no_check=?" + " where own_pno=?",
					new Object[] { getValue("NOTIFY_NO_TRIAL_PROD"), getValue("OWN_PNO") });
			message("簽核完成！");
			break;
		case 組長:

			// 建立子流程FLOWC物件 使其出現在待簽核表單列表
			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")) {
				BaseService service = new BaseService(this);
				SampleEvaluationSubBaseDto sc = new SampleEvaluationTp(service);
				SampleEvaluationTpDaoImpl checkDao = new SampleEvaluationTpDaoImpl(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update((SampleEvaluationTp) sc);
				} else {
					// insert一筆子流程主檔
					checkDao.add((SampleEvaluationTp) sc);

					SampleEvaluationTpFlowc flowc = new SampleEvaluationTpFlowc(sc.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					// 取得被分案組長empid
					String[] designee = getValue("DESIGNEE").trim().split(" ");
					flowc.setF_INP_ID(designee[0]);
					flowc.setF_INP_STAT("填寫試製單號");
					flowc.setF_INP_TIME(time);
					AbstractGenericFlowcDao secfDao = new SampleEvaluationTpFlowcDaoImpl();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// 建立子流程FLOWC_HIS 物件 能夠顯示簽核歷史
					time = DateTimeUtil.getApproveAddSeconds(0);
					SampleEvaluationTpFlowcHis his = new SampleEvaluationTpFlowcHis(sc.getOwnPno(),
							flowc.getF_INP_STAT(), time);

					his.setF_INP_ID(designee[0]);
					SampleEvaluationTpFlowcHisDaoImpl secfhDao = new SampleEvaluationTpFlowcHisDaoImpl();
					secfhDao.create(getTalk().getConnectionFromPool(), his);

				}
			}
			break;
		default:

			break;
		}
		return true;

	}

}
