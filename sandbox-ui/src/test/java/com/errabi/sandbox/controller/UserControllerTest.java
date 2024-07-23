package com.errabi.sandbox.controller;

import com.errabi.sandbox.services.MailService;
import com.errabi.sandbox.web.model.AuthDto;
import com.errabi.sandbox.web.model.UserDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin", password = "admin")
class UserControllerTest extends BaseControllerIT{
    @MockBean
    private MailService mailService ;
    @Test
    @Order(1)
    void CreateUserOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/users")
                        .content(asJsonString(UserDto.builder()
                                .username("e2rabi")
                                .firstName("Amine")
                                .lastName("errabi")
                                .email("amine@errabi.com")
                                .password("123456")
                                .phone("06131")
                                .address("casa")
                                .accountNonExpired(true)
                                .accountNonLocked(true)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void UserLoginOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/login")
                        .content(asJsonString(AuthDto.builder()
                                .username("e2rabi")
                                .password("123456")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindUserByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/users/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void FindAllUserOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(5)
    void UpdateUserOkTest() throws Exception {
        mockMvc.perform(put("/sandbox-api/v1/users")
                        .content(asJsonString(UserDto.builder()
                                .id(1L)
                                .username("update")
                                .firstName("test")
                                .lastName("test")
                                .email("ayoub@test.com")
                                .password("654321")
                                .phone("06131")
                                .address("essa")
                                .accountNonExpired(true)
                                .accountNonLocked(false)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(6)
    void DeleteUserOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/users/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
