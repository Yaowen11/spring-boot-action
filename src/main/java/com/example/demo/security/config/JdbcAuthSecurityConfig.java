package com.example.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author zyw
 * @date 2020/7/15 20:40
 */
@EnableWebSecurity
public class JdbcAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public JdbcAuthSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        final String usersByUsernameQuery = "select username, password, enabled from jdbc_users where username = ?";
        final String authoritiesByUsernameQuery = "select username, authority from jdbc_user_authorities where username = ?";
        final String groupAuthoritiesByUsername = "select g.id, g.group_name, g.authority from jdbc_groups g, jdbc_user_groups ug where ug.username = ? and ug.group_id = g.id";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(usersByUsernameQuery)
                .authoritiesByUsernameQuery(authoritiesByUsernameQuery)
                .groupAuthoritiesByUsername(groupAuthoritiesByUsername)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .and()
                .rememberMe().tokenValiditySeconds(60 * 60 * 24).key("jdbc")
                .and()
                .authorizeRequests()
                .antMatchers("/auth/jdbc/user")
                .hasAnyRole("JDBC_USER", "JDBC_ADMIN", "JDBC_MANAGER", "GROUP_USER", "GROUP_ADMIN", "GROUP_MANAGER")
                .antMatchers("/auth/jdbc/admin")
                .hasAnyRole("JDBC_ADMIN", "JDBC_MANAGER", "GROUP_ADMIN", "GROUP_MANAGER")
                .antMatchers("/auth/jdbc/manager")
                .hasAnyRole("JDBC_MANAGER", "GROUP_MANAGER")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .cors()
                .and()
                .csrf().disable();
    }
}
