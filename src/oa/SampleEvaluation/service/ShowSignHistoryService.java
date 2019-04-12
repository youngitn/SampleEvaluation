package oa.SampleEvaluation.service;

import java.util.Vector;

import com.ysp.util.DateFormatUtil;

import jcx.jform.hproc;

/**
 * The Class ShowSignHistoryService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class ShowSignHistoryService extends hproc {

	// private Log log = LogUtil.getLog(this.getClass());11

	/* (non-Javadoc)
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	public String action(String value) throws Throwable {
		String id = "a.PNO= '" + getValue("QUERY_LIST.PNO") + "'";
		String rec[][] = getFlowHistory(getFunctionName(), id);
		if (rec == null || rec.length == 0) {
			setValue("text3", "<center><font size=\"4\" color=red></font></center>");
			return value;
		}
		StringBuilder sb = getMainFlowHistory(id, rec, getFunctionName());

		String subid = "a.OWN_PNO= '" + getValue("QUERY_LIST.PNO") + "CHECK'";
		String[][] subRec = getFlowHistory(getFunctionName() + "_����y�{", subid);
		StringBuilder sbCheck = new StringBuilder("");
		if (subRec != null && subRec.length != 0) {
			sbCheck = getMainFlowHistory(subid, subRec, getFunctionName() + "_����y�{");
		}

		String subidTp = "a.OWN_PNO= '" + getValue("QUERY_LIST.PNO") + "TP'";
		String[][] subRecTp = getFlowHistory(getFunctionName() + "_�ջs�y�{", subidTp);
		StringBuilder sbTp = new StringBuilder("");
		if (subRecTp != null && subRecTp.length != 0) {
			sbTp = getMainFlowHistory(subidTp, subRecTp, getFunctionName() + "_�ջs�y�{");
		}

		String subidTest = "a.OWN_PNO= '" + getValue("QUERY_LIST.PNO") + "TEST'";
		String[][] subRecTest = getFlowHistory(getFunctionName() + "_�ը��y�{", subidTest);
		StringBuilder sbTest = new StringBuilder("");
		if (subRecTest != null && subRecTest.length != 0) {
			sbTest = getMainFlowHistory(subidTest, subRecTest, getFunctionName() + "_�ը��y�{");
		}

		setValue("text3",
				"<table ><tr>" + "<td valign=\"top\">" + sb.toString() + "</td>" + "<td valign=\"top\">"
						+ sbCheck.toString() + "</td>" + "<td valign=\"top\">" + sbTp.toString() + "</td>"
						+ "<td valign=\"top\">" + sbTest.toString() + "</td></tr>" +

						"</table>");

		return value;

	}

	/**
	 * Gets the MainFlowHistory.
	 *
	 * @param id [String]
	 * @param rec [String[][]]
	 * @param fName [String]
	 * @return [StringBuilder]
	 */
	private StringBuilder getMainFlowHistory(String id, String[][] rec, String fName) {
		StringBuilder sb = new StringBuilder();
		String APPLYEMPNAME = "";

		sb.append("<div  style=\"float:left;\"><TABLE>");
		sb.append(getHeaderStr(fName));
		for (int i = 0; i < rec.length; i++) {
			sb.append("<TR>");
			sb.append(
					"<td align='center' height='28' style='background-image:url(/image/eip/down_alt.png);background-repeat:no-repeat;background-position: center;'><font size='2' color='#003333'></font></td>");

			if ("AUTO".equals(rec[i][1])) {
				APPLYEMPNAME = "AUTO";
			} else {
				APPLYEMPNAME = getName(rec[i][1]);
			}

			sb.append("<TD align='center' width='400' >");
			sb.append("<TABLE width='100%' height='100%' border='1'");
			sb.append("<TR>");
			sb.append("<TD width='20%' align='center'>ñ�֤H</TD>");
			sb.append("<TD width='33%' align='center'>ñ�֮ɶ�</TD>");
			sb.append("<TD width='47%' align='center'>�Ƶ�</TD>");
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

	/**
	 * Gets the HeaderStr.
	 *
	 * @param fName [String]
	 * @return [String]
	 */
	private String getHeaderStr(String fName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		sb.append("<td nowrap align='right' width='150' style='border:solid 1px #003333';>");

		sb.append("<p align='center'><font size='2'>" + fName + "</font></p></td>");
		sb.append("<td align='center' width='400'></td>");
		sb.append("</tr>");
		return sb.toString();
	}

	/**
	 * Gets the Information.
	 *
	 * @return [String]
	 */
	public String getInformation() {
		return "---------------null(null).SIGN_HISTORY_V.defaultValue()----------------";
	}
}
