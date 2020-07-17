package com.example.demo.security.jdbc;

import com.example.demo.security.jdbc.data.JdbcUsers;
import com.example.demo.security.jdbc.data.JdbcUsersRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyw
 * @date 2020/7/15 22:28
 */
@Controller
@Log
public class JdbcAuthController {

    private final JdbcAuthService jdbcAuthService;

    private final JdbcUsersRepository jdbcUsersRepository;

    @Autowired
    public JdbcAuthController(JdbcAuthService jdbcAuthService, JdbcUsersRepository jdbcUsersRepository) {
        this.jdbcAuthService = jdbcAuthService;
        this.jdbcUsersRepository = jdbcUsersRepository;
    }

    @GetMapping("/jdbc/register")
    public String jdbcRegister(Model model) {
        model.addAttribute(new JdbcUsers());
        return "secure/jdbc/register";
    }

    @PostMapping("/jdbc/user/store")
    public String jdbcUserStore(@Valid JdbcUsers users, Errors errors, RedirectAttributes model) {
        if (errors.hasErrors()) {
            return "redirect:/jdbc/register";
        }
        JdbcUsers jdbcUsers = jdbcAuthService.store(users);
        if (jdbcUsers.getId() == null) {
            return "redirect:/jdbc/register";
        }
        model.addFlashAttribute("user", jdbcUsers);
        return "redirect:/jdbc/show/" + jdbcUsers.getUsername();
    }

    @GetMapping("/jdbc/show/{username}")
    public String jdbcShow(@PathVariable String username, Model model) {
        String userKey = "user";
        if (!model.containsAttribute(userKey)) {
            model.addAttribute(userKey, jdbcAuthService.user(username));
        }
        return "secure/jdbc/show";
    }

    @GetMapping("/jdbc/{auth}/list")
    @ResponseBody
    public List<?> jdbcGroups(@PathVariable String auth,
                              @RequestParam(required = false) String username) throws IllegalAccessException, InvocationTargetException {
        log.info("auth: " + auth + " username: " + username);
        Method[] methods = jdbcAuthService.getClass().getDeclaredMethods();
        Map<String, Method> noParamsMethodMap = new HashMap<>();
        Map<String, Method> usernameParamsMethodMap = new HashMap<>();
        for (Method method : methods) {
            if (method.getParameterCount() == 0 && method.getReturnType() == List.class) {
                noParamsMethodMap.put(method.getName(), method);
                continue;
            }
            if (method.getParameterCount() == 1 && method.getReturnType() == List.class) {
                usernameParamsMethodMap.put(method.getName(), method);
            }
        }
        if (username == null) {
            Method noParamMethod = noParamsMethodMap.get(auth);
            return (List<?>) noParamMethod.invoke(jdbcAuthService);
        }
        Method usernameParamMethod = usernameParamsMethodMap.get(auth);
        return (List<?>) usernameParamMethod.invoke(jdbcAuthService, username);
    }

    @GetMapping("/jdbc/{username}/relation")
    @ResponseBody
    public Map<String, List<?>> userRelation(@PathVariable String username) {
        return jdbcAuthService.userRelation(username);
    }

    @PostMapping("/generate/{size}")
    @CrossOrigin(origins = "*")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void generate(@PathVariable int size) {
        for (int i = 0; i < size; i++) {
            JdbcUsers jdbcUsers = new JdbcUsers();
            jdbcUsers.setUsername("guest" + i);
            jdbcUsers.setPassword("123123");
            jdbcUsers.setEnabled("true");
            jdbcAuthService.store(jdbcUsers);
        }
    }

    @DeleteMapping("/flush")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public void flushUser() {
        jdbcUsersRepository.deleteAll();
    }
}
