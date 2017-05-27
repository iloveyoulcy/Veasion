package veasion.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Veasion注解. 
 * 
 * @author zhuowei.luo
 * @date 2017/5/27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Veasion {
	
	/**xxx.vea不包含/*/
	public String value();
	
	/**是否重定向*/
	public boolean redirect() default false;
	
}
