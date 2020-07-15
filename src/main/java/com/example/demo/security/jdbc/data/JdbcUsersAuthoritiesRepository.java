package com.example.demo.security.jdbc.data;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zyw
 * @date 2020/7/15 21:52
 */
public interface JdbcUsersAuthoritiesRepository extends JpaRepository<JdbcUserAuthorities, Integer> {
}
