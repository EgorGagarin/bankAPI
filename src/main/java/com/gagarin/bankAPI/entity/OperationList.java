package com.gagarin.bankAPI.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "operation_list")
public class OperationList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String operation;
    private BigDecimal money;
    private LocalDate dateOperation;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public OperationList() {

    }

    public OperationList(String operation, BigDecimal money, LocalDate dateOperation, User user) {
        this.operation = operation;
        this.money = money;
        this.dateOperation = dateOperation;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
