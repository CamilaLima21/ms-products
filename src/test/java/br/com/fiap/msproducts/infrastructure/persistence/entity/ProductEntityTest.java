package br.com.fiap.msproducts.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    void shouldCreateProductEntityUsingAllArgsConstructor() {
        Long id = 1L;
        String name = "Test Product";
        String sku = "SKU123";
        Double price = 49.99;

        ProductEntity entity = new ProductEntity(id, name, sku, price);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(sku, entity.getSku());
        assertEquals(price, entity.getPrice());
    }

    @Test
    void shouldSetAndGetFieldsIndividually() {
        ProductEntity entity = new ProductEntity();
        entity.setId(2L);
        entity.setName("Mouse Gamer");
        entity.setSku("SKU456");
        entity.setPrice(199.90);

        assertEquals(2L, entity.getId());
        assertEquals("Mouse Gamer", entity.getName());
        assertEquals("SKU456", entity.getSku());
        assertEquals(199.90, entity.getPrice());
    }

    @Test
    void shouldHandleNullValues() {
        ProductEntity entity = new ProductEntity();
        entity.setId(null);
        entity.setName(null);
        entity.setSku(null);
        entity.setPrice(null);

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getSku());
        assertNull(entity.getPrice());
    }
}
