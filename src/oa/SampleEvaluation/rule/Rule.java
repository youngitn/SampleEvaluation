package oa.SampleEvaluation.rule;

import java.util.Vector;

import jcx.db.talk;
import jcx.jform.bRule;

public class Rule extends bRule {
	public Vector<String> getIDs(String value) throws Throwable {
		// �^�ǭȬ� Vector contails �ŦX�o���W�檺�b��
		// value �� "���ʲ��B�t�Фγ]�Ʋ��ʥӽг�_���o"
		String state = getState();
		String depNo = getData("RECEIPT_UNIT").trim(); // ���z��츹�X

		Vector<String> id = new Vector<String>();
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
