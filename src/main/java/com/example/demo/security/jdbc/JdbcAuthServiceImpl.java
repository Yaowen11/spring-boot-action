package com.example.demo.security.jdbc;

import com.example.demo.security.jdbc.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zyw
 * @date 2020/7/15 22:56
 */
@Service
public class JdbcAuthServiceImpl implements JdbcAuthService {

    private final JdbcUsersRepository jdbcUsersRepository;

    private final JdbcUserGroupsRepository jdbcUserGroupsRepository;

    private final JdbcUsersAuthoritiesRepository jdbcUsersAuthoritiesRepository;

    private final JdbcGroupsRepository jdbcGroupsRepository;

    @Autowired
    public JdbcAuthServiceImpl(JdbcUsersRepository jdbcUsersRepository,
                           JdbcUserGroupsRepository jdbcUserGroupsRepository,
                           JdbcUsersAuthoritiesRepository jdbcUsersAuthoritiesRepository,
                           JdbcGroupsRepository jdbcGroupsRepository) {
        this.jdbcUsersRepository = jdbcUsersRepository;
        this.jdbcGroupsRepository = jdbcGroupsRepository;
        this.jdbcUserGroupsRepository = jdbcUserGroupsRepository;
        this.jdbcUsersAuthoritiesRepository = jdbcUsersAuthoritiesRepository;
        storeGroups();
    }

    @Override
    public void store(JdbcUsers users) {
        jdbcUsersRepository.save(users);
        userAuthorities(users.getUsername());
        userGroup(users.getUsername());
    }

    private void userAuthorities(String username) {
        final String[] roles = {"JDBC_USER", "JDBC_ADMIN", "JDBC_MANAGER"};
        for (int i = 0; i < System.currentTimeMillis() % roles.length; i++) {
            JdbcUserAuthorities userAuthorities = new JdbcUserAuthorities();
            userAuthorities.setUsername(username);
            userAuthorities.setAuthority(roles[i]);
            jdbcUsersAuthoritiesRepository.save(userAuthorities);
        }
    }

    private void userGroup(String username) {
        int size = 3;
        for (int i = 0; i < System.currentTimeMillis() % size; i++) {
            JdbcUserGroups jdbcUserGroups = new JdbcUserGroups();
            jdbcUserGroups.setGroupId(i);
            jdbcUserGroups.setUsername(username);
            jdbcUserGroupsRepository.save(jdbcUserGroups);
        }
    }

    private void storeGroups() {
        final String[] groups = {"GROUP_USER", "GROUP_ADMIN", "GROUP_MANAGER"};
        for (String group : groups) {
            JdbcGroups jdbcGroups = new JdbcGroups();
            jdbcGroups.setGroupName(group);
            jdbcGroups.setAuthority("JDBC" + group);
            jdbcGroupsRepository.save(jdbcGroups);
        }
    }
}
