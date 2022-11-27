package com.gagarin.bankAPI;

import com.gagarin.bankAPI.controller.UsersController;
import com.gagarin.bankAPI.entity.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                (linkTo(methodOn(UsersController.class)
                        .getUser(user.getId())).withSelfRel()),
                linkTo(methodOn(UsersController.class)
                        .userList()).withRel("users"));
    }

}
