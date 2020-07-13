package com.example.demo.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author z
 */
@Service
@Qualifier("admin")
@Log
public class AdminRepositoryUserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminRepositoryUserDetailsServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        log.info("admin: " + admin);
        if (admin == null) {
            throw new UsernameNotFoundException(String.format("not found admin by username %s", username));
        }
        return admin;
    }
}
