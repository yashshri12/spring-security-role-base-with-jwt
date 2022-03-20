package com.api.security.service;

import com.api.security.entities.Role;
import com.api.security.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user){
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roleSet =user.getRole();
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList =new ArrayList<>();

        for(Role role: roleSet){
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return simpleGrantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
