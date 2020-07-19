package com.example.demo.security.customize;

import com.example.demo.security.customize.data.CustomizeUserRepository;
import com.example.demo.security.customize.data.CustomizeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author zyw
 * @date 2020/7/19 10:42
 */
@Service
@Qualifier("customize")
public class CustomizeUserDetailsServiceImpl implements UserDetailsService {

    private final CustomizeUserRepository customizeUserRepository;

    @Autowired
    public CustomizeUserDetailsServiceImpl(CustomizeUserRepository customizeUserRepository) {
        this.customizeUserRepository = customizeUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomizeUser user = customizeUserRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(String.format("username: %s", username));
    }
}
