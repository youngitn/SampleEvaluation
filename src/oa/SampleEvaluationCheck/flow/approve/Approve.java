package oa.SampleEvaluationCheck.flow.approve;

import jcx.jform.bProcFlow;

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
		case ��g����渹:
			if (getValue("NOTIFY_NO_CHECK").trim().equals("")) {
				message("�ж�g����渹");
				return false;
			}
			// ��s�D�����渹���
			t.execFromPool("UPDATE  sample_evaluation  SET notify_no_check=?" + " where pno=?",
					new Object[] { getValue("NOTIFY_NO_CHECK"), getValue("PNO") });

			// ��s�l�y�{�D�����渹���
			t.execFromPool("UPDATE  sample_evaluation_check  SET notify_no_check=?" + " where own_pno=?",
					new Object[] { getValue("NOTIFY_NO_CHECK"), getValue("OWN_PNO") });

		default:
			break;
		}

		message("ñ�֧����I");
		return true;
	}

}
