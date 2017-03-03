package c.e.g.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @File Name : Gentity.java
 * @Project Name : grimp-component
 * @package Name : c.e.g.annotation
 * @create Date : 2016. 3. 25.
 * @explain : 국제화 메세지의 접두사 접미사 설정 
 * @made by : "GOEDOKID"
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Gentity {
	
	/**
	 * @Method Name : msgPreFix
	 * @create Date : 2016. 3. 25.
	 * @made by : "GOEDOKID"
	 * @explain : 국제화 메세지 접두사 설정(접두사(com.es.e)+변수명(test))
	 * @param : 
	 * @return : String
	 */
	String msgPrefix() default "";
	
	/**
	 * @Method Name : msgPreFix
	 * @create Date : 2016. 3. 25.
	 * @made by : "GOEDOKID"
	 * @explain : 국제화 메세지 접미사 설정(변수명(test)+접미사(.alert))
	 * @param : 
	 * @return : String
	 */
	String msgSuffix() default "";

}
 