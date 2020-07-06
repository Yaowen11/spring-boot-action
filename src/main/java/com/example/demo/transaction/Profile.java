package com.example.demo.transaction;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author zyw
 * @date 2020/6/28 21:27
 */
@Data
@Entity
public class Profile {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
