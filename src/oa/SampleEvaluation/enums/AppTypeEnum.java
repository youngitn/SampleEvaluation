package oa.SampleEvaluation.enums; 

public enum AppTypeEnum {
	A, B, C;

	public static String getAppType(String apptype) {
		switch (AppTypeEnum.valueOf(apptype.toUpperCase())) {
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
