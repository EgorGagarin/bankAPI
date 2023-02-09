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
public class OperationService {

    private final UserRepository userRepository;
    private final OperationListRepository operationListRepository;

    public OperationService(UserRepository userRepository, OperationListRepository operationListRepository) {
        this.userRepository = userRepository;
        this.operationListRepository = operationListRepository;
    }

    public ResponseEntity<?> getBalanceUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        BigDecimal userBalance = user.getBalance();

        return ResponseEntity.ok("Balance: " + userBalance);
    }

    @Transactional
    public ResponseEntity<?> putMoneyUser(Long userId, BigDecimal putMoney) {
        Optional<User> row = userRepository.findById(userId);

        if (row.isPresent()) {
            User item = row.get();
            if (putMoney.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal currentBalance = item.getBalance();
                BigDecimal newBalance = currentBalance.add(putMoney);
                item.setBalance(newBalance);
                userRepository.save(item);

                String operation = "Contributed";

                LocalDate date = LocalDate.now();

                OperationList operationListAdd = new OperationList(operation, putMoney, date, item);
                operationListRepository.save(operationListAdd);

            } else {
                return ResponseEntity.badRequest()
                        .body("Please enter a positive number greater than zero");
            }
        } else {
            throw new UserNotFoundException(userId);
        }
        return ResponseEntity.ok("Added amount: " + putMoney);
    }

    @Transactional
    public String takeMoneyUser(Long userId, BigDecimal takeMoney) {
        Optional<User> row = userRepository.findById(userId);

        if (row.isPresent()) {
            User item = row.get();
            if (takeMoney.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal currentBalance = item.getBalance();
                if (takeMoney.compareTo(currentBalance) <= 0) {
                    BigDecimal newBalance = currentBalance.subtract(takeMoney);
                    item.setBalance(newBalance);
                    userRepository.save(item);

                    String operation = "Deducted";

                    LocalDate date = LocalDate.now();

                    OperationList operationListAdd = new OperationList(operation, takeMoney, date, item);
                    operationListRepository.save(operationListAdd);
                } else {
                    return "Insufficient funds on the account";
                }
            } else {
                return "Please enter a positive number greater than zero";
            }
        } else {
            throw new UserNotFoundException(userId);
        }
        return "Deducted: " + takeMoney;
    }

}
