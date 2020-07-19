package com.example.demo.security.customize.data;

import com.example.demo.security.customize.CustomizeAuthService;
import com.example.demo.security.customize.CustomizeAuthServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zyw
 * @date 2020/7/18 21:15
 */
@Entity
@Data
public class CustomizeUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String enabled;

    @OneToMany(targetEntity = CustomizeAuthority.class, cascade = CascadeType.REMOVE)
    private List<CustomizeAuthority> customizeUserAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!customizeUserAuthorities.isEmpty()) {
            List<SimpleGrantedAuthority> userAuthorities = new LinkedList<>();
            customizeUserAuthorities.forEach(authority ->
                    userAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority())));
            return userAuthorities;
        }
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
