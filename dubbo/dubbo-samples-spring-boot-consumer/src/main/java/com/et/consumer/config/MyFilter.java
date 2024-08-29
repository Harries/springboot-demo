package com.et.consumer.config;

import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put("TRACKING_LOG_SESSION_TOKEN_ID", UUID.randomUUID().toString().toUpperCase());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove("TRACKING_LOG_SESSION_TOKEN_ID");
        }

    }

}
