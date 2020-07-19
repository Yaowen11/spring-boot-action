package com.example.demo.security.customize.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zyw
 * @date 2020/7/19 16:25
 */
public interface CustomizeAuthorityRepository extends JpaRepository<CustomizeAuthority, Integer> {
    List<CustomizeAuthority> findByUserId(Integer userId);
}
