
package com.et.annotation;

import com.et.annotation.util.AspectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Slf4j
@Aspect
@Component
public class RequestTimeAspect {



    @Pointcut(value = "@annotation(com.et.annotation.RequestTime)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint point) throws Throwable {
		Method currentMethod = AspectUtil.getMethod(point);
		long starttime = System.currentTimeMillis();
		Object result = point.proceed();
		long endtime = System.currentTimeMillis();
		long requesttime =endtime-starttime;
		//if(requesttime>1000){
		log.info(AspectUtil.getClassName(point)+"."+currentMethod.getName()+"execute time："+requesttime+" ms");
		//}
        return result;
    }
}
