package com.example.demo.security;

import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/auth")
@Log
public class MemoryAuthController {


}
