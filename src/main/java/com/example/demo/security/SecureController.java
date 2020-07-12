package com.example.demo.security;

import com.example.demo.advice.User;
import com.example.demo.advice.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

/**
 * @author zyw
 * @date 2020/7/12 10:39
 */
@Controller
public class SecureController {

    private final UserRepository userRepository;

    @Autowired
    public SecureController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/form")
    public String form() {
        return "secure/form";
    }

    @PostMapping("/form/store")
    public String store(@RequestParam String name,
                        @RequestParam String password,
                        RedirectAttributes model) {
        System.out.println(name + password);
        User user = userRepository.save(new User(name, password));
        model.addAttribute("userId", user.getId());
        model.addAttribute("name", name);
        model.addFlashAttribute("user", user);
        return "redirect:/show/{userId}";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable int id, Model model) {
        final String userAttribute = "user";
        System.out.println("flash attribute is exists: " + model.containsAttribute(userAttribute));
        if (!model.containsAttribute(userAttribute)) {
            model.addAttribute(userAttribute, userRepository.getOne(id));
        }
        return "secure/show";
    }

    @GetMapping("/admin")
    public String admin() { return "secure/admin"; }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("msg", LocalDateTime.now().toString());
        return "secure/index";
    }
}
