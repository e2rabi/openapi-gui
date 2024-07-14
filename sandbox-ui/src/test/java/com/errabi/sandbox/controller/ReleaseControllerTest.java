package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.ReleaseDto;
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
class ReleaseControllerTest extends BaseControllerIT{
    @Test
    @Order(1)
    void CreateReleaseOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/releases")
                        .content(asJsonString(ReleaseDto.builder()
                                .name("test")
                                .description("test")
                                .color("#FFFFFF")
                                .enabled(true)
                                .visibility(true)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void FindReleaseByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/releases/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindAllReleasesOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/releases"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void UpdateReleaseOkTest() throws Exception {
        mockMvc.perform(put("/sandbox-api/v1/releases")
                        .content(asJsonString(ReleaseDto.builder()
                                .id(1L)
                                .name("card")
                                .description("update")
                                .color("#FFF1FF")
                                .enabled(false)
                                .visibility(false)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(5)
    void DeleteReleaseOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/releases/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
