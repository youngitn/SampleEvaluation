/**
 * 
 */
package oa.global;

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
public @interface dbTable {
	public String name();

	public String pkName();
}
