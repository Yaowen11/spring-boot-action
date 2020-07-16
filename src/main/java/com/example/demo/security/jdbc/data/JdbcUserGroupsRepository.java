package com.example.demo.security.jdbc.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zyw
 * @date 2020/7/15 21:53
 */
public interface JdbcUserGroupsRepository extends JpaRepository<JdbcUserGroups, Integer> {
    /**
     * user group by username
     * @param username user name
     * @return list
     */
    List<JdbcUserGroups> findByUsername(String username);

    /**
     * exists
     * @param username username
     * @param groupId groupId
     * @return boolean
     */
    boolean existsByUsernameAndGroupId(String username, Integer groupId);
}
