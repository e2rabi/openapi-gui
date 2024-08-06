package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.WorkspaceDto;
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
class WorkspaceControllerTest extends BaseControllerIT{
    @Test
    @Order(1)
    void CreateWorkspaceOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/workspace")
                        .content(asJsonString(WorkspaceDto.builder()
                                .name("e2rabi")
                                .description("Amine")
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
    void FindWorkspaceByIdOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/workspace/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void FindAllWorkspacesOkTest() throws Exception {
        mockMvc.perform(get("/sandbox-api/v1/workspaces"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void UpdateWorkspaceOkTest() throws Exception {
        mockMvc.perform(put("/sandbox-api/v1/workspace")
                        .content(asJsonString(WorkspaceDto.builder()
                                .id(26L)
                                .name("test")
                                .description("test")
                                .enabled(false)
                                .visibility(true)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(5)
    void DeleteWorkspaceOkTest() throws Exception {
        mockMvc.perform(delete("/sandbox-api/v1/workspace/26"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
