package com.example.demo.advice;

import com.adobe.xmp.impl.Base64;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author zyw
 * @date 2020/7/6 21:39
 */
@RestController
@Log
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/store")
    public String auth(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setUsername(password);
        User storeUser = userRepository.save(user);
        log.info("username: " + username + " password: " + password);
        log.info("store user id: " + storeUser.getId());
        String token = Base64.encode(storeUser.toString());
        log.info("token: " + token);
        return token;
    }

    @GetMapping("/show")
    public User show(@ModelAttribute("token") User user) {
        return user;
    }

    public void sayHi(Date date) {
        log.info("date: " + date.toString());
        userService.sayHi();
    }
}