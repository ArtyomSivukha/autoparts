package com.example.autoparts.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class AdminRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        return request.getRequestURI().contains("/admin/protected/");
    }
}
