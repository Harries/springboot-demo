package com.et.annotation;

import java.lang.annotation.*;


@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
//add more on a method
@Repeatable(CheckContainer.class)
public @interface Check {

	String ex() default "";

	String msg() default "";

}
