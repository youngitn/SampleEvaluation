package oa.SampleEvaluation.enums; 

public enum AppTypeEnum {
	A, B, C;

	public static String getAppType(String apptype) {
		switch (AppTypeEnum.valueOf(apptype.toUpperCase())) {
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
