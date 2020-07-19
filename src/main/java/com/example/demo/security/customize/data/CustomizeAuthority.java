package com.example.demo.security.customize.data;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zyw
 * @date 2020/7/18 21:26
 */
@Entity
@Data
public class CustomizeAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String authority;
}
