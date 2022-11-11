package com.gagarin.bankAPI.service;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> userList() {
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

}
