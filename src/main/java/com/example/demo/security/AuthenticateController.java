package com.example.demo.security;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @author zyw
 * @date 2020/7/12 21:24
 */
@Controller
@Log
public class AuthenticateController {

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

    @GetMapping("/auth/admin")
    public String admin() { return "secure/admin"; }

    @GetMapping("/auth/user")
    public String user() { return "secure/user"; }

    @GetMapping("/auth/manager")
    public String manager() { return "secure/manager"; }

}
