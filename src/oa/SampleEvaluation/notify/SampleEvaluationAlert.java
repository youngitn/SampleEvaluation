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

		// �d�ߥ����׳��
		// ��ʾ���:�S��_���ʳ��e�X���7�u�@��; ���_���ʳ��e�X���12�u�@��; �@��_���ʳ��e�X���22�u�@��
		// �ثe�H��¤Ѽƭp�� �ӫD�u�@��
		String[][] before7Ret = null, before12Ret = null, before22Ret = null, before50Ret = null, before55Ret = null,
				before65Ret = null;
		ArrayList<String[][]> al = new ArrayList<String[][]>();
		ArrayList<String[][]> fal = new ArrayList<String[][]>();
		String today = getToday(0);
		try {
			String before7 = DateTool.getBeforeWorkDate(today, 7, t);// �S��
			String before12 = DateTool.getBeforeWorkDate(today, 12, t);// ���
			String before22 = DateTool.getBeforeWorkDate(today, 11, t);// �@�� 6 for test

			String before50 = DateTool.getBeforeWorkDateOver25Day(today, 50, t);// �S��
			String before55 = DateTool.getBeforeWorkDateOver25Day(today, 55, t);// ���
			String before65 = DateTool.getBeforeWorkDateOver25Day(today, 65, t);// �@��

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
		// �S����ƻݭn�������� �����y�{
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
		// �L�o���
		StringBuilder sb = new StringBuilder();

		sb.append("<table  style=\"border-collapse: collapse;\" border=\"1\"> " + "<tr bgcolor=\"yellow\"> "
				+ "<th>���W��</th>" + "<th>���渹</th> " + "<th>�D��</th>" + " <th>�ץ󪬺A</th> " + "<th>�Ѽ�</th> "
				+ "<th>�ӽФH</th> " + "<th>�ثe�B�z��</th>" + "</tr>");
		try {
			for (String[] strings : al.get(0)) {
				sb.append("<TR>");

				sb.append(getDataTd(strings, "�@", 7, t, isBeforeAppDate));

				sb.append("</TR>");
			}

			for (String[] strings : al.get(1)) {
				sb.append("<TR>");

				sb.append(getDataTd(strings, "�G", 12, t, isBeforeAppDate));

				sb.append("</TR>");
			}

			for (String[] strings : al.get(2)) {
				sb.append("<TR>");

				sb.append(getDataTd(strings, "�T", 22, t, isBeforeAppDate));

				sb.append("</TR>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("</table>");
		// ���D
		String title = "�i��T�ǻ��q����ץ󴣿���j";
		// ���e
		String content = "�z�n�G<BR>" + "���W��T�ǻ��q����ץ�|�������M��A�@��" + count + "���A�M��p�U�G<BR>" + sb.toString()
				+ "�ԲӤ��e�жi�JEmaker���ΪA�Ȩt��http://ehr.yungshingroup.com/yspehr.html<BR>" + "���l��Ѩt�Φ۰ʵo�X�A�ФŦ^�H�A����!!<BR>";
		// ������??mail��������
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
		rightNow.add(Calendar.DAY_OF_YEAR, d);// ����[��
		Date date1 = rightNow.getTime();
		String str = bartDateFormat.format(date1);
		return str;
	}

	public String[][] getRetByAppDate(String date, String urgency, talk t) throws SQLException, Exception {
		return t.queryFromPool(
				"SELECT PNO,'('+APPLICANT+')'+(select hecname from hruser where empid = APPLICANT),APP_DATE,APP_TYPE,MATERIAL,SAP_CODE FROM SAMPLE_EVALUATION WHERE APP_DATE = "
						+ date + " AND URGENCY = '" + urgency
						+ "' AND PNO IN (SELECT PNO FROM SAMPLE_EVALUATION_FLOWC WHERE F_INP_STAT != '����')");

	}

	public String getDataTd(String[] strings, String week, int day, talk t, boolean isBeforeAppDate) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<TD>");

		sb.append(getAppTypeName(strings[3]) + "_��T�ǻ��q����");
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(strings[0]);
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(strings[4] + "(" + strings[5] + ")");
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(isBeforeAppDate ? "�O��" + week + "�g�H�W" : getFinishStrByWeek(week));
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(isBeforeAppDate ? "�O��" + day + "�g�H�W" : getFinishStrByWeek(week));
		sb.append("</TD>");
		sb.append("<TD>");
		sb.append(strings[1]);
		sb.append("</TD>");
		sb.append("<TD>");
		Vector v = getApprovablePeople("��T�ǻ��q����", "a.pno='" + strings[0] + "'");
		sb.append(getName((String) v.get(0)) + v.get(0));
		sb.append("</TD>");
		return sb.toString();
	}

	public String getFinishStrByWeek(String week) {
		if ("�@".equals(week))
			return "�w�p�����骺�e50��";

		if ("�G".equals(week))
			return "�w�p�����骺�e55��";
		if ("�T".equals(week))
			return "�w�p�����骺�e65��";
		return week;

	}

	public String getAppTypeName(String code) {
		if (code.equals("A")) {
			return "�s�쪫��";
		} else if (code.equals("B")) {
			return "�­쪫��";
		} else {
			return "��L";
		}

	}

	public String getInformation() {
		return "---------------\u8ab2\u4e3b\u7ba1.Auto Notify()----------------";
	}

}