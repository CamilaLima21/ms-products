package br.com.fiap.msproducts.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void shouldCreateProductCorrectly() {
        long id = 1L;
        String name = "Product Test";
        String sku = "SKU123";
        BigDecimal price = BigDecimal.valueOf(99.99); // CORRIGIDO

        Product product = new Product(id, name, sku, price);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(sku, product.getProductSku());
        assertEquals(price, product.getPrice());
    }

    @Test
    void shouldAllowZeroId() {
        BigDecimal price = BigDecimal.valueOf(10.0); // CORRIGIDO
        Product product = new Product(0L, "Product Zero", "SKU000", price);

        assertEquals(0L, product.getId());
        assertEquals("Product Zero", product.getName());
        assertEquals("SKU000", product.getProductSku());
        assertEquals(price, product.getPrice());
    }

    @Test
    void shouldAllowNullPrice() {
        Product product = new Product(2L, "Product Null Price", "SKU456", null);

        assertEquals(2L, product.getId());
        assertEquals("Product Null Price", product.getName());
        assertEquals("SKU456", product.getProductSku());
        assertNull(product.getPrice());
    }
}
