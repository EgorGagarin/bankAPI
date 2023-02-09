package com.gagarin.bankAPI.service;

import com.gagarin.bankAPI.entity.OperationList;
import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.repository.OperationListRepository;
import com.gagarin.bankAPI.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransferService {

    private final UserRepository userRepository;
    private final OperationListRepository operationListRepository;

    public TransferService(UserRepository userRepository, OperationListRepository operationListRepository) {
        this.userRepository = userRepository;
        this.operationListRepository = operationListRepository;
    }

    @Transactional
    public ResponseEntity<?> transferMoney(Long userIdFrom, Long userIdFor, BigDecimal transferAmount) {
        Optional<User> rowUserFrom = userRepository.findById(userIdFrom);
        Optional<User> rowUserFor = userRepository.findById(userIdFor);

        if (rowUserFrom.isPresent()) {
            User itemUserFrom = rowUserFrom.get();

            if (transferAmount.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal currentBalanceUserFrom = itemUserFrom.getBalance();

                if (transferAmount.compareTo(currentBalanceUserFrom) <= 0) {
                    BigDecimal newBalanceUserFrom = currentBalanceUserFrom.subtract(transferAmount);
                    itemUserFrom.setBalance(newBalanceUserFrom);
                    userRepository.save(itemUserFrom);

                    String operationFrom = "Deducted";

                    LocalDate dateFrom = LocalDate.now();

                    OperationList operationListAddUserFrom = new OperationList(operationFrom, transferAmount, dateFrom, itemUserFrom);
                    operationListRepository.save(operationListAddUserFrom);

                    if (rowUserFor.isPresent()) {
                        User itemUserFor = rowUserFor.get();
                        BigDecimal currentBalanceUserFor = itemUserFor.getBalance();
                        BigDecimal newBalanceUserFor = currentBalanceUserFor.add(transferAmount);
                        itemUserFor.setBalance(newBalanceUserFor);
                        userRepository.save(itemUserFor);

                        String operationFor = "Received";

                        LocalDate dateFor = LocalDate.now();

                        OperationList operationListAddUserFor = new OperationList(operationFor, transferAmount, dateFor, itemUserFor);
                        operationListRepository.save(operationListAddUserFor);

                    } else {
                        throw new UserNotFoundException(userIdFor);
                    }
                } else {
                    return ResponseEntity.ok("Insufficient funds on the account");
                }
            } else {
                return ResponseEntity.badRequest()
                        .body("Please enter a positive number greater than zero");
            }
        } else {
            throw new UserNotFoundException(userIdFrom);
        }
        return ResponseEntity.ok("Transferred " + transferAmount + ", from user " + userIdFrom + " to user " + userIdFor);
    }

}
