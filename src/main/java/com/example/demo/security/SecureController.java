package com.example.demo.security;

import com.example.demo.advice.User;
import com.example.demo.advice.UserRepository;
import com.example.demo.tools.Hash;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zyw
 * @date 2020/7/12 10:39
 */
@Controller
@Log
public class SecureController {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    @Autowired
    public SecureController(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
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
        return "redirect:/secure/show/{userId}";
    }

    @GetMapping("/secure/show/{id}")
    public String show(@PathVariable int id, Model model) {
        final String userAttribute = "user";
        System.out.println("flash attribute is exists: " + model.containsAttribute(userAttribute));
        if (!model.containsAttribute(userAttribute)) {
            model.addAttribute(userAttribute, userRepository.getOne(id));
        }
        return "secure/show";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("msg", LocalDateTime.now().toString());
        return "secure/index";
    }

    @GetMapping("/admins")
    @ResponseBody
    public List<Admin> adminList() {
        List<Admin> list = adminRepository.findAll();
        if (list.isEmpty()) {
            final String pass = Hash.hash("123123", "SHA-256");
            log.info("hash: " + pass);
            for (String admin: new String[]{"admin", "user", "manager"}) {
                Admin adminTemp = new Admin();
                adminTemp.setUsername(admin);
                adminTemp.setPassword(pass);
                adminTemp.setType((int) System.currentTimeMillis() % 3);
                list.add(adminTemp);
            }
            return adminRepository.saveAll(list);
        }
        return list;
    }
}
