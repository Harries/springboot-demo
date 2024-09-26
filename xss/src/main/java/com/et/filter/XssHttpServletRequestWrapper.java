package com.et.filter;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * XSS filter process
 * 
 * @author Frank
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    @Override
    public String[] getParameterValues(String name) {
        // Customize rules based on actual needs. Here, the content field is rich text and does not need to be filtered.
        if ("content".equals(name)) {
            return super.getParameterValues(name);
        }
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                // Prevent xss attacks and filter leading and trailing spaces
                escapseValues[i] = Jsoup.clean(values[i], Whitelist.relaxed()).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }
}