package com.example.autoparts.service;

import com.example.autoparts.advice.exception.UserNotFoundException;
import com.example.autoparts.auth.jwt.JwtUtil;
import com.example.autoparts.controller.utils.UsersUtil;
import com.example.autoparts.model.User;
import com.example.autoparts.repository.UserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    public String getTokenByCredentials(String email, String password) {
        User user = this.findByEmail(email);
        if (!user.getPassword().contentEquals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return this.jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
        return user;
    }

    public User getCurrent() {
        String customerEmail = UsersUtil.getCurrentUserEmail();
        User user = findByEmail(customerEmail);
        if (user == null) {
            throw new UserNotFoundException(-1L);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = this.findByEmail(email);
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(
                            new SimpleGrantedAuthority(user.getRole().name())
                    )
            );
        } catch (NoSuchElementException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
    }
}