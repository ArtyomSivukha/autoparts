package com.example.autoparts.service;

import com.example.autoparts.auth.jwt.JwtUtil;
import com.example.autoparts.model.User;
import com.example.autoparts.model.enums.Role;
import com.example.autoparts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
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

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
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