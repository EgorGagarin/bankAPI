package com.gagarin.bankAPI.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @BeforeEach
    public void beforeAll() {
        System.out.println(System.currentTimeMillis());
    }

    @AfterEach
    public void afterEach() {
        System.out.println(System.currentTimeMillis());
    }

    @Autowired
    private UsersController usersController;

    @Autowired
    private OperationsController operationsController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(usersController).isNotNull();
        assertThat(operationsController).isNotNull();
    }
}