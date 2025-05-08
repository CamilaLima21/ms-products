package br.com.fiap.msproducts.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    void shouldCreateProductEntityUsingAllArgsConstructor() {
        Long id = 1L;
        String name = "Test Product";
        String sku = "SKU123";
        BigDecimal price = BigDecimal.valueOf(49.99); // CORRIGIDO

        ProductEntity entity = new ProductEntity(id, name, sku, price);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(sku, entity.getProductSku());
        assertEquals(price, entity.getPrice());
    }

    @Test
    void shouldSetAndGetFieldsIndividually() {
        ProductEntity entity = new ProductEntity();
        entity.setId(2L);
        entity.setName("Mouse Gamer");
        entity.setProductSku("SKU456");
        BigDecimal price = BigDecimal.valueOf(199.90); // CORRIGIDO
        entity.setPrice(price);

        assertEquals(2L, entity.getId());
        assertEquals("Mouse Gamer", entity.getName());
        assertEquals("SKU456", entity.getProductSku());
        assertEquals(price, entity.getPrice());
    }

    @Test
    void shouldHandleNullValues() {
        ProductEntity entity = new ProductEntity();
        entity.setId(null);
        entity.setName(null);
        entity.setProductSku(null);
        entity.setPrice(null);

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getProductSku());
        assertNull(entity.getPrice());
    }
}
