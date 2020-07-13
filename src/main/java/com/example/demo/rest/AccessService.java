package com.example.demo.rest;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author z
 */
public interface AccessService {
    /**
     * access
     * @param userPass user pass
     * @return response entity
     */
    ResponseEntity<String> login(Map<String, String> userPass);

    /**
     * access
     * @param userPass user pass
     * @param servletResponse servlet response
     */
    void access(Map<String, String> userPass, HttpServletResponse servletResponse);
}
