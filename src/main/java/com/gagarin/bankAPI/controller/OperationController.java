package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.service.OperationService;
import com.gagarin.bankAPI.service.StatsService;
import com.gagarin.bankAPI.service.TransferService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "operation")
public class OperationController {

    private final OperationService operationService;
    private final TransferService transferService;
    private final StatsService statsService;

    public OperationController(OperationService operationService, TransferService transferService, StatsService statsService) {
        this.operationService = operationService;
        this.transferService = transferService;
        this.statsService = statsService;
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

    @GetMapping(path = "deduct/{userId}/{takeMoney}")
    public String takeMoneyUser(@PathVariable("userId") Long userId,
                                @PathVariable("takeMoney") BigDecimal takeMoney) {
        return operationService.takeMoneyUser(userId, takeMoney);
    }

    @GetMapping(path = "transfer/{userIdFrom}/{userIdFor}/{transferAmount}")
    public String transferMoney(
            @PathVariable("userIdFrom") Long userIdForm,
            @PathVariable("userIdFor") Long userIdFor,
            @PathVariable("transferAmount") BigDecimal transferAmount) {
        return transferService.transferMoney(userIdForm, userIdFor, transferAmount);
    }

    @GetMapping(path = "getStats")
    public List<Map<String, Object>> getOperationList(
            @RequestParam(value = "userId") Long userId,

            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,

            @RequestParam(value = "beforeDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beforeDate) {

        return statsService.getOperationList(userId, fromDate, beforeDate);
    }

}
