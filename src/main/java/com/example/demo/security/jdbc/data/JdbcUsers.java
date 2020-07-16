package com.example.demo.security.jdbc.data;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zyw
 * @date 2020/7/15 21:32
 */
@Entity
@Data
public class JdbcUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    @Size(min = 6, message = "{密码长度必须大于等于6}")
    private String password;

    private String enabled;
}
