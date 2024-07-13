package com.errabi.sandbox.controller;

import com.errabi.sandbox.web.model.ProductDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest extends BaseControllerIT{
    @Test
    @Order(1)
    void CreateProductOkTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/products")
                        .content(asJsonString(ProductDto.builder()
                                                        .name("test")
                                                        .description("test")
                                                        .color("#FFFFFF")
                                                        .enabled("true")
                                                        .visibility(true)
                                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void CreateProductKOTest() throws Exception {
        mockMvc.perform(post("/sandbox-api/v1/products")
                        .content(asJsonString(ProductDto.builder()
                                .name("")
                                .description("test")
                                .color("#FFFFFF")
                                .enabled("true")
                                .visibility(true)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
