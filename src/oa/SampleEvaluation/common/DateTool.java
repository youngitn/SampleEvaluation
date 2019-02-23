package oa.SampleEvaluation.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jcx.db.talk;

/**
 * The Class DateTool.
 */
public class DateTool {

	/**
	 * Gets the AfterWorkDate.
	 *
	 * @param date [String]
	 * @param day  [int]
	 * @param t    [talk]
	 * @return [String]
	 * @throws Exception the exception
	 */
	// 依日期取得未來n個工作天
	public static String getAfterWorkDate(String date, int day, talk t) throws Exception {

		String workDate = "";
		System.out.println("------>" + date);
		String DD = date.substring(6, 8);
		int int_DD = Integer.parseInt(DD);
		int D = 0;
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

				if (mm < 10 && mm != 0) {
					mM = "0" + mm;
				} else if (mm == 0) {
					mM = "12";
					yy = yy - 1;
				} else {
					mM = "" + mm;
				}
				workDate = yy + "" + mM + String.format("%02d", dayNumInMon + 1);
				break;
			}
			for (int i = int_DD; i < ret[0].length; i++) {

				// 資料讀取是i-1 (0開始)
				// 日期是i
				if ("A01".equals(ret[0][i])) {
					dayNumInMon = i;
					D++;
				}
				if (D == day) {

					workDate = yy + "" + mM + String.format("%02d", i + 1);
					break;
				}
			}
			mm++;
			if (mm > 12) {
				mm = 1;
				yy++;
			}
			int_DD = 1;
		}
		return workDate + "";
	}

	/**
	 * Gets the BeforeWorkDate.
	 *
	 * @param date   [String]
	 * @param dayNum [int]
	 * @param t      [talk]
	 * @return [String]
	 * @throws Exception the exception
	 */
	public static String getBeforeWorkDate(String date, int dayNum, talk t) throws Exception {

		String workDate = "";

		String DD = date.substring(6, 8);// 取得上課日期的日
		int int_DD = Integer.parseInt(DD);
		int D = 0;
		int yy = Integer.parseInt(date.substring(0, 4));
		int mm = Integer.parseInt(date.substring(4, 6));
		int dayNumInMon = 0;
		while (D != dayNum) {
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
			// 取到行事曆存在的月份資料為止,避免錯誤中斷程式
			if (ret.length == 0) {

				if (mm == 12) {
					mM = "01";
					yy = yy + 1;
				}
				if (mm < 10) {
					mM = "0" + (mm + 1);
				} else {
					mM = "" + mm;
				}
				workDate = yy + "" + mM + String.format("%02d", dayNumInMon + 1);
				break;
			}
			for (int i = int_DD; i >= 2; i--) {

				if ("A01".equals(ret[0][i - 2]) && !"*".equals(ret[0][i - 2])) {
					dayNumInMon = i;
					D++;
				}
				if (D == dayNum) {

					workDate = yy + "" + mM + String.format("%02d", i - 1);
					break;
				}
			}
			// 往上個月找
			mm--;
			if (mm == 0) {
				mm = 12;
				yy--;
			}
			int_DD = 31;
		}
		return workDate + "";
	}

	/**
	 * 取得兩日期差異天數.
	 *
	 * @param FD [String]
	 * @param SD [String]
	 * @return [int]
	 * @throws ParseException the parse exception
	 */
	public static int diffDays(String FD, String SD) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date start = sdf.parse(FD);
		Date end = sdf.parse(SD);

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(start);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 閏年
				{
					timeDistance += 366;
				} else // 不是閏年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			System.out.println("判斷day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}
}
