package org.springframework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMapping {

	String name() default "";

	@AliasFor("path")
	String[] value() default {};

	@AliasFor("value")
	String[] path() default {};

	String[] params() default {};

	String[] headers() default {};

	String[] consumes() default {};

	String[] produces() default {};

}
