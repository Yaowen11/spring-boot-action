package com.example.demo.security.jdbc.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author zyw
 * @date 2020/7/15 21:46
 */
@Entity
@Data
public class JdbcUserGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer groupId;

    private String username;
}
