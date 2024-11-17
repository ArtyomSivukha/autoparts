package com.example.autoparts.controller.utils;


import com.example.autoparts.model.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UsersUtil {
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String)authentication.getPrincipal();
    }

    public static Role getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleGrantedAuthority authority = (SimpleGrantedAuthority) authentication.getAuthorities().stream().toArray()[0];
        return Role.valueOf(authority.getAuthority());
    }
}
