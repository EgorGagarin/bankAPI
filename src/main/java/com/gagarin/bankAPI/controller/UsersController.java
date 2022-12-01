package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public CollectionModel<EntityModel<User>> userList() {
        return userService.userList();
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable("userId") Long userId) {
        return userService.updateUser(newUser, userId);
    }

    @GetMapping(path = "{userId}")
    public EntityModel<User> getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

}
