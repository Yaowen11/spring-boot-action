package com.example.demo.advice;

import lombok.extern.java.Log;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zyw
 * @date 2020/7/6 21:09
 */
@RestControllerAdvice
@Log
public class AppWideExceptionHandler {

    private final UserRepository userRepository;

    @Autowired
    public AppWideExceptionHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("token")
    public User modelAttribute (HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("token");
        int id = Integer.parseInt(new String(Base64.decode(bearerToken)));
        return userRepository.getOne(id);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void runTimeException(Exception e) {
        log.info("execute e: " + e.getClass().getSimpleName());
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
