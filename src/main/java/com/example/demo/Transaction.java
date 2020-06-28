package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zyw
 * @date 2020/6/28 21:23
 */
@Service
public class Transaction implements StoreDataService {

    private final TransactionTemplate transactionTemplate;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Transaction(TransactionTemplate transactionTemplate, JdbcTemplate jdbcTemplate) {
        this.transactionTemplate = transactionTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(Profile profile) {
        String insertSql = "insert into profile (first_name, last_name, password) values (:firstName, :lastName, :password)";
        Map<String, String> params = new HashMap<>();
        params.put("firstName", profile.getFirstName());
        params.put("lastName", profile.getLastName());
        params.put("password", profile.getPassword());
        return transactionTemplate.execute(transactionStatus -> {
            try {
                return jdbcTemplate.update(insertSql, params);
            } catch (RuntimeException e) {
                transactionStatus.setRollbackOnly();
                throw e;
            }
        });
    }
}
