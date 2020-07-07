package com.example.demo.advice;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;

/**
 * @author zyw
 * @date 2020/7/6 21:39
 */
@RestController
@Log
public class AuthController {

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public AuthController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/auth/store")
    public String auth(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setUsername(password);
        User storeUser = userRepository.save(user);
        log.info("username: " + username + " password: " + password);
        log.info("store user id: " + storeUser.getId());
        String token = Base64.getEncoder().encodeToString(storeUser.toString().getBytes());
        log.info("token: " + token);
        return token;
    }

    @GetMapping("/say")
    public void sayHi(@RequestParam Date date) {
        log.info("date: " + date);
        userService.sayHi();
    }
}
