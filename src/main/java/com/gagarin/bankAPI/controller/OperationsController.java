package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.service.OperationService;
import com.gagarin.bankAPI.service.StatsService;
import com.gagarin.bankAPI.service.TransferService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/operation")
public class OperationsController {

    private final OperationService operationService;
    private final TransferService transferService;
    private final StatsService statsService;

    public OperationsController(OperationService operationService, TransferService transferService, StatsService statsService) {
        this.operationService = operationService;
        this.transferService = transferService;
        this.statsService = statsService;
    }

    @GetMapping(path = "balance/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.getId")
    public String getBalanceUser(@PathVariable("userId") Long userId) {
        return operationService.getBalanceUser(userId);
    }

    @GetMapping(path = "refill/{userId}/{putMoney}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.getId")
    public String putMoneyUser(@PathVariable("userId") Long userId,
                               @PathVariable("putMoney") BigDecimal putMoney) {
        return operationService.putMoneyUser(userId, putMoney);
    }

    @GetMapping(path = "deduct/{userId}/{takeMoney}")
    @PreAuthorize("hasRole('ADMIN')")
    public String takeMoneyUser(@PathVariable("userId") Long userId,
                                @PathVariable("takeMoney") BigDecimal takeMoney) {
        return operationService.takeMoneyUser(userId, takeMoney);
    }

    @GetMapping(path = "transfer/{userIdFrom}/{userIdFor}/{transferAmount}")
    @PreAuthorize("hasRole('ADMIN') or #userIdForm == authentication.principal.getId")
    public String transferMoney(
            @PathVariable("userIdFrom") Long userIdForm,
            @PathVariable("userIdFor") Long userIdFor,
            @PathVariable("transferAmount") BigDecimal transferAmount) {
        return transferService.transferMoney(userIdForm, userIdFor, transferAmount);
    }

    @GetMapping(path = "getStats")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.getId")
    public List<Map<String, Object>> getOperationList(
            @RequestParam(value = "userId") Long userId,

            @RequestParam(value = "fromDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,

            @RequestParam(value = "beforeDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beforeDate) {

        return statsService.getOperationList(userId, fromDate, beforeDate);
    }

}
