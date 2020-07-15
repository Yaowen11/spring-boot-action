package com.example.demo.security.jdbc;

import com.example.demo.security.jdbc.data.JdbcUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * @author zyw
 * @date 2020/7/15 22:28
 */
@Controller
public class JdbcAuthController {

    private final JdbcAuthService jdbcAuthService;

    @Autowired
    public JdbcAuthController(JdbcAuthService jdbcAuthService) {
        this.jdbcAuthService = jdbcAuthService;
    }

    @GetMapping("/jdbc/register")
    public String jdbcRegister() {
        return "secure/jdbc/register";
    }

    @PostMapping("/jdbc/user/store")
    public String jdbcUserStore(JdbcUsers users) {
        jdbcAuthService.store(users);
        return "redirect:/jdbc/show";
    }

    @GetMapping("/jdbc/show")
    public String jdbcShow(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "secure/jdbc/show";
    }
}
