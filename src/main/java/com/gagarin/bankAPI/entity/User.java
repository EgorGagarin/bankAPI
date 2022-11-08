package com.gagarin.bankAPI.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_balance")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private BigDecimal balance;

    public User() {
    }

    public User(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
