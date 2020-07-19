package com.example.demo.security.customize;

import com.example.demo.security.customize.data.CustomizeUser;
import com.example.demo.security.customize.data.CustomizeAuthority;
import java.util.List;
import java.util.Map;

/**
 * @author zyw
 * @date 2020/7/19 15:58
 */
public interface CustomizeAuthService {
    CustomizeUser store(CustomizeUser user);
    CustomizeUser getOneByUsername(String username);
    List<CustomizeAuthority> userAuthorities(String username);
    Map<String, List<CustomizeAuthority>> userAuthorities();
    List<CustomizeUser> users();
    void flush();
}
