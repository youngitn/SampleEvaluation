package oa.SampleEvaluationCheck.flow.approve;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.daointerface.IFlowcDao;
import oa.SampleEvaluation.dto.FlowcDto;
import oa.SampleEvaluation.dto.FlowcHisDto;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckDaoImpl;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcDaoImpl;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisDaoImpl;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.*;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpDaoImpl;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import jcx.jform.bProcFlow;

import com.ysp.service.BaseService;
import com.ysp.util.DateTimeUtil;

import jcx.db.*;

public class Approve extends bProcFlow {

	String table_name = "MIS_SERVICE";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"

		String state = getState();
		talk t = getTalk();
		boolean ret = doReminder("");
		switch (FlowState.valueOf(state)) {
		case ��g����渹:
		case ����Ǹg��:
			/*
			 * �P�_����渹���O�_�ŭȷ|�b��g����渹�³B�z����
			 *�����Ǹg��� �u�|�P�B��s�T�� 
			 * */
			
			if (getValue("NOTIFY_NO_CHECK").trim().equals("") || getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				message("�ж�g��Ʃθջs�~����渹,�p�G���i�����Ъ����b��줤��g��].");
				ret = false;
			}
			if (ret) {
				// �T��P�B
				BaseService service = new BaseService(this);

				SampleEvaluationTpDaoImpl tpDao = new SampleEvaluationTpDaoImpl(getTalk());
				SampleEvaluationTp tp = new SampleEvaluationTp();
				tp.setAllValue(service);
				tpDao.update(tp);
				// message(service.getValue("FIELD9") + " " + tp.getFile9());
				// message(tp.getFile1());

				SampleEvaluationCheckDaoImpl ckDao = new SampleEvaluationCheckDaoImpl(getTalk());
				SampleEvaluationCheck ck = new SampleEvaluationCheck();
				ck.setAllValue(service);
				ckDao.update(ck);

				SampleEvaluationDaoImpl seDao = new SampleEvaluationDaoImpl(getTalk());
				SampleEvaluation se = new SampleEvaluation();
				se.setAllValue(service);
				seDao.update(se);
				// message("ñ�֧����I");
			}
			return ret;

		case �ժ�:// �ثe���}��o�����d
			// ��h?�n�h�h��?
			// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��
			if (getValue("IS_CHECK").trim().equals("1")) {
				BaseService service = new BaseService(this);
				SampleEvaluationSubBaseDto sc = new SampleEvaluationCheck(service);
				SampleEvaluationCheckDaoImpl checkDao = new SampleEvaluationCheckDaoImpl(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update((SampleEvaluationCheck) sc);
				} else {
					// insert�@���l�y�{�D��
					checkDao.add((SampleEvaluationCheck) sc);

					FlowcDto flowc = new FlowcDto(sc.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					// ���o�Q���ײժ�empid
					String[] designee = getValue("DESIGNEE").trim().split(" ");
					flowc.setF_INP_ID(designee[0]);
					flowc.setF_INP_STAT("��g����渹");
					flowc.setF_INP_TIME(time);
					IFlowcDao secfDao = new SampleEvaluationCheckFlowcDaoImpl();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// �إߤl�y�{FLOWC_HIS ���� ������ñ�־��v
					time = DateTimeUtil.getApproveAddSeconds(0);
					FlowcHisDto his = new FlowcHisDto(sc.getOwnPno(), flowc.getF_INP_STAT(), time);

					his.setF_INP_ID(designee[0]);
					SampleEvaluationCheckFlowcHisDaoImpl secfhDao = new SampleEvaluationCheckFlowcHisDaoImpl();
					secfhDao.create(getTalk().getConnectionFromPool(), his);

				}
			}
			break;
		default:

			break;
		}
		return ret;

	}

	/**
	 * ���ɴ��� ���ǤJ �^��true/false
	 */
	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "�T�w�e�X���H", "���ɴ���", 0);
		if (result == 1) {
			message("�w�����e�X���");
			return false;
		}
		String space = "";
		for (int i = 0; i < 16; i++) {
			space += "&emsp;";
		}
		percent(100, space + "���e�X���A�еy��...<font color=white>");
		message("ñ�֧���");
		return true;
	}

}
