package com.example.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zyw
 * @date 2020/7/5 12:44
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Profile Not Found")
public class ProfileNotFoundException extends RuntimeException {}
