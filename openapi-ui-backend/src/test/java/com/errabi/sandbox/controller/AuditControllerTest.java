package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.AuditDto;
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
class AuditControllerTest extends BaseControllerIT {
    @Test
    @Order(1)
    void CreateAuditOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/audits")
                        .content(asJsonString(AuditDto.builder()
                                .userName("e2rabi")
                                .data("test")
                                .component("test")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void FindAuditByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/audits/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindAllAuditsOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/audits"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void UpdateAuditOkTest() throws Exception {
        mockMvc.perform(put("/sandbox-api/v1/audits")
                        .content(asJsonString(AuditDto.builder()
                                .id(1L)
                                .userName("amine")
                                .data("test2")
                                .component("test2")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(5)
    void DeleteAuditOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/audits/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
