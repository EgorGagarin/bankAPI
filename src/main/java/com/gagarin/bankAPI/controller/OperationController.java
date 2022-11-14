package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.service.OperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "operation")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping(path = "balance/{userId}")
    public String getBalanceUser(@PathVariable("userId") Long userId) {
        return operationService.getBalanceUser(userId);
    }

    @GetMapping(path = "refill/{userId}/{putMoney}")
    public String putMoneyUser(@PathVariable("userId") Long userId,
                               @PathVariable("putMoney") BigDecimal putMoney) {
        return operationService.putMoneyUser(userId, putMoney);
    }

}
