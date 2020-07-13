package com.example.demo.security.mem;

import com.example.demo.security.MemoryAuthSecurityMapping;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Map;


/**
 * @author zyw
 * @date 2020/7/12 10:45
 */
@EnableWebSecurity
public class MemoryAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String rolePrefix = "MEMORY_";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String authPrefix = "/auth/mem/";
        Map<String, String[]> urlMapping = MemoryAuthSecurityMapping.urlRoleMap(authPrefix, rolePrefix);
        http.formLogin()
                .loginPage("/login")
                .and()
                .authorizeRequests()
                .antMatchers(authPrefix + MemoryAuthSecurityMapping.users[0]).hasAnyRole(urlMapping.get(authPrefix + MemoryAuthSecurityMapping.users[0]))
                .antMatchers(authPrefix + MemoryAuthSecurityMapping.users[1]).hasAnyRole(urlMapping.get(authPrefix + MemoryAuthSecurityMapping.users[1]))
                .antMatchers(authPrefix + MemoryAuthSecurityMapping.users[2]).hasAnyRole(urlMapping.get(authPrefix + MemoryAuthSecurityMapping.users[2]))
                .anyRequest().permitAll()
                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 30)
                .key("auth")
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        Map<String, String[]> userRoleMapping = MemoryAuthSecurityMapping.userRoleMap(rolePrefix);
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser(MemoryAuthSecurityMapping.users[0]).password(MemoryAuthSecurityMapping.password).roles(userRoleMapping.get(MemoryAuthSecurityMapping.users[0]))
                .and()
                .withUser(MemoryAuthSecurityMapping.users[1]).password(MemoryAuthSecurityMapping.password).roles(userRoleMapping.get(MemoryAuthSecurityMapping.users[1]))
                .and()
                .withUser(MemoryAuthSecurityMapping.users[2]).password(MemoryAuthSecurityMapping.password).roles(userRoleMapping.get(MemoryAuthSecurityMapping.users[2]));

    }
}
