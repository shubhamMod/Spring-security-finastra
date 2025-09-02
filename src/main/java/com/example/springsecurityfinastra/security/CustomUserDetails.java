package com.example.springsecurityfinastra.security;

import com.example.springsecurityfinastra.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    
   private final Employee emp;
    public CustomUserDetails(Employee employee) {
        this.emp = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + emp.getRole()));
    }


    @Override
    public String getPassword() {
        return emp.getPassword();
    }

    @Override
    public String getUsername() {
        return emp.getUserId();
    }
}
