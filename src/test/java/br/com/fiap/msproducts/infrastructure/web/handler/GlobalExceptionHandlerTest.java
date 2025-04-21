package br.com.fiap.msproducts.infrastructure.web.handler;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.msproducts.application.service.ProductService;
import br.com.fiap.msproducts.domain.exception.ProductNotFoundException;
import br.com.fiap.msproducts.infrastructure.web.controller.ProductController;

@WebMvcTest(controllers = ProductController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 404 quando ProductNotFoundException for lançada")
    void shouldReturnNotFoundWhenProductNotFoundExceptionIsThrown() throws Exception {
        when(service.findById(999L)).thenThrow(new ProductNotFoundException("Product not found."));

        mockMvc.perform(get("/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("Product not found.")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando IllegalArgumentException for lançada")
    void shouldReturnBadRequestWhenIllegalArgumentExceptionIsThrown() throws Exception {
        when(service.findById(-1L)).thenThrow(new IllegalArgumentException("Invalid ID."));

        mockMvc.perform(get("/products/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", is("Invalid ID.")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Deve retornar 500 quando uma exceção genérica for lançada")
    void shouldReturnInternalServerErrorForGenericException() throws Exception {
        when(service.findById(1L)).thenThrow(new RuntimeException("Unexpected internal error."));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.error", is("Internal Server Error")))
                .andExpect(jsonPath("$.message", is("Internal server error")))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
