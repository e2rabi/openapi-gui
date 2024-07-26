package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.ModuleDto;
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
class ModuleControllerTest extends BaseControllerIT{
    @Test
    @Order(1)
    void CreateModuleOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/modules")
                        .content(asJsonString(ModuleDto.builder()
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
    void FindModuleByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/modules/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindAllModulesOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/modules"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void UpdateModuleOkTest() throws Exception {
        mockMvc.perform(put("/sandbox-api/v1/modules")
                        .content(asJsonString(ModuleDto.builder()
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
    void DeleteModuleOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/modules/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
