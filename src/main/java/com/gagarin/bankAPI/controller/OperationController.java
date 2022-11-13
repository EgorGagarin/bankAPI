package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.service.OperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
