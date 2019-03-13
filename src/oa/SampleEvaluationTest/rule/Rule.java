package oa.SampleEvaluationTest.rule;

import jcx.jform.bRule;
import oa.SampleEvaluationTest.flow.approve.gateEnum.FlowStateEnum;

import java.util.*;

public class Rule extends bRule {
	public Vector<String> getIDs(String value) throws Throwable {
		// �^�ǭȬ� Vector contails �ŦX�o���W�檺�b��
		// value �� "���ʲ��B�t�Фγ]�Ʋ��ʥӽг�_���o"
		String state = getState().trim();
		Vector<String> id = new Vector<String>();
		String[] ret = null;
		switch (FlowStateEnum.valueOf(state)) {

		case �t�X�H��:
			ret = getData("COORDINATOR").trim().split(" ");
			id.addElement("admin");
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
