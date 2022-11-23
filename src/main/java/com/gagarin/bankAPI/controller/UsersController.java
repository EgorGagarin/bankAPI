package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> userList(){
        return userService.userList();
    }

    @PostMapping
    public  void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }

    @PutMapping
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

    @GetMapping(path = "{userId}")
    public Optional<User> getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

}
