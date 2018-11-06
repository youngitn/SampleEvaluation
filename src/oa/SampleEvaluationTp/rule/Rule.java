package oa.SampleEvaluationTp.rule;

import jcx.jform.bRule;

import java.util.*;

public class Rule extends bRule {
	public Vector getIDs(String value) throws Throwable {
		// �^�ǭȬ� Vector contails �ŦX�o���W�檺�b��
		// value �� "���ʲ��B�t�Фγ]�Ʋ��ʥӽг�_���o"
		String state = getState();

		Vector id = new Vector();

		// ���z���D�ީҤ������H��
		if (state.equals("��g�ջs�渹")) {

			id.addElement("admin");
			String[] u = getData("DOC_CTRLER").trim().split(" ");
			id.addElement(u[0]);

			return id;
		}
		if (state.equals("����Ǹg��")) {

			id.addElement("admin");
			String[] u = getData("LAB_EXE").trim().split(" ");
			id.addElement(u[0]);
			return id;
		}

		if (state.equals("�ջs�@�~���")) {

			String[] ret = getData("DESIGNEE").trim().split(" ");

			id.addElement("admin");
			id.addElement(ret[0]);

			return id;
		}

		if (state.equals("�ժ�")) {

			String[] ret = getData("DESIGNEE").trim().split(" ");

			id.addElement("admin");
			id.addElement(ret[0]);

			return id;
		}

		return id;
	}

	public String getInformation() {
		return "---------------\u8b93\u552e\u901a\u77e5\u55ae.Rule()----------------";
	}
}
