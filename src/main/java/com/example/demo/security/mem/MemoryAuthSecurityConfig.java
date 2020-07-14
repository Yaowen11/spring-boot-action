package com.example.demo.security.mem;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author zyw
 * @date 2020/7/12 10:45
 */
@EnableWebSecurity
public class MemoryAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String password = new BCryptPasswordEncoder().encode("123123");

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/auth/mem/user").hasAnyRole("MEMORY_USER", "MEMORY_ADMIN", "MEMORY_MANAGER")
                .antMatchers("/auth/mem/admin").hasAnyRole("MEMORY_ADMIN", "MEMORY_MANAGER")
                .antMatchers("/auth/mem/manager").hasAnyRole("MEMORY_MANAGER")
                .anyRequest().permitAll()
                .and()
                .rememberMe().tokenValiditySeconds(60 * 60 * 24 * 30).key("memory")
                .and()
                .logout().logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(password).roles("MEMORY_USER")
                .and()
                .withUser("admin").password(password).roles("MEMORY_USER", "MEMORY_ADMIN")
                .and()
                .withUser("manager").password(password).roles("MEMORY_USER", "MEMORY_ADMIN", "MEMORY_MANAGER");

    }
}
