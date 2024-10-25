package com.et.exception;

import java.util.ArrayList;
import java.util.List;

public class HttpResult<T> {
	protected boolean status;
	protected int code;
	protected String message;

	protected T entry;

	public static final String MESSAGE_SUCCESS = "SUCCESS";
	public static final String MESSAGE_FAILURE = "FAILURE";

	public static final int RESPONSE_SUCCESS = 200;
	public static final int RESPONSE_FAILURE = -1;

	private HttpResult() {

	}

	public String getMessage() {
		return message;
	}

	public HttpResult<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int responseCode) {
		this.code = responseCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getEntry() {
		return entry;
	}

	public HttpResult<T> setEntry(T entry) {
		this.entry = entry;
		return this;
	}

	public static <T> HttpResult<T> failure() {
		return HttpResult.failure(RESPONSE_FAILURE, "");
	}

	public static <T> HttpResult<T> failure(String msg) {
		return HttpResult.failure(RESPONSE_FAILURE, msg);
	}

	public static <T> HttpResult<T> failure(int code, String msg) {
		HttpResult result = new HttpResult<T>();
		result.setStatus(false);
		result.setMessage(msg);
		result.setCode(code);
		return result;
	}

	public static <T> HttpResult<T> success(T obj) {
		SuccessResult result = new SuccessResult<T>();
		result.setStatus(true);
		result.setEntry(obj);
		result.setCode(200);
		return result;
	}

	public static <T> HttpResult<T> success(T obj, int code) {
		SuccessResult<T> result = new SuccessResult<T>();
		result.setStatus(true);
		result.setCode(200);
		result.setEntry(obj);
		return result;
	}

	public static <T> HttpResult<List<T>> success(List<T> list) {
		SuccessResult<List<T>> result = new SuccessResult<List<T>>();
		result.setStatus(true);
		result.setCode(200);
		if (null == list) {
			result.setEntry(new ArrayList<T>(0));
		} else {
			result.setEntry(list);
		}
		return result;
	}

	public static HttpResult<Boolean> success() {
		SuccessResult<Boolean> result = new SuccessResult<Boolean>();
		result.setStatus(true);
		result.setEntry(true);
		result.setCode(200);
		return result;
	}

	public static <T> HttpResult<T> success(T entry, String message) {
		SuccessResult<T> result = new SuccessResult<T>();
		result.setStatus(true);
		result.setEntry(entry);
		result.setMessage(message);
		result.setCode(200);
		return result;
	}

	/**
	 * Result set data with paging information
	 */
	public static class PageSuccessResult<T> extends HttpResult<T> {

		@Override
		public String getMessage() {
			return null != message ? message : HttpResult.MESSAGE_SUCCESS;
		}

		@Override
		public int getCode() {
			return HttpResult.RESPONSE_SUCCESS;
		}

	}

	public static class SuccessResult<T> extends HttpResult<T> {

		@Override
		public String getMessage() {
			return null != message ? message : HttpResult.MESSAGE_SUCCESS;
		}

		@Override
		public int getCode() {
			return code != 0 ? code : HttpResult.RESPONSE_SUCCESS;
		}

	}

	public static class FailureResult<T> extends HttpResult<T> {

		@Override
		public String getMessage() {
			return null != message ? message : HttpResult.MESSAGE_FAILURE;
		}

		@Override
		public int getCode() {
			return code != 0 ? code : HttpResult.RESPONSE_FAILURE;
		}
	}

}
