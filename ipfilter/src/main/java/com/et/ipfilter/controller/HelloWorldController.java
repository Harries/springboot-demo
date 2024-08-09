package com.et.ipfilter.controller;

import com.et.ipfilter.util.AddressUtils;
import com.et.ipfilter.util.IPOfflineUtil;
import com.et.ipfilter.util.IPOnlineUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String ip){

        //fisrt get ip addr by offline file
        //String ip = IPOfflineUtil.getIpAddr(httpServletRequest);
        //analyze address
        String addr = IPOfflineUtil.getAddr(ip);
        if(StringUtils.isEmpty(addr)) {
            //get addr by online service
            ip = IPOfflineUtil.getIpAddr(httpServletRequest);
            addr= AddressUtils.getRealAddressByIP(ip);
            log.info("IP >> {},Address >> {}", ip, addr);
			// you can filter by country or province
        }
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        map.put("ipaddr", addr);
        return map;
    }
}