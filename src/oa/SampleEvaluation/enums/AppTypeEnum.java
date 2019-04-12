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
			return "�s���";

		case B:
			return "�­��";

		case C:
			return "��L";
		case D:
			return "�s����";
		case E:
			return "�ª���";

		default:
			return "";

		}
	}
}
