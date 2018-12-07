package oa.SampleEvaluation;

import java.util.Vector;

import com.ysp.util.DateFormatUtil;

import jcx.jform.hproc;

public class ShowSignHistory extends hproc {

	// private Log log = LogUtil.getLog(this.getClass());11

	public String action(String value) throws Throwable {
		String id = "a.PNO= '" + getValue("QUERY_LIST.PNO") + "'";
		String rec[][] = getFlowHistory(getFunctionName(), id);

		if (rec == null || rec.length == 0) {
			setValue("text3", "<center><font size=\"4\" color=red></font></center>");
			return value;
		}
		StringBuilder sb = getMainFlowHistory(id, rec);

		String subid = "a.OWN_PNO= '" + getValue("QUERY_LIST.PNO") + "CHECK'";
		String subRec[][] = getFlowHistory(getFunctionName() + "_請驗流程", subid);
		StringBuilder sb1 = new StringBuilder("");
		if (subRec == null || subRec.length != 0) {
			sb1 = getMainFlowHistory(subid, subRec);
		}

		String subidTp = "a.OWN_PNO= '" + getValue("QUERY_LIST.PNO") + "TP'";
		String subRecTp[][] = getFlowHistory(getFunctionName() + "_試製流程", subidTp);
		StringBuilder sbTp = new StringBuilder("");
		if (subRecTp == null || subRecTp.length != 0) {
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
	private StringBuilder getMainFlowHistory(String id, String[][] rec) {
		StringBuilder sb = new StringBuilder();
		String APPLYEMPNAME = "";
		String fName = getFunctionName();
		if (id.contains("CHECK")) {
			fName = fName + "_請驗流程";
		}
		if (id.contains("TP")) {
			fName = fName + "_試製流程";
		}
		sb.append("<div  style=\"float:left;\"><TABLE>");
		sb.append(getHeaderStr(fName));
		for (int i = 0; i < rec.length; i++) {
			sb.append("<TR>");
			sb.append(
					"<td align='center' width='150' height='28' style='background-image:url(/image/eip/down_alt.png);background-repeat:no-repeat;background-position: center;'><font size='2' color='#003333'></font></td>");

			if ("AUTO".equals(rec[i][1])) {
				APPLYEMPNAME = "AUTO";
			} else {
				APPLYEMPNAME = getName(rec[i][1]);
			}

			sb.append("<TD align='center' width='400' >");
			sb.append("<TABLE width='100%' height='100%' border='1'");
			sb.append("<TR>");
			sb.append("<TD width='20%' align='center'>簽核人</TD>");
			sb.append("<TD width='33%' align='center'>簽核時間</TD>");
			sb.append("<TD width='47%' align='center'>備註</TD>");
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
		StringBuilder sb1 = new StringBuilder();
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

	private String getHeaderStr(String fName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		sb.append("<td nowrap align='right' width='150' style='border:solid 1px #003333';>");

		sb.append("<p align='center'><font size='2'>" + fName + "</font></p></td>");
		sb.append("<td align='center' width='400'></td>");
		sb.append("</tr>");
		return sb.toString();
	}

	public String getInformation() {
		return "---------------null(null).SIGN_HISTORY_V.defaultValue()----------------";
	}
}
