
package com.et.annotation;

import java.lang.annotation.*;

/**
 * computa the excute time for the method
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequestTime {

}
