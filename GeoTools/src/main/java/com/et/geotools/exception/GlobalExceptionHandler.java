package com.et.geotools.exception;


import com.et.geotools.result.ResponseResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>全局异常捕获处理类</p>
 * @author Appleyk
 * @blob https://blog.csdn.net/appleyk
 * @date Created on 上午 2018年10月25日09:57:57
 */
@CrossOrigin
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseResult processException(Exception ex, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return new ResponseResult(400, ex);
	}

}
