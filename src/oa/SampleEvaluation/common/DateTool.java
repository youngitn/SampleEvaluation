package oa.SampleEvaluation.common;

import jcx.db.talk;

public class DateTool {

	// �̤�����o����n�Ӥu�@��
	public static String getAfterWorkDate(String date, int day, talk t) throws Exception {

		String workDate = "";

		String DD = date.substring(6, 8);// ���o�W�Ҥ������
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

				// ���Ū���Oi-1 (0�}�l)
				// ����Oi
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

	// �̤�����o�en�Ӥu�@��
	public static String getBeforeWorkDate(String date, int day, talk t) throws Exception {

		String workDate = "";

		String DD = date.substring(6, 8);// ���o�W�Ҥ������
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
			// �����ƾ�s�b�������Ƭ���,�קK���~���_�{��
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
				if (D == day) {

					workDate = yy + "" + mM + String.format("%02d", i - 1);
					break;
				}
			}
			// ���W�Ӥ��
			mm--;
			if (mm == 0) {
				mm = 12;
				yy--;
			}
			int_DD = 31;
		}
		return workDate + "";
	}
}
