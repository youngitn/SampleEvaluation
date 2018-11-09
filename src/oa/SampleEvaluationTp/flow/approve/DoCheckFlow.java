package oa.SampleEvaluationTp.flow.approve;

import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluation.flow.approve.Approve;

import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpDaoImpl;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

import com.ysp.service.BaseService;

import jcx.jform.bProcFlow;

public class DoCheckFlow extends bProcFlow {

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"
		boolean ret = true;
		ret = doReminder("�N�s�W��T�ǻ��q���� ����ñ�֬y�{  ");
		if (ret) {

			if (getValue("IS_CHECK").equals("0")) {

				setValue("IS_CHECK", "1");
				BaseService service = new BaseService(this);
				SampleEvaluationSubBaseDto secDto = new SampleEvaluationCheck();
				secDto.setAllValue(service);
				Approve.goSubFlow("Check", secDto, getTalk());

				String title = "ñ�ֳq���G" + this.getFunctionName() + "_����y�{" + "( �渹�G" + getValue("PNO") + " )";
				// ������y�{ �H�X�q���H
				Approve.sendSubFlowMail(service, getValue("DOC_CTRLER"), secDto, title);

				SampleEvaluationTpDaoImpl tpDao = new SampleEvaluationTpDaoImpl(getTalk());
				SampleEvaluationTp tp = new SampleEvaluationTp();
				tp.setAllValue(service);
				tpDao.update(tp);

				SampleEvaluationDaoImpl seDao = new SampleEvaluationDaoImpl(getTalk());
				SampleEvaluation se = new SampleEvaluation();
				se.setAllValue(service);
				seDao.update(se);
				ret = true;
			} else if (getValue("IS_CHECK").equals("1")) {
				message("�Ӹջs�~�w�g����L�F");
				ret = false;
			}
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
