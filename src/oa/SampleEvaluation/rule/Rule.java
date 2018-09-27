package oa.SampleEvaluation.rule;

import jcx.jform.bRule;

import java.util.*;

import jcx.db.*;

public class Rule extends bRule {
	public Vector getIDs(String value) throws Throwable {
		// �^�ǭȬ� Vector contails �ŦX�o���W�檺�b��
		// value �� "���ʲ��B�t�Фγ]�Ʋ��ʥӽг�_���o"
		String state = getState();
		String depNo = getData("RECEIPT_UNIT").trim(); // ���z��츹�X

		Vector id = new Vector();
		talk t = getTalk();
		// �Lenum �¦r��P�_
		if (state.equals("���z���D�ޤ���")) {

			String ret[][] = t.queryFromPool(
					" select DEP_CHIEF from DEP_ACTIVE_VIEW where DEP_NO = '" + depNo + "' and CPNYID = 'YT01' ");
			if (ret.length > 0) {
				id.addElement(ret[0][0]);

				id.addElement("admin");
			}

			return id;
		}
		// ���z���D�ީҤ������H��
		if (state.equals("�ժ�") || state.equals("�ջs�@�~���")) {

			String[] ret = getData("DESIGNEE").trim().split(" ");

			id.addElement("admin");
			id.addElement(ret[0]);

			return id;
		}
		if (state.equals("�����H��")) {

			id.addElement("admin");
			String[] u = getData("ASSESSOR").trim().split(" ");
			id.addElement(u[0]);
			return id;
		}

		if (state.equals("�ջs�渹��g")) {

			id.addElement("admin");

			return id;
		}

		if (state.equals("����Ǹg��")) {

			id.addElement("admin");
			String[] u = getData("LAB_EXE").trim().split(" ");
			id.addElement(u[0]);
			return id;
		}

//		if (state.equals("�ջs�@�~���")) {
//
//			id.addElement("admin");
//
//			return id;
//		}

		if (state.equals("����Ǹg��")) {

			id.addElement("admin");

			return id;
		}

		return id;
	}

	public String getInformation() {
		return "---------------\u8b93\u552e\u901a\u77e5\u55ae.Rule()----------------";
	}
}
