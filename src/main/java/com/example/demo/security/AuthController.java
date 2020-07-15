package com.example.demo.security;

import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

/**
 * @author zyw
 * @date 2020/7/12 21:24
 */
@Controller
@Log
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "secure/login";
    }

    @GetMapping("/me")
    public String me(Model model,
                     Principal principal) {
        log.info("principal is null: " + (principal == null));
        log.info("principal: " + principal.toString());
        model.addAttribute("name", principal.getName());
        return "secure/me";
    }

    @GetMapping("/auth/{type}/{role}")
    public String user(@PathVariable String type,
                       @PathVariable String role,
                       Authentication authentication,
                       Principal principal,
                       Model model) {
        log.info("authorities: " + authentication.getAuthorities());
        log.info("principal name: " + principal.getName());
        model.addAttribute("type", type);
        model.addAttribute("role", role);
        model.addAttribute("name", principal.getName());
        return "secure/role";
    }

}
