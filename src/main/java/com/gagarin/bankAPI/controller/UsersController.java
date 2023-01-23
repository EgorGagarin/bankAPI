package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.security.pojo.SignupRequest;
import com.gagarin.bankAPI.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    @PreAuthorize("hasRole('ADMIN')")
    public CollectionModel<EntityModel<User>> userList() {
        return userService.userList();
    }

    @PostMapping("users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody SignupRequest signupRequest) {
        return userService.addUser(signupRequest);
    }

    @DeleteMapping(path = "users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping(path = "users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable("userId") Long userId) {
        return userService.updateUser(newUser, userId);
    }

    @GetMapping(path = "users/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.getId")
    public EntityModel<User> getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

}
