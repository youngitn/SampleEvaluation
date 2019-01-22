package oa.SampleEvaluation.common.global;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface xmaker {
	public String name();// when UI name same as DB field name,,設置這個屬性即可

	public String adapDbFieldName() default "";// if UI name != DB field name, 須設置這個屬性以對應查詢條件

	public boolean isDateStart() default false;

	public boolean isDateEnd() default false;
	
	public boolean isText() default false;
	
	public boolean isFlowStatus() default false;
}
