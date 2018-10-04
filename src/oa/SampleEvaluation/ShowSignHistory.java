package oa.SampleEvaluation;

import java.sql.SQLException;
import java.util.Vector;

import jcx.db.talk;
import jcx.jform.hproc;

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
		return getHtml(value);
	}

	/**
	 * @param value
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private String getHtml(String value) throws SQLException, Exception {
		// 取得目前所有流程節點
		String id = "a.PNO= '" + getValue("QUERY_LIST.PNO") + "'"; // 單號欄位
		String rec[][] = getFlowHistory(getFunctionName(), id); // getFlowHistory(String功能名稱, String欄位前加上 a. 如
																// a.PNO='A12345678' )
		// 若無簽核流程紀錄時執行
		if (rec.length == 0 || rec == null) {
			setValue("text3", "<center><font size=\"4\" color=red>無簽核流程紀錄</font></center>");
			return value;
		}
		// 簽核流程紀錄
		talk t = getTalk();
		String APPLYEMPNAME;
		StringBuffer sb = getMainFlowHistory(id, rec);

		// 有子流程才加額外的div
		String subid = "a.OWN_PNO= '" + getValue("QUERY_LIST.PNO") + "CHECK'"; // 單號欄位
		String subRec[][] = getFlowHistory(getFunctionName() + "_請驗流程", subid);
		StringBuffer sb1 = new StringBuffer("");
		if (subRec == null || subRec.length != 0) {
			sb1 = getMainFlowHistory(subid, subRec);
		}

		setValue("text3", "<table ><tr><td valign=\"top\">" + sb.toString() + "</td><td valign=\"top\">"
				+ sb1.toString() + "<td></tr></table>");

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
		sb.append("<div  style=\"float:left;\"><TABLE>");
		sb.append("<tr>"); // 1列開始
		sb.append("<td nowrap align='right' width='150' style='border:solid 1px #003333';>"); // 1-1儲存格(內容不換行 右寬150 邊框)
		sb.append("<p align='center'><font size='2'>新增-" + getFunctionName() + "</font></p></td>"); // 內容置中 字體大小2
		sb.append("<td align='center' width='400'></td>"); // 1-2儲存格
		sb.append("</tr>"); // 1列結束
		for (int i = 0; i < rec.length; i++) {
			sb.append("<tr>"); // 2列開始
			sb.append(
					"<td align='center' width='150' height='28' style='background-image:url(/image/eip/down_alt.png);background-repeat:no-repeat;background-position: center;'><font size='2' color='#003333'></font></td>"); // 2-1儲存格
			// sb.append("<td align='center'><font size='5' >&#8595;</font></td>");
			// //2-1儲存格(簡單的箭頭)
			// 對於AUTO的處置(AUTO為系統內部的)
			if ("AUTO".equals(rec[i][1])) {
				APPLYEMPNAME = "AUTO";
			} else {
				APPLYEMPNAME = getName(rec[i][1]);
			}

			// rec[i][0] rec[i][1] rec[i][2] rec[i][3]
			// 節點名稱、批核人員、批核時間、批核意見
			sb.append("<TD align='center' width='400' >");
			sb.append("<TABLE width='100%' height='100%' border='1'");
			sb.append("<TR>");
			sb.append("<TD width='20%' align='center'>簽核人員</TD>");
			sb.append("<TD width='33%' align='center'>簽核時間</TD>");
			sb.append("<TD width='47%' align='center'>簽核意見</TD>");
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
		return sb;
	}

	public String getInformation() {
		return "---------------null(null).SIGN_HISTORY_V.defaultValue()----------------";
	}
}
