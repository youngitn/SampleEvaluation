package oa.SampleEvaluationCheck.rule;

import jcx.jform.bRule;
import oa.SampleEvaluationCheck.flow.approve.gateEnum.FlowState;

import java.util.*;

public class Rule extends bRule {
	public Vector<String> getIDs(String value) throws Throwable {
		// �^�ǭȬ� Vector contails �ŦX�o���W�檺�b��
		// value �� "���ʲ��B�t�Фγ]�Ʋ��ʥӽг�_���o"
		String state = getState();

		Vector<String> id = new Vector<String>();
		String[] u = null;
		// ���z���D�ީҤ������H��
		switch (FlowState.valueOf(state)) {
		case ��g����渹:
			id.addElement("admin");
			u = getData("DOC_CTRLER").trim().split(" ");
			id.addElement(u[0]);
			break;
		case ����Ǹg��:
			id.addElement("admin");
			u = getData("LAB_EXE").trim().split(" ");
			id.addElement(u[0]);

			break;
		case ����@�~���:
			u = getData("DESIGNEE").trim().split(" ");
			id.addElement("admin");
			id.addElement(u[0]);
			break;
		case �ժ�:
			u = getData("DESIGNEE").trim().split(" ");

			id.addElement("admin");
			id.addElement(u[0]);
			break;

		default:
			break;
		}

		return id;
	}

	public String getInformation() {
		return "---------------\u8b93\u552e\u901a\u77e5\u55ae.Rule()----------------";
	}
}
