package com.et.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticResourceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // validate Referer
       String referer = httpRequest.getHeader("Referer");
        String allowedDomain = "http://localhost:8088";
        if (referer == null || !referer.startsWith(allowedDomain)) {
            httpResponse.getWriter().write("403 Forbidden: Hotlinking not allowed");
            return;
        }

        // validate Token
        if (!TokenValidator.validateToken(httpRequest, httpResponse)) {
            return;
        }

        // validate Timestamp
        if (!TimeValidator.validateTimestamp(httpRequest, httpResponse)) {
            return;
        }

        chain.doFilter(request, response);
    }
}