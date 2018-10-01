package oa.SampleEvaluation.enums; 

public enum Urgency {
	A, B, C;

	public static String getUrgency(String urgency) {

		switch (Urgency.valueOf(urgency.toUpperCase())) {
		case A:
			return "�S��";

		case B:
			return "���";

		case C:
			return "�@��";

		default:
			return "";
		}
	}
}
