package oa.SampleEvaluation.enums; 

public enum AppType {
	A, B, C;

	public static String getAppType(String apptype) {
		switch (AppType.valueOf(apptype.toUpperCase())) {
		case A:
			return "�s�쪫��";

		case B:
			return "�­쪫��";

		case C:
			return "��L";

		default:
			return "";

		}
	}
}
