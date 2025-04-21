package br.com.fiap.msproducts.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.fiap.msproducts.infrastructure.persistence.entity.ProductEntity;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    @DisplayName("Deve salvar e buscar um produto por ID")
    void shouldSaveAndFindById() {
        ProductEntity entity = new ProductEntity(null, "Product Test", "SKU123", 99.99);
        ProductEntity saved = repository.save(entity);

        Optional<ProductEntity> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Product Test", found.get().getName());
        assertEquals("SKU123", found.get().getSku());
    }

    @Test
    @DisplayName("Deve buscar um produto por SKU")
    void shouldFindBySku() {
        ProductEntity entity = new ProductEntity(null, "Product Test", "SKU456", 199.90);
        repository.save(entity);

        Optional<ProductEntity> found = repository.findBySku("SKU456");

        assertTrue(found.isPresent());
        assertEquals("Product Test", found.get().getName());
    }

    @Test
    @DisplayName("Deve retornar true se SKU existir")
    void shouldReturnTrueIfSkuExists() {
        ProductEntity entity = new ProductEntity(null, "Existing Product", "SKU789", 59.99);
        repository.save(entity);

        assertTrue(repository.existsBySku("SKU789"));
    }

    @Test
    @DisplayName("Deve retornar false se SKU não existir")
    void shouldReturnFalseIfSkuDoesNotExist() {
        assertFalse(repository.existsBySku("SKU000"));
    }
}
