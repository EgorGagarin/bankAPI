package com.gagarin.bankAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagarin.bankAPI.entity.User;
import com.gagarin.bankAPI.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        List<EntityModel<User>> users = List.of(
                EntityModel.of(new User(1L, "Aleks", BigDecimal.valueOf(1000))),
                EntityModel.of(new User(2L, "Ben", BigDecimal.valueOf(2000))),
                EntityModel.of(new User(3L, "Sveta", BigDecimal.valueOf(3000))),
                EntityModel.of(new User(4L, "Kola", BigDecimal.valueOf(4000))),
                EntityModel.of(new User(5L, "Oly", BigDecimal.valueOf(5000)))
        );
        when(userService.userList())
                .thenReturn(CollectionModel.of(users));
        this.mockMvc.perform(get("/users")
                .with(user("user").password("password").roles("USER"))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"_embedded":
                        {"userList":[
                        {
                        "id":1,
                        "username":"Aleks",
                        "balance":1000
                        },
                        {
                        "id":2,
                        "username":"Ben",
                        "balance":2000
                        },
                        {
                        "id":3,
                        "username":"Sveta",
                        "balance":3000
                        },
                        {
                        "id":4,
                        "username":"Kola",
                        "balance":4000
                        },
                        {
                        "id":5,
                        "username":"Oly",
                        "balance":5000
                        }]}}
                        """));
    }

    @Test
    @WithMockUser
    void addUser() throws Exception {
        User user = new User(1L, "Aleks", BigDecimal.valueOf(1000));

        when(userService.addUser(user))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        this.mockMvc.perform(
                post("/users")
                        .with(csrf())
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteUser() throws Exception {

        when(userService.deleteUser(1L))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        this.mockMvc
                .perform(delete("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateUser() throws Exception{
        User newUser = new User("Ivan", BigDecimal.valueOf(10000));

        when(userService.updateUser(newUser, 1L))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        this.mockMvc
                .perform(put("/users/1")
                        .content(new ObjectMapper().writeValueAsString(newUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser
    void getUser() throws Exception {
        User user = new User(1L, "Aleks", BigDecimal.valueOf(1000));

        when(userService.getUser(1L))
                .thenReturn(EntityModel.of(user));
        this.mockMvc
                .perform(get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1, \"username\":\"Aleks\", \"balance\":1000}"));
    }

}