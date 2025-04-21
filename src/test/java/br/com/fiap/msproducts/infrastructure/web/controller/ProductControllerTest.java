package br.com.fiap.msproducts.infrastructure.web.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.msproducts.application.dto.ProductDto;
import br.com.fiap.msproducts.application.service.ProductService;
import br.com.fiap.msproducts.domain.exception.ProductNotFoundException;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /products deve retornar todos os produtos")
    void shouldReturnAllProducts() throws Exception {
        ProductDto dto = new ProductDto(1L, "Product A", "SKU123", 99.90);
        when(service.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Product A")))
                .andExpect(jsonPath("$[0].sku", is("SKU123")))
                .andExpect(jsonPath("$[0].price", is(99.90)));
    }

    @Test
    @DisplayName("GET /products/{id} deve retornar produto por ID")
    void shouldReturnProductById() throws Exception {
        ProductDto dto = new ProductDto(1L, "Product A", "SKU123", 99.90);
        when(service.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product A")));
    }

    @Test
    @DisplayName("GET /products/sku/{sku} deve retornar produto por SKU")
    void shouldReturnProductBySku() throws Exception {
        ProductDto dto = new ProductDto(1L, "Product A", "SKU123", 99.90);
        when(service.findBySku("SKU123")).thenReturn(dto);

        mockMvc.perform(get("/products/sku/SKU123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku", is("SKU123")));
    }

    @Test
    @DisplayName("POST /products deve criar um produto")
    void shouldCreateProduct() throws Exception {
        ProductDto dto = new ProductDto(0L, "New Product", "SKU999", 49.90);
        ProductDto created = new ProductDto(1L, "New Product", "SKU999", 49.90);
        when(service.create(any())).thenReturn(created);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.sku", is("SKU999")));
    }

    @Test
    @DisplayName("PUT /products/{id} deve atualizar um produto")
    void shouldUpdateProduct() throws Exception {
        ProductDto dto = new ProductDto(1L, "Updated Product", "SKU123", 79.90);
        when(service.update(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Product")));
    }

    @Test
    @DisplayName("DELETE /products/{id} deve excluir um produto")
    void shouldDeleteProduct() throws Exception {
        when(service.delete(1L)).thenReturn("Product deleted successfully!");

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully!"));
    }

    @Test
    @DisplayName("GET /products/{id} deve retornar 404 se produto não encontrado")
    void shouldReturn404WhenProductNotFound() throws Exception {
        when(service.findById(99L)).thenThrow(new ProductNotFoundException("Product not found."));

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Product not found.")));
    }
}
