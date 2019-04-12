package oa.SampleEvaluation.rule;

import java.sql.SQLException;
import java.util.Vector;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bRule;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.enums.FlowStateEnum;
import oa.SampleEvaluation.service.SyncDataService;
import oa.SampleEvaluation.subflowbuilder.builder.CheckFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.SubFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TestFlowBuilder;
import oa.SampleEvaluation.subflowbuilder.builder.TpFlowBuilder;

/**
 * The Class Rule.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class Rule extends bRule {
	
	/* (non-Javadoc)
	 * @see jcx.jform.bRule#getIDs(java.lang.String)
	 */
	public Vector<String> getIDs(String value) throws Throwable {
		// �^�ǭȬ� Vector contails �ŦX�o���W�檺�b��
		String state = getState();
		String depNo = getData("RECEIPT_UNIT").trim(); // ���z��츹�X

		Vector<String> id = new Vector<String>();
		talk t = getTalk();
		// �Lenum �¦r��P�_
		switch (FlowStateEnum.valueOf(state)) {
		case ���z���D�ޤ���:
			String ret[][] = t.queryFromPool(
					" select DEP_CHIEF from DEP_ACTIVE_VIEW where DEP_NO = '" + depNo + "' and CPNYID = 'YT01' ");
			if (ret.length > 0) {
				id.addElement(ret[0][0]);

				id.addElement("admin");
			}
			break;
		case �ժ�:

			id.addElement(getData("DESIGNEE").trim().split(" ")[0]);
			id.addElement("admin");
			break;
		case ��ޤH��:
			// �ھڹ�����ñ�֤H���w�]��ޤH��
			String[][] doc = null;
			try {
				doc = getTalk()
						.queryFromPool("SELECT DOC_CTRLER_CHECK FROM SAMPLE_EVALUATION_SUB_FLOW_SIGN_MAP WHERE DEPNO='"
								+ getData("RECEIPT_UNIT") + "'");

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			id.addElement(doc[0][0].trim().split(" ")[0]);
			id.addElement("admin");
			break;
		default:
			break;
		}

		return id;
	}

	/* (non-Javadoc)
	 * @see jcx.jform.bBase#getInformation()
	 */
	public String getInformation() {
		return "---------------\u8b93\u552e\u901a\u77e5\u55ae.Rule()----------------";
	}
}
