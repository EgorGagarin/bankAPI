package com.gagarin.bankAPI.controller;

import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void userList() throws Exception {
        this.mockMvc.perform(get("/users/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getUser() throws Exception {
        User user = new User(1L, "Aleks", BigDecimal.valueOf(1000));
        when(userService.getUser(1l))
                .thenReturn(EntityModel.of(user));
        this.mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1, \"username\":\"Aleks\", \"balance\":1000}"));

    }
}