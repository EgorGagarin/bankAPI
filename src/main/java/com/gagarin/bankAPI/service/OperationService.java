package com.gagarin.bankAPI.service;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class OperationService {

    private final UserRepository userRepository;

    public OperationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getBalanceUser(Long userId) {
        Optional<User> row = userRepository.findById(userId);

        if (row.isPresent()) {
            User user = row.get();
            BigDecimal userBalance = user.getBalance();
            return "Balance: " + userBalance;
        } else {
            return "User with this id does not exist";
        }
    }

}
