package com.wiser.library.annotation.thread;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Wiser
 * @version 版本
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Background {

	BackgroundType value() default BackgroundType.HTTP;
}
