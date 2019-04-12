package oa.SampleEvaluation.enums;

public class UrgencyType {

	public static String getUrgency(String urgency) {
		int c = Integer.parseInt(urgency);
		switch (c) {
		case 1:
			return "�ī~";

		case 2:
			return "���~";

		case 3:
			return "�Ƨ��~";

		case 4:
			return "�ƾǫ~";

		case 5:
			return "��������";

		case 6:
			return "��L";

		default:
			return "";
		}
	}
}
