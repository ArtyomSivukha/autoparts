package com.example.autoparts.controller;

import com.example.autoparts.controller.contract.LoginRequest;
import com.example.autoparts.controller.contract.LoginResponse;
import com.example.autoparts.controller.utils.UsersUtil;
import com.example.autoparts.model.Profile;
import com.example.autoparts.model.User;
import com.example.autoparts.model.enums.Role;
import com.example.autoparts.service.ProfileService;
import com.example.autoparts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public AuthController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    //    private final CustomerService customerService;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = this.userService.getTokenByCredentials(loginRequest.getEmail(), loginRequest.getPassword());
        System.out.println("Generated Token: " + token);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        user.setRole(Role.CLIENT);


        // Сохранение пользователя
        User savedUser = userService.saveUser(user);

        // Создание профиля для нового пользователя
        profileService.createProfileForUser(savedUser);

//        userService.saveUser(user);
        return login(new LoginRequest(user.getEmail(),user.getPassword()));
    }

    @GetMapping("protected/user-info")
    public ResponseEntity<?> getUserInfo() {
        String customerEmail = UsersUtil.getCurrentUserEmail();
        User user = userService.findByEmail(customerEmail);
//        boolean isAdmin = UsersUtil.getCurrentUserRole() == Role.ADMIN;

        profileService.ensureProfileForUser(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PatchMapping("protected/user-info")
//    public void updateUserInfo(@RequestBody Customer customer) {
//        String customerEmail = UsersUtil.getCurrentUserEmail();
//        customer.setEmail(customerEmail);
//
//        customerService.updateCustomer(customer);
//    }
//
//    @PatchMapping("protected/password")
//    public void changePassword(@RequestBody ChangePasswordRequest request) {
//        String email = UsersUtil.getCurrentUserEmail();
//        userService.updatePassword(email, request.getCurrentPassword(), request.getNewPassword());
//    }
}
