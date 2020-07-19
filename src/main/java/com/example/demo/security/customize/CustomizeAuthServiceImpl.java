package com.example.demo.security.customize;

import com.example.demo.security.customize.data.CustomizeUserRepository;
import com.example.demo.security.customize.data.CustomizeUser;
import com.example.demo.security.customize.data.CustomizeAuthority;
import com.example.demo.security.customize.data.CustomizeAuthorityRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author zyw
 * @date 2020/7/19 15:59
 */
@Service
@Log
public class CustomizeAuthServiceImpl implements CustomizeAuthService {

    private final CustomizeUserRepository customizeUserRepository;

    private final CustomizeAuthorityRepository customizeAuthorityRepository;

    @Autowired
    public CustomizeAuthServiceImpl(CustomizeUserRepository customizeUserRepository, CustomizeAuthorityRepository customizeAuthorityRepository) {
        this.customizeUserRepository = customizeUserRepository;
        this.customizeAuthorityRepository = customizeAuthorityRepository;
    }

    @Override
    @Transactional(
            isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class}
    )
    public CustomizeUser store(CustomizeUser user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        CustomizeUser dbUser = customizeUserRepository.save(user);
        final String[] authorities = {"ROLE_CUSTOMIZE_USER", "ROLE_CUSTOMIZE_ADMIN", "ROLE_CUSTOMIZE_MANAGER"};
        final int index = new Random().nextInt(authorities.length);
        CustomizeAuthority authority = new CustomizeAuthority();
        authority.setUserId(dbUser.getId());
        authority.setAuthority(authorities[index]);
        customizeAuthorityRepository.save(authority);
        return dbUser;
    }

    @Override
    public CustomizeUser getOneByUsername(String username) {
        CustomizeUser user = customizeUserRepository.findByUsername(username);
        List<CustomizeAuthority> authorityList = customizeAuthorityRepository.findByUserId(user.getId());
        user.setCustomizeUserAuthorities(authorityList);
        return user;
    }

    @Override
    public List<CustomizeAuthority> userAuthorities(String username) {
        return getOneByUsername(username).getCustomizeUserAuthorities();
    }

    @Override
    public Map<String, List<CustomizeAuthority>> userAuthorities() {
        return null;
    }

    @Override
    public List<CustomizeUser> users() {
        List<CustomizeUser> users = customizeUserRepository.findAll();
        users.forEach(customizeUser ->
                customizeUser.setCustomizeUserAuthorities(customizeAuthorityRepository.findByUserId(customizeUser.getId())));
        return users;
    }

    @Override
    @Transactional(
            isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class}
    )
    public void flush() {
        customizeUserRepository.deleteAll();
        customizeAuthorityRepository.deleteAll();
    }
}
