package com.example.demo.security.jdbc.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zyw
 * @date 2020/7/15 21:52
 */
public interface JdbcUsersAuthoritiesRepository extends JpaRepository<JdbcUserAuthorities, Integer> {
    /**
     * find by username
     * @param username username
     * @return list
     */
    List<JdbcUserAuthorities> findByUsername(String username);

    /**
     * exists
     * @param username username
     * @param authority authority
     * @return boolean
     */
    boolean existsByUsernameAndAuthority(String username, String authority);
}
