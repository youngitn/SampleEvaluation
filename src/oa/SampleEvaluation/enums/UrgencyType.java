package oa.SampleEvaluation.enums;

public class UrgencyType {

	public static String getUrgency(String urgency) {
		int c = Integer.parseInt(urgency);
		switch (c) {
		case 1:
			return "藥品";

		case 2:
			return "食品";

		case 3:
			return "化妝品";

		case 4:
			return "化學品";

		case 5:
			return "醫療器材";

		case 6:
			return "其他";

		default:
			return "";
		}
	}
}
