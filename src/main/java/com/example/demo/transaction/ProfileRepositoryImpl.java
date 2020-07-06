package com.example.demo.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zyw
 * @date 2020/7/5 13:01
 */
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    @Autowired private JdbcTemplate jdbcTemplate;

    @Override
    public Profile getOne(int id) {
        try {
            return jdbcTemplate.queryForObject("select * from profile where id = ?", new Object[]{id}, new ProfileRowMapping());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(Profile profile) {
        jdbcTemplate.update("insert into profile (id, first_name, last_name, password) values (?, ?, ?, ?)",
                profile.getId(),profile.getFirstName(),profile.getLastName(),profile.getPassword());
    }

    private static class ProfileRowMapping implements RowMapper<Profile> {
        @Override
        public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
            Profile profile = new Profile();
            profile.setId(rs.getInt("id"));
            profile.setFirstName(rs.getString("first_name"));
            profile.setLastName(rs.getString("last_name"));
            profile.setPassword(rs.getString("password"));
            return profile;
        }
    }
}
