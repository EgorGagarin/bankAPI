package com.gagarin.bankAPI.service;

import com.gagarin.bankAPI.controller.UsersController;
import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> addUser(User user) {
        EntityModel<User> entityModel = userModelAssembler.toModel(userRepository.save(user));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public ResponseEntity<?> updateUser(User user) {
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

        EntityModel<User> entityModel = userModelAssembler.toModel(user);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

}

