package com.gagarin.bankAPI.service;

public class UserNotFoundException extends RuntimeException{

    UserNotFoundException(Long userId){
        super ("Could not find user " + userId);
    }

}
