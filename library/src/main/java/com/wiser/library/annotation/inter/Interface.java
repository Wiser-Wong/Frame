package com.wiser.library.annotation.inter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Wiser
 * @version 版本
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Interface {

	Class value();

}
