package oa.SampleEvaluation.enums; 

public enum AppType {
	A, B, C;

	public static String getAppType(String apptype) {
		switch (AppType.valueOf(apptype.toUpperCase())) {
		case A:
			return "新原物料";

		case B:
			return "舊原物料";

		case C:
			return "其他";

		default:
			return "";

		}
	}
}
