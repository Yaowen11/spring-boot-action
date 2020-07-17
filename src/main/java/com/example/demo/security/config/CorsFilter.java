package com.example.demo.security.config;

import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author z
 */
//@WebFilter(urlPatterns = {"/generate/*", "/flush"})
@Log
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) request;
        String requestMethod = httpServletRequest.getMethod();
        log.info("request path: " + httpServletRequest.getPathInfo());
        log.info("request method: " + requestMethod);
//        if ("OPTIONS".equals(requestMethod)) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
//            httpServletResponse.setHeader("Access-Control-Allow-Credentials", true);
//            httpServletResponse.setHeader("Access-Control-Max-Age", 180);
            chain.doFilter(httpServletRequest, httpServletResponse);
//        }
    }
}
