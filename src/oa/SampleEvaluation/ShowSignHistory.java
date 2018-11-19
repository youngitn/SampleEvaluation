package oa.SampleEvaluation;

import java.sql.SQLException;
import java.util.Vector;

import jcx.db.talk;
import jcx.jform.hproc;

import com.ysp.util.DateFormatUtil;

public class ShowSignHistory extends hproc {

	// private Log log = LogUtil.getLog(this.getClass());11

	public String action(String value) throws Throwable {

//		SignFlowHistoryService service = new SignFlowHistoryService(this);
//		service.doDefaultDisplaySignFlowHistory();
		getDefaultValue(value);
		return value;
	}

	public String getDefaultValue(String value) throws Throwable {
		return getHtml(value);
	}

	/**
	 * @param value
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private String getHtml(String value) throws SQLException, Exception {
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
		StringBuffer sb = getMainFlowHistory(id, rec);

		// ���l�y�{�~�[�B�~��div
		String subid = "a.OWN_PNO= '" + getValue("QUERY_LIST.PNO") + "CHECK'"; // �渹���
		String subRec[][] = getFlowHistory(getFunctionName() + "_����y�{", subid);
		StringBuffer sb1 = new StringBuffer("");
		if (subRec == null || subRec.length != 0) {
			sb1 = getMainFlowHistory(subid, subRec);
		}

		String subidTp = "a.OWN_PNO= '" + getValue("QUERY_LIST.PNO") + "TP'"; // �渹���
		String subRecTp[][] = getFlowHistory(getFunctionName() + "_�ջs�y�{", subidTp);
		StringBuffer sbTp = new StringBuffer("");
		if (subRec == null || subRec.length != 0) {
			sbTp = getMainFlowHistory(subidTp, subRecTp);
		}

		setValue("text3", "<table ><tr><td valign=\"top\">" + sb.toString() + "</td><td valign=\"top\">"
				+ sb1.toString() + "</td><td valign=\"top\">" + sbTp.toString() + "</td></tr></table>");

		return value;
	}

	/**
	 * @param id
	 * @param rec
	 * @return
	 */
	private StringBuffer getMainFlowHistory(String id, String[][] rec) {
		StringBuffer sb = new StringBuffer();
		String APPLYEMPNAME = "";
		String fName = getFunctionName();
		if (id.contains("CHECK")) {
			fName = fName + "_����y�{";
		}
		if (id.contains("TP")) {
			fName = fName + "_�ջs�y�{";
		}
		sb.append("<div  style=\"float:left;\"><TABLE>");
		sb.append("<tr>"); // 1�C�}�l
		sb.append("<td nowrap align='right' width='150' style='border:solid 1px #003333';>"); // 1-1�x�s��(���e������ �k�e150 ���)
		sb.append("<p align='center'><font size='2'>�s�W-" + fName + "</font></p></td>"); // ���e�m�� �r��j�p2
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
		Vector people = getApprovablePeople(fName, id);
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
		return sb;
	}

	public String getInformation() {
		return "---------------null(null).SIGN_HISTORY_V.defaultValue()----------------";
	}
}
