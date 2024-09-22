package com.et.lombok.model;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;

public class Main1 {

	@SneakyThrows(UnsupportedEncodingException.class)
	public String utf8ToString(byte[] bytes) {
		return new String(bytes, "UTF8");
	}

}
