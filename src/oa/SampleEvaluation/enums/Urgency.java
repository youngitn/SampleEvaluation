package oa.SampleEvaluation.enums; 

public enum Urgency {
	A, B, C;

	public static String getUrgency(String urgency) {

		switch (Urgency.valueOf(urgency.toUpperCase())) {
		case A:
			return "¯S«æ";

		case B:
			return "«æ¥ó";

		case C:
			return "¤@¯ë";

		default:
			return "";
		}
	}
}
