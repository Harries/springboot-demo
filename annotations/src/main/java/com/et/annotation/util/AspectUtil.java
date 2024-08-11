
package com.et.annotation.util;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import org.springframework.validation.support.BindingAwareModelMap;

import java.lang.reflect.Method;


public class AspectUtil {


	/**
	 * get class name
	 *
	 * @param point
	 *
	 */
	public static String getClassName(ProceedingJoinPoint point) {
		return point.getTarget().getClass().getName().replaceAll("\\.", "_");
	}

	/**
	 * get method
	 *
	 * @param point
	 *
	 * @throws NoSuchMethodException
	 */
	public static Method getMethod(ProceedingJoinPoint point) throws NoSuchMethodException {
		Signature sig = point.getSignature();
		MethodSignature msig = (MethodSignature) sig;
		Object target = point.getTarget();
		return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	}


}
