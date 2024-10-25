package com.et.exception;

public class UserFriendlyException extends RuntimeException {

	public UserFriendlyException() {
	}

	public UserFriendlyException(String message) {
		super(message);
	}

	public UserFriendlyException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserFriendlyException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode.getCode();
	}

	public UserFriendlyException(Throwable cause) {
		super(cause);
	}

	public UserFriendlyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	private int httpStatusCode = 200;

	private int errorCode = ErrorCode.serverError.getCode();

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public UserFriendlyException setHttpStatusCode(int statusCode) {
		this.httpStatusCode = statusCode;
		return this;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public UserFriendlyException setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}
}
