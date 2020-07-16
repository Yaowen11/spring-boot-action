package com.example.demo.security.jdbc;

import com.example.demo.security.jdbc.data.JdbcGroups;
import com.example.demo.security.jdbc.data.JdbcUserGroups;
import com.example.demo.security.jdbc.data.JdbcUsers;
import java.util.List;
import java.util.Map;

/**
 * @author zyw
 * @date 2020/7/15 22:55
 */
public interface JdbcAuthService {
    /**
     * store
     * @param users jdbc user
     * @return JdbcUser
     */
    JdbcUsers store(JdbcUsers users);

    /**
     * jdbc user
     * @param username username
     * @return jdbc users
     */
    JdbcUsers user(String username);

    /**
     * groups groups
     * @return list
     */
    List<JdbcGroups> groups();

    /**
     * jdbc users
     * @return list
     */
    List<JdbcUsers> users();

    /**
     * user relation
     * @param username username
     * @return map
     */
    Map<String, List<?>> userRelation(String username);

    /**
     * user group
     * @param username username
     * @return groups
     */
    List<JdbcUserGroups> userGroups(String username);

    /**
     * user groups
     * @return user groups list
     */
    List<JdbcUserGroups> userGroups();

    /**
     * user authorities
     * @param username username
     * @return authorities
     */
    List<String> userAuthorities(String username);

    /**
     * all user authorities
     * @return list
     */
    List<Map<String, List<String>>> userAuthorities();
}
