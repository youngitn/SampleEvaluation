/**
 * 
 */
package oa.SampleEvaluation.common.global;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE })
/**
 * @author u52116
 *
 */
public @interface dbTableName {
	public String name();
}
