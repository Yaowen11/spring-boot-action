package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zyw
 * @date 2020/7/13 21:55
 */
public class MemoryAuthSecurityMapping {

    public static final String password = new BCryptPasswordEncoder().encode("123123");

    public static final String[] users = {"user", "admin", "manager"};

    public static Map<String, String[]> userRoleMap(String rolePrefix) {
        Map<String, String[]> userRoleMap = new HashMap<>();
        for (int i = 0; i < users.length; i++) {
            String[] roles = new String[i + 1];
            for (int l = 0; l <= i; l++) {
                roles[l] = rolePrefix + users[i].toUpperCase();
            }
            userRoleMap.put(users[i], roles);
        }
        return userRoleMap;
    }

    public static Map<String, String[]> urlRoleMap(String urlPrefix, String rolePrefix) {
        Map<String, String[]> urlRoleMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : userRoleMap(rolePrefix).entrySet()) {
            urlRoleMap.put(urlPrefix + entry.getKey(), entry.getValue());
        }
        return urlRoleMap;
    }
}
