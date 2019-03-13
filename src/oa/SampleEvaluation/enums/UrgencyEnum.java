package oa.SampleEvaluation.enums; 

public enum UrgencyEnum {
	A, B, C;

	public static String getUrgency(String urgency) {

		switch (UrgencyEnum.valueOf(urgency.toUpperCase())) {
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
