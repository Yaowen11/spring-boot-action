package com.example.demo.advice;

import com.example.demo.transaction.Profile;
import com.example.demo.transaction.ProfileRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zyw
 * @date 2020/7/5 11:44
 */
@Controller
@Log
public class ErrorController {

    private final ProfileRepository profileRepository;

    @Autowired
    public ErrorController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping("/bad")
    public String bad() {
        return "index";
    }

    @GetMapping("/profiles/{id}")
    public String profile(@PathVariable int id, Model model) {
        Profile profile = profileRepository.getOne(id);
        if (profile == null) {
            throw new ProfileNotFoundException();
        }
        model.addAttribute("profile", profile);
        return "profile";
    }

    @PostMapping("/profiles/profile")
    public Profile store(Profile profile) {
        profileRepository.save(profile);
        return profile;
    }
}
