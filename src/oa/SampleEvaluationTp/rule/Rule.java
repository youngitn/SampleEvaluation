package oa.SampleEvaluationTp.rule;

import jcx.jform.bRule;
import oa.SampleEvaluationTp.flow.approve.gateEnum.FlowState;

import java.util.*;

public class Rule extends bRule {
	public Vector<String> getIDs(String value) throws Throwable {
		// �^�ǭȬ� Vector contails �ŦX�o���W�檺�b��
		// value �� "���ʲ��B�t�Фγ]�Ʋ��ʥӽг�_���o"
		String state = getState();
		Vector<String> id = new Vector<String>();
		String[] ret = null;
		switch (FlowState.valueOf(state)) {
		case �ժ�:
		case �ժ��T�{�O�_����:
		case �ջs�@�~��Z:
			ret = getData("DESIGNEE").trim().split(" ");
			id.addElement("admin");
			id.addElement(ret[0]);

			break;
		case ����Ǹg��:
			id.addElement("admin");
			ret = getData("LAB_EXE").trim().split(" ");
			id.addElement(ret[0]);
			break;
		case �����H��:
			id.addElement("admin");
			ret = getData("ASSESSOR").trim().split(" ");
			id.addElement(ret[0]);
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