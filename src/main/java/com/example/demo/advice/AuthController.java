package com.example.demo.advice;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * @author zyw
 * @date 2020/7/6 21:39
 */
@RestController
@Log
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("token")
    public User modelAttribute (HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("token");
        int id = Integer.parseInt(new String(org.bouncycastle.util.encoders.Base64.decode(bearerToken)));
        return userRepository.getOne(id);
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

    @GetMapping("/advice/show")
    public User show(@ModelAttribute("token") User user) {
        return user;
    }
}
