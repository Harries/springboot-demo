package com.et.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenValidator {

    public static boolean validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");
        String validToken = "your-predefined-token"; //set your predefined token here

        if (token == null || !token.equals(validToken)) {
            response.getWriter().write("403 Forbidden: Invalid Token");
            return false;
        }

        return true;
    }
}