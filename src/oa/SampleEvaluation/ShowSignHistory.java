package oa.SampleEvaluation;

import java.util.Vector;

import jcx.db.talk;
import jcx.jform.hproc;

import com.ysp.service.SignFlowHistoryService;
import com.ysp.util.DateFormatUtil;

public class ShowSignHistory extends hproc {

	// private Log log = LogUtil.getLog(this.getClass());

	public String action(String value) throws Throwable {

//		SignFlowHistoryService service = new SignFlowHistoryService(this);
//		service.doDefaultDisplaySignFlowHistory();
		getDefaultValue(value);
		return value;
	}

	public String getDefaultValue(String value) throws Throwable {
		// ���o�ثe�Ҧ��y�{�`�I
		String id = "a.PNO= '" + getValue("QUERY_LIST.PNO") + "'"; // �渹���
		String rec[][] = getFlowHistory(getFunctionName(), id); // getFlowHistory(String�\��W��, String���e�[�W a. �p
																// a.PNO='A12345678' )
		// �Y�Lñ�֬y�{�����ɰ���
		if (rec.length == 0 || rec == null) {
			setValue("text3", "<center><font size=\"4\" color=red>�Lñ�֬y�{����</font></center>");
			return value;
		}
		// ñ�֬y�{����
		talk t = getTalk();
		StringBuffer sb = new StringBuffer();
		String APPLYEMPNAME = "";
		sb.append("<div  style=\"float:left;\"><TABLE>");
		sb.append("<tr>"); // 1�C�}�l
		sb.append("<td nowrap align='right' width='150' style='border:solid 1px #003333';>"); // 1-1�x�s��(���e������ �k�e150 ���)
		sb.append("<p align='center'><font size='2'>�s�W-" + getFunctionName() + "</font></p></td>"); // ���e�m�� �r��j�p2
		sb.append("<td align='center' width='400'></td>"); // 1-2�x�s��
		sb.append("</tr>"); // 1�C����
		for (int i = 0; i < rec.length; i++) {
			sb.append("<tr>"); // 2�C�}�l
			sb.append(
					"<td align='center' width='150' height='28' style='background-image:url(/image/eip/down_alt.png);background-repeat:no-repeat;background-position: center;'><font size='2' color='#003333'></font></td>"); // 2-1�x�s��
			// sb.append("<td align='center'><font size='5' >&#8595;</font></td>");
			// //2-1�x�s��(²�檺�b�Y)
			// ���AUTO���B�m(AUTO���t�Τ�����)
			if ("AUTO".equals(rec[i][1])) {
				APPLYEMPNAME = "AUTO";
			} else {
				APPLYEMPNAME = getName(rec[i][1]);
			}

			// rec[i][0] rec[i][1] rec[i][2] rec[i][3]
			// �`�I�W�١B��֤H���B��֮ɶ��B��ַN��
			sb.append("<TD align='center' width='400' >");
			sb.append("<TABLE width='100%' height='100%' border='1'");
			sb.append("<TR>");
			sb.append("<TD width='20%' align='center'>ñ�֤H��</TD>");
			sb.append("<TD width='33%' align='center'>ñ�֮ɶ�</TD>");
			sb.append("<TD width='47%' align='center'>ñ�ַN��</TD>");
			sb.append("</TR>");
			sb.append("<TR>");
			sb.append("<TD align='center'><font size='2' color='#003333'>" + APPLYEMPNAME + "(" + rec[i][1]
					+ ")</font></TD>");
			sb.append("<TD align='center'><font size='2' color='#003333'>" + DateFormatUtil.formatAcDateTime(rec[i][2])
					+ "</font></TD>");
			sb.append("<TD align='center'><font size='2' color='#003333'>" + rec[i][3] + "</font></TD>");
			sb.append("</TR>");
			sb.append("</TABLE>");
			sb.append("</TD>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append(
					"<td nowrap align='center' width='150' height='82' style='background-image:url(/image/eip/circle1.gif);background-repeat:no-repeat;background-position: center;'><font size='2' color='#003333'>"
							+ rec[i][0] + "</font></td>");
		}
		StringBuffer sb1 = new StringBuffer();
		Vector people = getApprovablePeople(getFunctionID(), id);
		if (people != null) {
			if (people.size() != 0) {
				for (int j = 0; j < people.size(); j++) {
					if (j != 0)
						sb1.append(",");
					String id1 = (String) people.elementAt(j);
					String name1 = getName(id1);
					sb1.append(name1 + ":" + id1);
				}
				sb.append("<td align='left' nowrap width='400'><font size='2' color='#FF0000'>" + sb1.toString()
						+ "</font></td>");
			}
		}
		sb.append("</tr>");
		sb.append("</TABLE>");
		sb.append("</DIV>");
		// ���l�y�{�~�[�B�~��div
		String sql = "select a.own_pno from SAMPLE_EVALUATION_CHECK a,SAMPLE_EVALUATION_CHECK_flowc b where a.own_pno ='"
				+ getValue("QUERY_LIST.PNO") + "CHECK" + "' and a.own_pno=b.own_pno ";
		String ret[][] = t.queryFromPool(sql);
		StringBuffer subSb = new StringBuffer();
		if (ret.length > 0) {
			for (int i = 0; i < ret.length; i++) {
				String dev = "select F_INP_STAT,F_INP_ID,F_INP_TIME,F_INP_INFO from SAMPLE_EVALUATION_CHECK_flowc_HIS where own_pno= '"
						+ ret[i][0] + "' order  by F_INP_TIME ";
				String flow[][] = t.queryFromPool(dev);
				// ñ�֬y�{����
				subSb.append("<DIV style=\"float:left;background-color: pink\" ><TABLE>");
				subSb.append("<tr>"); // 1�C�}�l
				subSb.append("<td nowrap align='right' width='150' style='border:solid 1px #003333';>"); // 1-1�x�s��(���e������
																											// �k�e150 ���)
				subSb.append("<p align='center'><font size='2'>�l�y�{-�˫~�����ӽЧ@�~_����y�{</font></p></td>"); // ���e�m�� �r��j�p2
				subSb.append("<td align='center' width='400'></td>"); // 1-2�x�s��
				subSb.append("</tr>"); // 1�C����
				for (int j = 0; j < flow.length; j++) {
					subSb.append("<tr>"); // 2�C�}�l
					subSb.append(
							"<td align='center' width='150' height='28' style='background-image:url(/image/eip/down_alt.png);background-repeat:no-repeat;background-position: center;'><font size='2' color='#003333'></font></td>"); // 2-1�x�s��
					// sb.append("<td align='center'><font size='5' >&#8595;</font></td>");
					// //2-1�x�s��(²�檺�b�Y)
					// ���AUTO���B�m(AUTO���t�Τ�����)
					if ("AUTO".equals(flow[j][1])) {
						APPLYEMPNAME = "AUTO";
					} else {
						APPLYEMPNAME = getName(flow[j][1]);
					}

					// rec[i][0] rec[i][1] rec[i][2] rec[i][3]
					// �`�I�W�١B��֤H���B��֮ɶ��B��ַN��
					subSb.append("<TD align='center' width='400' >");
					subSb.append("<TABLE width='100%' height='100%' border='1'");
					subSb.append("<TR>");
					subSb.append("<TD width='20%' align='center'>ñ�֤H��</TD>");
					subSb.append("<TD width='33%' align='center'>ñ�֮ɶ�</TD>");
					subSb.append("<TD width='47%' align='center'>ñ�ַN��</TD>");
					subSb.append("</TR>");
					subSb.append("<TR>");
					subSb.append("<TD align='center'><font size='2' color='#003333'>" + APPLYEMPNAME + "(" + flow[j][1]
							+ ")</font></TD>");
					subSb.append("<TD align='center'><font size='2' color='#003333'>"
							+ DateFormatUtil.formatAcDateTime(flow[j][2]) + "</font></TD>");
					subSb.append("<TD align='center'><font size='2' color='#003333'>" + flow[j][3] + "</font></TD>");
					subSb.append("</TR>");
					subSb.append("</TABLE>");
					subSb.append("</TD>");
					subSb.append("</tr>");
					subSb.append("<tr>");
					subSb.append(
							"<td nowrap align='center' width='150' height='82' style='background-image:url(/image/eip/circle1.gif);background-repeat:no-repeat;background-position: center;'><font size='2' color='#003333'>"
									+ flow[j][0] + "</font></td>");
				}
				StringBuffer htm1 = new StringBuffer();
				String id2 = "a.NO= '" + ret[i][0] + "'";
				Vector people2 = getApprovablePeople("SAMPLE_EVALUATION_CHECK", id2);
				if (people2 != null) {
					if (people2.size() != 0) {
						for (int k = 0; k < people2.size(); k++) {
							if (k != 0)
								htm1.append(",");
							String no1 = (String) people2.elementAt(k);
							String name2 = getName(no1);
							htm1.append(name2 + ":" + no1);
						}
						subSb.append("<td align='left' nowrap width='400'><font size='2' color='#FF0000'>"
								+ htm1.toString() + "</font></td>");
					}
				}
				subSb.append("</tr>"); 
				subSb.append("</TABLE></DIV>");
			}
			subSb.append("</DIV>");

		}
		setValue("text3", "<table ><tr><td valign=\"top\">" + sb.toString() + "</td><td valign=\"top\">"
				+ subSb.toString() + "<td></tr></table>");

		return value;
	}

	public String getInformation() {
		return "---------------null(null).SIGN_HISTORY_V.defaultValue()----------------";
	}
}
