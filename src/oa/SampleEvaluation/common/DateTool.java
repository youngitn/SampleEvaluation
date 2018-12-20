package oa.SampleEvaluation.common;

import jcx.db.talk;
import jcx.util.datetime;

public class DateTool {

	public static String getBeforeWorkDateOver25Day(String date, int day, talk t) throws Exception {
		int times = 1;
		int lessDay = 0;
		String nowdate = date;
		if (day >= 25) {
			times = day / 25;
			lessDay = day % 25;

		}
		for (int i = 1; i <= times; i++) {
			nowdate = getBeforeWorkDate(nowdate, 25, t);
		}
		if (lessDay > 0) {
			nowdate = getBeforeWorkDate(nowdate, lessDay, t);
		}
		return nowdate;

	}

	// 依日期取得前n個工作天
	public static String getBeforeWorkDate(String date, int day, talk t) throws Exception {

		String WorkDate = "";
		String YYMM = date.substring(0, 6);
		;// 取得上課日期的年月
		String DD = date.substring(6, 8);
		;// 取得上課日期的日
		int int_DD = Integer.parseInt(DD);
		int D = 0;
		int DLAST = 0;// 跨到上個月的天數
		int workDay = 0;
		String SQL = "";
		SQL = "select '',k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,k16,k17,k18,k19,k20,k21,k22,k23,k24,k25,k26,k27,k28,k29,k30,k31 "
				+ "from proceed where prtype = 'A01'  and yymm ='" + YYMM + "'";
		String ret[][] = t.queryFromPool(SQL);
		for (int i = int_DD; i > 0; i--) {
			if ((ret[0][i - 1]).equals("A01")) {
				D++;
			}
			if (D == day) {
				workDay = i - 1;
				WorkDate = YYMM + String.format("%02d", workDay);
				break;
			}
		}
		// 若D<day,則表示日期要跨到前一個月
		if (D < day) {
			int D1 = 0;
			DLAST = day - D;
			// 取得上個月的最後一天日期
			String lastdate = (datetime.dateAdd(YYMM + "01", "d", -1));
			String lastYYMM = lastdate.substring(0, 6);
			;// 取得上個月日期的年月
			String lastDD = lastdate.substring(6, 8);
			;// 取得上個月最後一天的日
			String lastSQL = "";
			lastSQL = "select '',k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,k16,k17,k18,k19,k20,k21,k22,k23,k24,k25,k26,k27,k28,k29,k30,k31 "
					+ "from proceed where prtype = 'A01'  and yymm ='" + lastYYMM + "'";
			String lastret[][] = t.queryFromPool(lastSQL);
			for (int x = Integer.parseInt(lastDD); x > 0; x--) {
				if ((lastret[0][x - 1]).equals("A01")) {
					D1++;
				}
				if (D1 == DLAST) {
					workDay = x - 1;
					WorkDate = lastYYMM + String.format("%02d", workDay);
					break;
				}
			}
			// message(lastDD+" "+D+" "+DLAST);
		}
		return WorkDate;
	}

	// 依日期取得前n個工作天
	public static String getAfterWorkDate(String date, int day, talk t) throws Exception {

		String WorkDate = "";
		String YYMM = date.substring(0, 6);

		;// 取得上課日期的年月
		String DD = date.substring(6, 8);// 取得上課日期的日
		int int_DD = Integer.parseInt(DD);
		int D = 0;
		int DLAST = 0;// 跨到上個月的天數
		int workDay = 0;
		int yy = Integer.parseInt(date.substring(0, 4));
		int mm = Integer.parseInt(date.substring(4, 6));
		int dayNumInMon = 0;
		while (D != day) {
			String SQL = "";

			String mM = "";
			System.out.println(mm);
			if (mm < 10) {
				mM = "0" + mm;
			} else {
				mM = "" + mm;
			}
			SQL = "select k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,k16,k17,k18,k19,k20,k21,k22,k23,k24,k25,k26,k27,k28,k29,k30,k31 "
					+ "from proceed where prtype = 'A01'  and yymm ='" + yy + "" + mM + "'";
			String ret[][] = t.queryFromPool(SQL);
			if (ret.length == 0) {
				mm = mm - 1;

				if (mm < 10) {
					mM = "0" + mm;
				} else if (mm == 0) {
					mm = 12;
				} else {
					mM = "" + mm;
				}
				WorkDate = yy + "" + mM + String.format("%02d", dayNumInMon + 1);
				break;
			}
			for (int i = int_DD; i < ret[0].length; i++) {

				if ("A01".equals(ret[0][i])) {
					dayNumInMon = i;
					D++;
				}
				if (D == day) {

					WorkDate = yy + "" + mm + String.format("%02d", i + 1);
					break;
				}
			}
			mm++;
			if (mm > 12) {
				mm = 1;
				yy++;
			}
		}
		return WorkDate + "";
	}
}
