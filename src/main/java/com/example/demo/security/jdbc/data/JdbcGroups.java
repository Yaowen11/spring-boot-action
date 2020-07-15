package com.example.demo.security.jdbc.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author zyw
 * @date 2020/7/15 21:43
 */
@Entity
@Data
public class JdbcGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String groupName;

    private String authority;

}
