package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
