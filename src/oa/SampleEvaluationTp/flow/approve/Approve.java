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
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"

		String state = getState();
		talk t = getTalk();

		switch (FlowState.valueOf(state)) {
		case ��g�ջs�渹:

			if (getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				message("�ж�g�ջs�渹");
				return false;
			}
			// ��s�D��ջs�渹���
			t.execFromPool("UPDATE  sample_evaluation  SET notify_no_check=?" + " where pno=?",
					new Object[] { getValue("NOTIFY_NO_TRIAL_PROD"), getValue("PNO") });

			// ��s�l�y�{�D��ջs�渹���
			t.execFromPool("UPDATE  sample_evaluation_tp  SET notify_no_check=?" + " where own_pno=?",
					new Object[] { getValue("NOTIFY_NO_TRIAL_PROD"), getValue("OWN_PNO") });
			message("ñ�֧����I");
			break;
		case �ժ�:

			// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��
			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")) {
				BaseService service = new BaseService(this);
				SampleEvaluationSubBaseDto sc = new SampleEvaluationTp(service);
				SampleEvaluationTpDaoImpl checkDao = new SampleEvaluationTpDaoImpl(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update((SampleEvaluationTp) sc);
				} else {
					// insert�@���l�y�{�D��
					checkDao.add((SampleEvaluationTp) sc);

					SampleEvaluationTpFlowc flowc = new SampleEvaluationTpFlowc(sc.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					// ���o�Q���ײժ�empid
					String[] designee = getValue("DESIGNEE").trim().split(" ");
					flowc.setF_INP_ID(designee[0]);
					flowc.setF_INP_STAT("��g�ջs�渹");
					flowc.setF_INP_TIME(time);
					AbstractGenericFlowcDao secfDao = new SampleEvaluationTpFlowcDaoImpl();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// �إߤl�y�{FLOWC_HIS ���� ������ñ�־��v
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
