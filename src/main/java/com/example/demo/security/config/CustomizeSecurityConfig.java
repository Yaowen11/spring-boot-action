package com.example.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author zyw
 * @date 2020/7/19 10:46
 */
@EnableWebSecurity
public class CustomizeSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public CustomizeSecurityConfig(@Qualifier("customize") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder())
                .and()
                .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("boss").password(new BCryptPasswordEncoder().encode("123123")).roles("MEMORY_BOSS");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .rememberMe().tokenValiditySeconds(144000).key("customize")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/auth/customize/user")
                .hasAnyRole("CUSTOMIZE_USER", "CUSTOMIZE_ADMIN", "CUSTOMIZE_MANAGER", "MEMORY_BOSS")
                .antMatchers("/auth/customize/admin")
                .hasAnyRole("CUSTOMIZE_ADMIN", "CUSTOMIZE_MANAGER", "MEMORY_BOSS")
                .antMatchers("/auth/customize/manager")
                .hasAnyRole("CUSTOMIZE_MANAGER", "MEMORY_BOSS")
                .and()
                .cors()
                .and().csrf().ignoringAntMatchers("/customize/generate/*").ignoringAntMatchers("/customize/flush");
    }
}
