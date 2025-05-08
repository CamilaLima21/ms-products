package br.com.fiap.msproducts.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import br.com.fiap.msproducts.infrastructure.persistence.entity.ProductEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    @DisplayName("Deve buscar um produto por SKU")
    void shouldFindBySku() {
        ProductEntity entity = new ProductEntity(null, "Product Test", "SKU456", BigDecimal.valueOf(199.90));
        repository.save(entity);

        Optional<ProductEntity> found = repository.findByProductSku("SKU456");

        assertTrue(found.isPresent());
        assertEquals("Product Test", found.get().getName());
    }

    @Test
    @DisplayName("Deve retornar true se SKU existir")
    void shouldReturnTrueIfSkuExists() {
        ProductEntity entity = new ProductEntity(null, "Existing Product", "SKU789", BigDecimal.valueOf(59.99));
        repository.save(entity);

        assertTrue(repository.existsByProductSku("SKU789"));
    }

    @Test
    @DisplayName("Deve retornar false se SKU não existir")
    void shouldReturnFalseIfSkuDoesNotExist() {
        assertFalse(repository.existsByProductSku("SKU000"));
    }

    @Test
    @DisplayName("Deve retornar todos os produtos correspondentes a uma lista de SKUs")
    void shouldFindAllBySkuInList() {
        ProductEntity p1 = new ProductEntity(null, "Product A", "SKU111", BigDecimal.valueOf(10.0));
        ProductEntity p2 = new ProductEntity(null, "Product B", "SKU222", BigDecimal.valueOf(20.0));
        ProductEntity p3 = new ProductEntity(null, "Product C", "SKU333", BigDecimal.valueOf(30.0));
        repository.saveAll(List.of(p1, p2, p3));

        List<String> skus = Arrays.asList("SKU111", "SKU333");

        List<ProductEntity> foundProducts = repository.findAllByProductSkuIn(skus);

        assertEquals(2, foundProducts.size());
        assertTrue(foundProducts.stream().anyMatch(p -> p.getProductSku().equals("SKU111")));
        assertTrue(foundProducts.stream().anyMatch(p -> p.getProductSku().equals("SKU333")));
    }

}
