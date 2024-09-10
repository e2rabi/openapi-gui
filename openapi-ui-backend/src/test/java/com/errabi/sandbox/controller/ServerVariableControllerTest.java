package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.ServerModel;
import com.errabi.sandbox.web.model.ServerVariableModel;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "admin", password = "admin") // Mock user for authentication
public class ServerVariableControllerTest extends BaseControllerIT{

    @Test
    void CreateServerVariablesOkTest() throws Exception {
        mockMvc.perform(post("/openapi/v1/server-variables")
                        .content(asJsonString(ServerVariableModel.builder()
                                .name("test")
                                .description("test")
                                .value("")
                                .defaultValue("")
                                .server(ServerModel.builder()
                                        .url("www")
                                        .description("test")
                                        .build())
                                .build()))

                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}
