package com.example.demo.advice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zyw
 * @date 2020/7/6 22:05
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
