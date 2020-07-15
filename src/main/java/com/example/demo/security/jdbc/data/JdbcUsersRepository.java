package com.example.demo.security.jdbc.data;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zyw
 * @date 2020/7/15 21:55
 */
public interface JdbcUsersRepository extends JpaRepository<JdbcUsers, Integer> {
}
