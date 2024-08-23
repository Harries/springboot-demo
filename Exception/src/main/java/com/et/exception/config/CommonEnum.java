package com.et.exception.config;

public enum CommonEnum implements BaseErrorInfoInterface {
	SUCCESS("200", "success!"),
	BODY_NOT_MATCH("400","format is wrong!"),
	SIGNATURE_NOT_MATCH("401","signature is wrong!"),
	NOT_FOUND("404", "unfind resource!"),
	INTERNAL_SERVER_ERROR("500", "inner error!"),
	SERVER_BUSY("503","server is busy ,please try it again after a moment!")
	;


	private String resultCode;


	private String resultMsg;

	CommonEnum(String resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	@Override
	public String getResultCode() {
		return resultCode;
	}

	@Override
	public String getResultMsg() {
		return resultMsg;
	}

}

