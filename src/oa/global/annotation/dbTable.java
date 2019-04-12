/**
 * 
 */
package oa.global.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface dbTable.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE })
/**
 * @author u52116
 *
 */
public @interface dbTable {
	
	/**
	 * Name.
	 *
	 * @return  [String]
	 */
	public String name();

	/**
	 * Pk name.
	 *
	 * @return  [String]
	 */
	public String pkName();
}
