package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.SolutionDto;
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
class SolutionControllerTest extends BaseControllerIT{
    @Test
    @Order(1)
    void CreateSolutionOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/solutions")
                        .content(asJsonString(SolutionDto.builder()
                                .name("test")
                                .description("test")
                                .color("#FFFFFF")
                                .enabled(true)
                                .visibility(true)
                                .image("url")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void FindSolutionByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/solutions/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindAllSolutionsOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/solutions"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void UpdateSolutionOkTest() throws Exception {
        mockMvc.perform(put("/sandbox-api/v1/solutions")
                        .content(asJsonString(SolutionDto.builder()
                                .id(1L)
                                .name("card")
                                .description("update")
                                .color("#FFF1FF")
                                .enabled(false)
                                .visibility(false)
                                .image("url2")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(5)
    void DeleteSolutionOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/solutions/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
