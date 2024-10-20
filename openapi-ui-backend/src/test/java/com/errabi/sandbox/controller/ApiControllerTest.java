package com.errabi.sandbox.controller;

import com.errabi.openapi.web.model.ApiDto;
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
class ApiControllerTest extends BaseControllerIT{
    @Test
    @Order(1)
    void CreateApiOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/api")
                        .content(asJsonString(ApiDto.builder()
                                .name("test")
                                .description("test")
                                .url("www")
                                .enabled(true)
                                .visibility(true)
                                .httpVerb("http")
                                .openApiSchema("openapi")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void FindApiByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/api/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindAllApiOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/api"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void UpdateApiOkTest() throws Exception {
        mockMvc.perform(put("/sandbox-api/v1/api")
                        .content(asJsonString(ApiDto.builder()
                                .id(26L)
                                .name("card")
                                .description("update")
                                .url("test")
                                .enabled(false)
                                .visibility(false)
                                .httpVerb("verb")
                                .openApiSchema("test")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(5)
    void DeleteApiOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/api/26"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
