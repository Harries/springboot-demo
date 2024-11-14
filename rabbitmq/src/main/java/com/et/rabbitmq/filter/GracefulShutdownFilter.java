package com.et.rabbitmq.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
@Component
public class GracefulShutdownFilter implements Filter {

    private final AtomicBoolean shuttingDown = new AtomicBoolean(false);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (shuttingDown.get()) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
        chain.doFilter(request, response);
    }

    public void startShutdown() {
        shuttingDown.set(true);
    }
}