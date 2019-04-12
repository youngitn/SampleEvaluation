package oa.SampleEvaluation.enums; 

/**
 * The Enum UrgencyEnum.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public enum UrgencyEnum {
	
	/** The a. */
	A, 
 /** The b. */
 B, 
 /** The c. */
 C;

	/**
	 * Gets the Urgency.
	 *
	 * @param urgency [String]
	 * @return [String]
	 */
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
