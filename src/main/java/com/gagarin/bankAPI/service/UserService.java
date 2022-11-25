package com.gagarin.bankAPI.service;

import com.gagarin.bankAPI.UserNotFoundException;
import com.gagarin.bankAPI.controller.UsersController;
import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> userList() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void updateUser(User user) {
        Optional<User> row = userRepository.findById(user.getId());
        if (row.isPresent()) {
            User item = row.get();
            if (!user.getUsername().isEmpty()) {
                item.setUsername(user.getUsername());
            }
            if (user.getBalance() != null) {
                item.setBalance(user.getBalance());
            }
            userRepository.save(item);
        }
    }

    public EntityModel<User> getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return EntityModel.of(user,
                linkTo(methodOn(UsersController.class).getUser(userId)).withSelfRel(),
                linkTo(methodOn(UsersController.class).userList()).withRel("users"));
    }

}

