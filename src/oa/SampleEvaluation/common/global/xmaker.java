package oa.SampleEvaluation.common.global;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface xmaker {
	public String name();// when UI name same as DB field name,,�]�m�o���ݩʧY�i

	public String adapDbFieldName() default "";// if UI name != DB field name, ���]�m�o���ݩʥH�����d�߱���

	public boolean isDateStart() default false;

	public boolean isDateEnd() default false;
	
	public boolean isText() default false;
	
	public boolean isFlowStatus() default false;
}
