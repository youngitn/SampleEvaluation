package oa.SampleEvaluation.enums;

/**
 * The Enum AppTypeEnum.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public enum AppTypeEnum {
	A, B, C, D, E;
	/**
	 * Gets the AppType.
	 *
	 * @param apptype [String]
	 * @return [String]
	 */
	public static String getAppType(String apptype) {
		switch (AppTypeEnum.valueOf(apptype.toUpperCase())) {
		case A:
			return "新原料";

		case B:
			return "舊原料";

		case C:
			return "其他";
		case D:
			return "新物料";
		case E:
			return "舊物料";

		default:
			return "";

		}
	}
}
