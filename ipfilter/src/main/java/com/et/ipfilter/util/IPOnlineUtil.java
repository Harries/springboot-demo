package com.et.ipfilter.util;

import com.et.ipfilter.dto.CountryInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName IPOnlineUtil
 * @Description todo
 * @date 2024/08/09/ 9:58
 */

public class IPOnlineUtil {
    private static RestTemplate restTemplate;

    private final RestTemplate template;

    public static final String IP_API_URL = "http://ip-api.com/json/";

    public IPOnlineUtil(RestTemplate restTemplate) {
        this.template = restTemplate;
    }

    /**
     * init RestTemplate
     */
    @PostConstruct
    public void init() {
        setRestTemplate(this.template);
    }

    /**
     * init RestTemplate
     */
    private static void setRestTemplate(RestTemplate template) {
        restTemplate = template;
    }

    /**
     * get country by ip
     *
     * @param ip
     * @return
     */
    public static CountryInfo getCountryByIpOnline(String ip) {
        ResponseEntity<CountryInfo> entity = restTemplate.getForEntity(
                IP_API_URL + ip + "?lang=zh-CN",
                CountryInfo.class
        );
        return entity.getBody();


    }
}
