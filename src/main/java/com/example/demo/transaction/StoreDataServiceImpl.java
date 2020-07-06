package com.example.demo.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zyw
 * @date 2020/6/28 21:23
 */
@Service
public class StoreDataServiceImpl implements StoreDataService {

    private final TransactionTemplate transactionTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final JdbcTemplate jdbcTemplate;

    public StoreDataServiceImpl(TransactionTemplate transactionTemplate,
                                NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                JdbcTemplate jdbcTemplate) {
        this.transactionTemplate = transactionTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int indexInsert(Profile profile) {
        final String insertSql = "insert into profile (first_name, last_name, username, password) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, profile.getFirstName());
            preparedStatement.setString(2, profile.getLastName());
            preparedStatement.setString(3, profile.getUsername());
            preparedStatement.setString(4, profile.getPassword());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public int nameParamInsert(Profile profile) {
        final String sql = "insert into profile (first_name,last_name, username, password) " +
                "values (:firstName,:lastName, :username, :password)";
        Map<String, Object> nameParams = new HashMap<>(3);
        nameParams.put("firstName", profile.getFirstName());
        nameParams.put("lastName", profile.getLastName());
        nameParams.put("username", profile.getUsername());
        nameParams.put("password", profile.getPassword());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new SqlParameterSource() {
            @Override
            public boolean hasValue(String paramName) {
                return nameParams.containsKey(paramName);
            }
            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                return nameParams.get(paramName);
            }
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public int editTransactionInsert(Profile profile) {
        return transactionTemplate.execute(transactionStatus -> {
            try {
                return nameParamInsert(profile);
            } catch (RuntimeException e) {
                transactionStatus.setRollbackOnly();
                throw e;
            }
        });
    };

    @Override
    @Transactional(
            isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class}
    )
    public int annotationTransactionInsert(Profile profile) {
        return indexInsert(profile);
    }
}
