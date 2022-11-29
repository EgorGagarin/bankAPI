package com.gagarin.bankAPI.service;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long userId){
        super ("Could not find user " + userId);
    }

}
