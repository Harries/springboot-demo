package com.et.lombok.model;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.*;

public class Main2 {
	@SneakyThrows(Exception.class)
	public static void main(String[] args) {
		@Cleanup InputStream in = new FileInputStream(args[0]);
		@Cleanup OutputStream out = new FileOutputStream(args[1]);
		byte[] b = new byte[1024];
		while (true) {
			int r = in.read(b);
			if (r == -1) break;
			out.write(b, 0, r);
		}
	}

}
