package com.example.autoparts.auth;

import com.example.autoparts.auth.jwt.JwtFilter;
import com.example.autoparts.model.enums.Role;
import com.example.autoparts.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(x -> x.disable())
                .httpBasic(x -> x.disable())
                .cors(x -> x.disable())
                .authorizeHttpRequests(x -> {
//                    x.requestMatchers(new AdminRequestMatcher()).hasAuthority(Role.ADMIN.name());
//                    x.requestMatchers(new CustomerRequestMatcher()).hasAnyAuthority(Role.ADMIN.name(), Role.CLIENT.name(), Role.SUPPLIER.name());
                    x.requestMatchers("/**").permitAll();
                })
                .userDetailsService(this.userService)
                .exceptionHandling(x ->
                    x.authenticationEntryPoint((req, res, exception) ->
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    )
                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
