package com.et.poster.controller;

import com.et.poster.service.PosterUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
	@RequestMapping(path = "/genPoster",produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] genposterpng( HttpServletRequest request,
			HttpServletResponse response) {
		try {
			byte[] bytes = PosterUtil.test(); //获得图片的Byte[]
			OutputStream out = null;
			try {
				out = response.getOutputStream();
				out.write(bytes);   //打印图片
				out.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}