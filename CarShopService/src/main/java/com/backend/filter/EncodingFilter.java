package com.backend.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "Filter", value = "/*")
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getServletPath().startsWith("/api/")) {
            resp.setContentType("application/json;charset=UTF-8");
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
        }

        chain.doFilter(request, response);
    }
}