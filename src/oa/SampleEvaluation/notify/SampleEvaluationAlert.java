package oa.SampleEvaluation.notify;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import com.ysp.service.MailService;

import jcx.db.talk;
import jcx.jform.bNotify;
import oa.SampleEvaluation.common.DateTool;

public class SampleEvaluationAlert extends bNotify {
	public void actionPerformed(String arg) throws Throwable {
		talk t = getTalk();

		// 查詢未結案單據
		// 跟催機制:特急_採購單位送出日＋7工作天; 急件_採購單位送出日＋12工作天; 一般_採購單位送出日＋22工作天
		// 目前以單純天數計算 而非工作日
		String[][] before7Ret = null, before12Ret = null, before22Ret = null, before50Ret = null, before55Ret = null,
				before65Ret = null;
		ArrayList<String[][]> al = new ArrayList<String[][]>();
		ArrayList<String[][]> fal = new ArrayList<String[][]>();
		String today = getToday(0);
		try {
			String before7 = DateTool.getBeforeWorkDate(today, 7, t);// 特級
			String before12 = DateTool.getBeforeWorkDate(today, 12, t);// 急件
			String before22 = DateTool.getBeforeWorkDate(today, 11, t);// 一般 6 for test

			String before50 = DateTool.getBeforeWorkDateOver25Day(today, 50, t);// 特級
			String before55 = DateTool.getBeforeWorkDateOver25Day(today, 55, t);// 急件
			String before65 = DateTool.getBeforeWorkDateOver25Day(today, 65, t);// 一般

			before7Ret = getRetByAppDate(before7, "A", t);
			before12Ret = getRetByAppDate(before12, "B", t);
			before22Ret = getRetByAppDate(before22, "C", t);

			before50Ret = getRetByAppDate(before50, "A", t);
			before55Ret = getRetByAppDate(before55, "B", t);
			before65Ret = getRetByAppDate(before65, "C", t);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int count = 0;
		int fcount = 0;
		count += before7Ret.length + before12Ret.length + before22Ret.length;
		fcount += before50Ret.length + before55Ret.length + before65Ret.length;
		al.add(before7Ret);
		al.add(before12Ret);
		al.add(before22Ret);
		fal.add(before50Ret);
		fal.add(before55Ret);
		fal.add(before65Ret);
		// 沒有資料需要提醒的話 結束流程
		if (count != 0) {
			try {
				goEmail(al, t, count, true);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (fcount != 0) {
			try {
				goEmail(al, t, count, false);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return;
	}

	public void goEmail(ArrayList<String[][]> al, talk t, int count, boolean isBeforeAppDate)
			throws SQLException, Exception {
		System.out.println("will send mail");
		// 過濾日期
		StringBuilder sb = new StringBuilder();

		sb.append("<table  style=\"border-collapse: collapse;\" border=\"1\"> " + "<tr bgcolor=\"yellow\"> "
				+ "<th>表單名稱</th>" + "<th>表單單號</th> " + "<th>主旨</th>" + " <th>案件狀態</th> " + "<th>天數</th> "
				+ "<th>申請人</th> " + "<th>目前處理者</th>" + "</tr>");
		try {
			for (String[] strings : al.get(0)) {
				sb.append("<TR>");

				sb.append(getDataTd(strings, "一", 7, t, isBeforeAppDate));

				sb.append("</TR>");
			}

			for (String[] strings : al.get(1)) {
				sb.append("<TR>");

				sb.append(getDataTd(strings, "二", 12, t, isBeforeAppDate));

				sb.append("</TR>");
			}

			for (String[] strings : al.get(2)) {
				sb.append("<TR>");

				sb.append(getDataTd(strings, "三", 22, t, isBeforeAppDate));

				sb.append("</TR>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("</table>");
		// 標題
		String title = "【資訊傳遞通知單案件提醒函】";
		// 內容
		String content = "您好：<BR>" + "附上資訊傳遞通知單案件尚未完成清單，共有" + count + "筆，清單如下：<BR>" + sb.toString()
				+ "詳細內容請進入Emaker應用服務系統http://ehr.yungshingroup.com/yspehr.html<BR>" + "此郵件由系統自動發出，請勿回信，謝謝!!<BR>";
		// 提醒誰??mail中未說明
		String to[] = new String[] { "u52116@ysp.local" };

		// String sendRS = smtp.sendMailbccUTF8(mailHost, from, to, title, content,
		// null, "", "text/html");
		BaseService service = new BaseService(this);
		MailService ms = new MailService(service);
		ms.sendMailbccUTF8(to, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);
	}

	public String getEmpid(String empid) throws Exception {
		if (empid.length() == 5) {
			return empid;
		} else if (empid.length() == 6) {
			return getEmpid(empid.substring(1, 6));
		} else {
			return empid;
		}
	}

	public String getToday(int d) {
		Date date = new Date();
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DAY_OF_YEAR, d);// 日期加減
		Date date1 = rightNow.getTime();
		String str = bartDateFormat.format(date1);
		return str;
	}

	public String[][] getRetByAppDate(String date, String urgency, talk t) throws SQLException, Exception {
		return t.queryFromPool(
				"SELECT PNO,'('+APPLICANT+')'+(select hecname from hruser where empid = APPLICANT),APP_DATE,APP_TYPE,MATERIAL,SAP_CODE FROM SAMPLE_EVALUATION WHERE APP_DATE = "
						+ date + " AND URGENCY = '" + urgency
						+ "' AND PNO IN (SELECT PNO FROM SAMPLE_EVALUATION_FLOWC WHERE F_INP_STAT != '結案')");

	}

	public String getDataTd(String[] strings, String week, int day, talk t, boolean isBeforeAppDate) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<TD>");

		sb.append(getAppTypeName(strings[3]) + "_資訊傳遞通知單");
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(strings[0]);
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(strings[4] + "(" + strings[5] + ")");
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(isBeforeAppDate ? "逾期" + week + "週以上" : getFinishStrByWeek(week));
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(isBeforeAppDate ? "逾期" + day + "週以上" : getFinishStrByWeek(week));
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(strings[1]);
		sb.append("</TD>");
		sb.append("<TD>");
		Vector v = getApprovablePeople("資訊傳遞通知單", "a.pno='" + strings[0] + "'");
		sb.append(getName((String) v.get(0)) + v.get(0));
		sb.append("</TD>");
		return sb.toString();
	}

	public String getFinishStrByWeek(String week) {
		if ("一".equals(week))
			return "預計完成日的前50天";

		if ("二".equals(week))
			return "預計完成日的前55天";
		if ("三".equals(week))
			return "預計完成日的前65天";
		return week;

	}

	public String getAppTypeName(String code) {
		if (code.equals("A")) {
			return "新原物料";
		} else if (code.equals("B")) {
			return "舊原物料";
		} else {
			return "其他";
		}

	}

	public String getInformation() {
		return "---------------\u8ab2\u4e3b\u7ba1.Auto Notify()----------------";
	}

}