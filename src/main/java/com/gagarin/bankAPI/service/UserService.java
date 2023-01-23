package com.gagarin.bankAPI.service;

import com.gagarin.bankAPI.controller.UsersController;
import com.gagarin.bankAPI.entity.ERole;
import com.gagarin.bankAPI.entity.Role;
import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.repository.RoleRepository;
import com.gagarin.bankAPI.repository.UserRepository;
import com.gagarin.bankAPI.security.pojo.MessageResponse;
import com.gagarin.bankAPI.security.pojo.SignupRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserModelAssembler userModelAssembler;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, UserModelAssembler userModelAssembler, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userModelAssembler = userModelAssembler;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public CollectionModel<EntityModel<User>> userList() {

        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UsersController.class)
                        .userList()).withSelfRel());
    }

    public ResponseEntity<?> addUser(SignupRequest signupRequest) {

            if (userRepository.existsByUsername(signupRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is exist"));
            }

            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is exist"));
            }

            User user = new User(signupRequest.getUsername(),
                    signupRequest.getEmail(),
                    passwordEncoder.encode(signupRequest.getPassword()));

            Set<String> reqRoles = signupRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            if (reqRoles == null) {
                Role userRole = roleRepository
                        .findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                roles.add(userRole);
            } else {
                reqRoles.forEach(r -> {
                    switch (r) {
                        case "admin":
                            Role adminRole = roleRepository
                                    .findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                            roles.add(adminRole);
                            break;

                        case "mod":
                            Role modRole = roleRepository
                                    .findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                            roles.add(modRole);
                            break;

                        default:
                            Role userRole = roleRepository
                                    .findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                            roles.add(userRole);
                    }
                });
            }
            user.setRoles(roles);

        EntityModel<User> entityModel = userModelAssembler.toModel(userRepository.save(user));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        userRepository.deleteById(userId);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateUser(User newUser, Long userId) {
        User updatedUser = userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setBalance(newUser.getBalance());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(userId));

        EntityModel<User> entityModel = userModelAssembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public EntityModel<User> getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userModelAssembler.toModel(user);
    }

}

