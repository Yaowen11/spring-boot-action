package com.example.demo.security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author z
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    /**
     * get admin by user
     * @param username username
     * @return admin
     */
    Admin findByUsername(String username);
}
