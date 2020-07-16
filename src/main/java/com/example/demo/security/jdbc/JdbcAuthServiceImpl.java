package com.example.demo.security.jdbc;

import com.example.demo.security.jdbc.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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
    public JdbcUsers store(JdbcUsers users) {
        if (jdbcUsersRepository.findFirstByUsername(users.getUsername()) != null) {
            return users;
        }
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        JdbcUsers jdbcUsers = jdbcUsersRepository.save(users);
        randGrantUserAuthorities(users.getUsername());
        randGrantUserGroup(users.getUsername());
        return jdbcUsers;
    }

    @Override
    public JdbcUsers user(String username) {
        return jdbcUsersRepository.findFirstByUsername(username);
    }

    private void randGrantUserAuthorities(String username) {
        final String[] roles = {"ROLE_JDBC_USER", "ROLE_JDBC_ADMIN", "ROLE_JDBC_MANAGER"};
        String role = roles[Math.abs(new Random().nextInt()) % roles.length];
        if (!jdbcUsersAuthoritiesRepository.existsByUsernameAndAuthority(username, role)) {
            JdbcUserAuthorities userAuthorities = new JdbcUserAuthorities();
            userAuthorities.setUsername(username);
            userAuthorities.setAuthority(role);
            jdbcUsersAuthoritiesRepository.save(userAuthorities);
        }
    }

    private void randGrantUserGroup(String username) {
        int groupId = Math.abs(new Random().nextInt()) % 4;
        if (!jdbcUserGroupsRepository.existsByUsernameAndGroupId(username, groupId)) {
            JdbcUserGroups jdbcUserGroups = new JdbcUserGroups();
            jdbcUserGroups.setGroupId(groupId == 0 ? 1 : groupId);
            jdbcUserGroups.setUsername(username);
            jdbcUserGroupsRepository.save(jdbcUserGroups);
        }
    }

    private void storeGroups() {
        final String[] groups = {"ROLE_GROUP_USER", "ROLE_GROUP_ADMIN", "ROLE_GROUP_MANAGER"};
        for (String group : groups) {
            JdbcGroups jdbcGroups = new JdbcGroups();
            jdbcGroups.setGroupName(group);
            jdbcGroups.setAuthority(group);
            jdbcGroupsRepository.save(jdbcGroups);
        }
    }

    @Override
    public List<JdbcGroups> groups() {
        return jdbcGroupsRepository.findAll();
    }

    @Override
    public List<JdbcUserGroups> userGroups() {
        return jdbcUserGroupsRepository.findAll();
    }

    @Override
    public List<JdbcUserGroups> userGroups(String username) {
        return jdbcUserGroupsRepository.findByUsername(username);
    }

    @Override
    public List<JdbcUsers> users() {
        return jdbcUsersRepository.findAll();
    }

    @Override
    public Map<String, List<?>> userRelation(String username) {
        Map<String, List<?>> userRelation = new HashMap<>();
        userRelation.put("userAuthorities", jdbcUsersAuthoritiesRepository.findByUsername(username));
        userRelation.put("userGroups", jdbcUserGroupsRepository.findByUsername(username));
        return userRelation;
    }

    @Override
    public List<String> userAuthorities(String username) {
        List<JdbcUserAuthorities> jdbcUserAuthorities = jdbcUsersAuthoritiesRepository.findByUsername(username);
        List<JdbcUserGroups> jdbcUserGroups = userGroups(username);
        List<Integer> jdbcUserGroupGroupIds = new ArrayList<>();
        for (JdbcUserGroups jdbcUserGroup : jdbcUserGroups) {
            jdbcUserGroupGroupIds.add(jdbcUserGroup.getGroupId());
        }
        List<JdbcGroups> jdbcGroups = jdbcGroupsRepository.findAllById(jdbcUserGroupGroupIds);
        List<String> userAuthorities = new LinkedList<>();
        jdbcUserAuthorities.forEach(jdbcUserAuthority -> userAuthorities.add(jdbcUserAuthority.getAuthority()));
        jdbcGroups.forEach(jdbcGroup -> userAuthorities.add(jdbcGroup.getAuthority()));
        return userAuthorities;
    }

    @Override
    public List<Map<String, List<String>>> userAuthorities() {
        List<JdbcUsers> jdbcUsers = jdbcUsersRepository.findAll();
        List<Map<String, List<String>>> usersAuthorities = new LinkedList<>();
        for (JdbcUsers jdbcUser : jdbcUsers) {
            usersAuthorities.add(Map.of(jdbcUser.getUsername(), userAuthorities(jdbcUser.getUsername())));
        }
        return usersAuthorities;
    }
}
