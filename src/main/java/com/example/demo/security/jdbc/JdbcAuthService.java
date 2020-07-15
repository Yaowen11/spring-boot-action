package com.example.demo.security.jdbc;

import com.example.demo.security.jdbc.data.JdbcUsers;

/**
 * @author zyw
 * @date 2020/7/15 22:55
 */
public interface JdbcAuthService {
    /**
     * store
     * @param users jdbc user
     */
    void store(JdbcUsers users);
}
