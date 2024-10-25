package com.et.annotation;

import com.et.exception.UserFriendlyException;
import com.googlecode.aviator.AviatorEvaluator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;


@Aspect
@Configuration
public class AopConfig {

	/**
	 * Aspects monitor multiple annotations, because one annotation is Check and multiple annotations are compiled to CheckContainer
	 */
	@Pointcut("@annotation(com.ler.jcheck.annation.CheckContainer) || @annotation(com.ler.jcheck.annation.Check)")
	public void pointcut() {
	}

	@Before("pointcut()")
	public Object before(JoinPoint point) {
		//获取参数
		Object[] args = point.getArgs();
		//用于获取参数名字
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paramNames = u.getParameterNames(method);

		CheckContainer checkContainer = method.getDeclaredAnnotation(CheckContainer.class);
		List<Check> value = new ArrayList<>();

		if (checkContainer != null) {
			value.addAll(Arrays.asList(checkContainer.value()));
		} else {
			Check check = method.getDeclaredAnnotation(Check.class);
			value.add(check);
		}
		for (int i = 0; i < value.size(); i++) {
			Check check = value.get(i);
			String ex = check.ex();
			//In the rule engine, null is represented by nil
			ex = ex.replaceAll("null", "nil");
			String msg = check.msg();
			if (StringUtils.isEmpty(msg)) {
				msg = "server exception...";
			}

			Map<String, Object> map = new HashMap<>(16);
			for (int j = 0; j < paramNames.length; j++) {
				//Prevent index out of bounds
				if (j > args.length) {
					continue;
				}
				map.put(paramNames[j], args[j]);
			}
			Boolean result = (Boolean) AviatorEvaluator.execute(ex, map);
			if (!result) {
				throw new UserFriendlyException(msg);
			}
		}
		return null;
	}
}