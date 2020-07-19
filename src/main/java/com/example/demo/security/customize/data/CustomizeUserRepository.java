package com.example.demo.security.customize.data;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zyw
 * @date 2020/7/19 10:38
 */
public interface CustomizeUserRepository extends JpaRepository<CustomizeUser, Integer> {

    /**
     * find by username
     * @param username string
     * @return customize user
     */
    CustomizeUser findByUsername(String username);
}
