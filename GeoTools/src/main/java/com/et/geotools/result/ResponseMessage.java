package com.et.geotools.result;

public enum ResponseMessage {

	/**
	 * 成功
	 */
	OK(200,"成功"),
	
	/**
	 * 失败
	 */
	BAD_REQUEST(400,"错误的请求");

	
	private final int status;
	private final String message;
	
	 ResponseMessage(int status, String message){
		this.status = status;
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	
}
