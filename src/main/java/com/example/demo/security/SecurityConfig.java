package com.example.demo.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author zyw
 * @date 2020/7/12 10:45
 */
@EnableWebSecurity
@Log
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("admin")
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//                .loginPage("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/auth/admin").hasRole("ADMIN")
                .antMatchers("/auth/user").hasRole("USER")
                .antMatchers("/auth/manager").hasRole("MANAGER")
                .anyRequest().permitAll()
                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 30)
                .key("auth")
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

    /*
      内存用户
      @param auth auth
     * @throws Exception ex

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user")
                .password(new BCryptPasswordEncoder().encode("123123"))
                .roles("USER", "MANAGER");
    }
    */

    /*
        自定义配置用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new StandardPasswordEncoder());
    }
    */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from member where username = ?")
                .authoritiesByUsernameQuery("select username, authority from member_authority where username = ?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
