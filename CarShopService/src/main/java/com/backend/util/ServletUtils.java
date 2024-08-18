package com.backend.util;

import jakarta.servlet.http.HttpServletRequest;

public class ServletUtils {

    public static String getAction (HttpServletRequest request) {
        String uri = request.getRequestURI();
        String[] pathParts = uri.split("/");

        return pathParts[pathParts.length - 1];
    }

}
