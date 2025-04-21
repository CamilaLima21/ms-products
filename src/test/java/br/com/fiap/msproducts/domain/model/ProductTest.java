package br.com.fiap.msproducts.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void shouldCreateProductCorrectly() {
        long id = 1L;
        String name = "Product Test";
        String sku = "SKU123";
        Double price = 99.99;

        Product product = new Product(id, name, sku, price);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(sku, product.getSku());
        assertEquals(price, product.getPrice());
    }

    @Test
    void shouldAllowZeroId() {
        Product product = new Product(0L, "Product Zero", "SKU000", 10.0);

        assertEquals(0L, product.getId());
        assertEquals("Product Zero", product.getName());
        assertEquals("SKU000", product.getSku());
        assertEquals(10.0, product.getPrice());
    }

    @Test
    void shouldAllowNullPrice() {
        Product product = new Product(2L, "Product Null Price", "SKU456", null);

        assertEquals(2L, product.getId());
        assertEquals("Product Null Price", product.getName());
        assertEquals("SKU456", product.getSku());
        assertNull(product.getPrice());
    }
}
