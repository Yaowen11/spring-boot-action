package com.example.demo.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author zyw
 * @date 2020/7/13 20:17
 */
@Controller
@RequestMapping("/member")
@Log
public class MemberController {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/register")
    public String register() {
        return "secure/jdbc/register";
    }

    @PostMapping
    public String store(@RequestBody Member member, Model model) {
        log.info("member: " + member);
        model.addAttribute("member", memberRepository.save(member));
        return "secure/jdbc/show";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Member> memberList() {
        return memberRepository.findAll();
    }
}
