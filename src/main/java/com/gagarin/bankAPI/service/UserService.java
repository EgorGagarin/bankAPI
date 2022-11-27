package com.gagarin.bankAPI.service;

import com.gagarin.bankAPI.UserModelAssembler;
import com.gagarin.bankAPI.UserNotFoundException;
import com.gagarin.bankAPI.controller.UsersController;
import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserModelAssembler userModelAssembler;

    public UserService(UserRepository userRepository, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.userModelAssembler = userModelAssembler;
    }

    public EntityModel<User> getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userModelAssembler.toModel(user);
    }

    public CollectionModel<EntityModel<User>> userList() {

        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UsersController.class)
                        .userList()).withSelfRel());
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

}

