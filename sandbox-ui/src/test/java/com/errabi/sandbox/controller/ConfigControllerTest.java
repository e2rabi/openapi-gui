package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.ConfigurationDto;
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
@WithMockUser(username = "admin", password = "admin")
class ConfigControllerTest extends BaseControllerIT{
    @Test
    @Order(1)
    void CreateConfigOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/configurations")
                        .content(asJsonString(ConfigurationDto.builder()
                                .key("test")
                                .value("test")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void FindConfigByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/configurations/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindConfigByKeyOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/configurations/keys/test"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void FindAllConfigOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/configurations"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(5)
    void DeleteConfigOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/configurations/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
