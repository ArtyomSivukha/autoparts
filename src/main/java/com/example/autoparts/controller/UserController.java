package com.example.autoparts.controller;

import com.example.autoparts.model.User;
import com.example.autoparts.service.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Data
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrent() {
        return new ResponseEntity<>(userService.getCurrent(), HttpStatus.OK);
    }

    @PatchMapping("/admin/update-role/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')") // Только админы
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestParam String role) {
        // Проверяем, что администратор не пытается изменить свою роль
        User currentUser = userService.getCurrent();
        if (currentUser.getId().equals(userId)) {
            return ResponseEntity.badRequest().body("Admin cannot change their own role");
        }

        try {
            userService.updateUserRole(userId, role);
            return ResponseEntity.ok("Role updated successfully for user ID " + userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}