package com.example.demo.security.customize;

import com.example.demo.security.customize.data.CustomizeUser;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zyw
 * @date 2020/7/19 11:10
 */
@Controller
@RequestMapping("/customize")
@Log
public class CustomizeAuthController {

    private final CustomizeAuthService customizeAuthService;

    @Autowired
    public CustomizeAuthController(CustomizeAuthService customizeAuthService) {
        this.customizeAuthService = customizeAuthService;
    }

    @GetMapping("/register")
    public String register() {
        return "secure/customize/register";
    }

    @PostMapping("/store")
    public String store(CustomizeUser user, RedirectAttributes model) {
        CustomizeUser dbUser = customizeAuthService.store(user);
        log.info("db user: " + dbUser);
        model.addFlashAttribute("user", dbUser);
        return "redirect:/customize/show/" + user.getUsername();
    }

    @PostMapping("/customize/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest httpServletRequest,
                        HttpSession httpSession) {
        CustomizeUser customizeUser = customizeAuthService.getOneByUsername(username);
        httpSession.setAttribute("role", customizeUser.getCustomizeUserAuthorities());
        String path = httpServletRequest.getPathInfo();
        log.info("username: " + username + " password: " + password);
        log.info("path: " + path);
        return "redirect:" + path;
    }

    @GetMapping("/show/{username}")
    public String show(@PathVariable String username, Model model) {
        String userKey = "user";
        log.info("user exists: " + model.containsAttribute(userKey));
        if (!model.containsAttribute(userKey)) {
            model.addAttribute(userKey, customizeAuthService.getOneByUsername(username));
        }
        return "secure/show";
    }

    @GetMapping("/users")
    @ResponseBody
    public List<CustomizeUser> users() {
        return customizeAuthService.users();
    }

    @GetMapping("/user/{name}")
    @ResponseBody
    public CustomizeUser user(@PathVariable String name) {
        return customizeAuthService.getOneByUsername(name);
    }

    @PostMapping("/generate/{size}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void generate(@PathVariable Integer size) {
        final String username = "user";
        final String password = "123123";
        final String enabled = "true";
        for (int i = 0; i < size; i++) {
            CustomizeUser temp = new CustomizeUser();
            temp.setUsername(username + i);
            temp.setPassword(password);
            temp.setEnabled(enabled);
            customizeAuthService.store(temp);
        }
    }

    @DeleteMapping("/flush")
    public void flush() {
        customizeAuthService.flush();
    }
}
