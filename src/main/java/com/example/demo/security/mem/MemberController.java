package com.example.demo.security.mem;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author zyw
 * @date 2020/7/13 20:54
 */
@Controller
@RequestMapping("/auth/mem")
@Log
public class MemberController {

    @GetMapping("/{role}")
    public String admin(@PathVariable String role, Principal principal, Model model) {
        log.info("principal: " + principal);
        model.addAttribute("title", "page of " + role);
        model.addAttribute("name", principal.getName());
        return "secure/mem-role";
    }
}
