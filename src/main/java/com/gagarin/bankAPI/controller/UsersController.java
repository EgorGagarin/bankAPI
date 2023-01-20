package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@PreAuthorize("hasRole('ADMIN')")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public CollectionModel<EntityModel<User>> userList() {
        return userService.userList();
    }

    @PostMapping("users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping(path = "users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping(path = "users/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable("userId") Long userId) {
        return userService.updateUser(newUser, userId);
    }

    @GetMapping(path = "users/{userId}")
    public EntityModel<User> getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

}
