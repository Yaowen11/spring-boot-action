package com.example.demo.advice;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * @author z
 */
@RestController
@Log
public class IndexController {
    private final UserRepository userRepository;

    @Autowired
    public IndexController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public User modelAttribute (HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("token");
        log.info("bearerToken: " + bearerToken);
        int id = Integer.parseInt(new String(Base64.getDecoder().decode(bearerToken.getBytes())));
        return userRepository.getOne(id);
    }

    @GetMapping("/show")
    @ModelAttribute("age")
    public String show() {
        return "100";
    }
}
