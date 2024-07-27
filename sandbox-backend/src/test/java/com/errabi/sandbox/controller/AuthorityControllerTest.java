package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.AuthorityDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin", password = "admin") // Mock user for authentication
class AuthorityControllerTest extends BaseControllerIT{
    @Test
    @Order(1)
    void CreateAuthorityOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/authorities")
                        .content(asJsonString(AuthorityDto.builder()
                                .permission("write")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void FindAuthorityByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/authorities/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindAllAuthoritiesOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/authorities"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void UpdateAuthorityOkTest() throws Exception {
        mockMvc.perform(put("/sandbox-api/v1/authorities")
                        .content(asJsonString(AuthorityDto.builder()
                                .id(1L)
                                .permission("read")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(5)
    void DeleteAuthorityOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/authorities/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
