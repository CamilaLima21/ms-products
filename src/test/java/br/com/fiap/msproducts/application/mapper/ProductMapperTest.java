package br.com.fiap.msproducts.application.mapper;

import br.com.fiap.msproducts.application.dto.ProductDto;
import br.com.fiap.msproducts.domain.model.Product;
import br.com.fiap.msproducts.infrastructure.persistence.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapper();
    }

    @Test
    void shouldConvertEntityToDomain() {
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setName("Produto A");
        entity.setSku("SKU123");
        entity.setPrice(99.99);

        Product product = mapper.toDomain(entity);

        assertEquals(entity.getId(), product.getId());
        assertEquals(entity.getName(), product.getName());
        assertEquals(entity.getSku(), product.getSku());
        assertEquals(entity.getPrice(), product.getPrice());
    }

    @Test
    void shouldConvertDomainToEntity() {
        Product domain = new Product(2L, "Produto B", "SKU456", 199.90);

        ProductEntity entity = mapper.toEntity(domain);

        assertNull(entity.getId()); // ID não é setado no método
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getSku(), entity.getSku());
        assertEquals(domain.getPrice(), entity.getPrice());
    }

    @Test
    void shouldConvertDomainToDto() {
        Product domain = new Product(3L, "Produto C", "SKU789", 149.50);

        ProductDto dto = mapper.toDto(domain);

        assertEquals(domain.getId(), dto.id());
        assertEquals(domain.getName(), dto.name());
        assertEquals(domain.getSku(), dto.sku());
        assertEquals(domain.getPrice(), dto.price());
    }

    @Test
    void shouldConvertDtoToDomain() {
        ProductDto dto = new ProductDto(4L, "Produto D", "SKU999", 55.00);

        Product domain = mapper.toDomain(dto);

        assertEquals(dto.id(), domain.getId());
        assertEquals(dto.name(), domain.getName());
        assertEquals(dto.sku(), domain.getSku());
        assertEquals(dto.price(), domain.getPrice());
    }

    @Test
    void shouldConvertEntityToDto() {
        ProductEntity entity = new ProductEntity();
        entity.setId(5L);
        entity.setName("Produto E");
        entity.setSku("SKU000");
        entity.setPrice(88.88);

        ProductDto dto = mapper.toDto(entity);

        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getName(), dto.name());
        assertEquals(entity.getSku(), dto.sku());
        assertEquals(entity.getPrice(), dto.price());
    }

    @Test
    void shouldConvertDtoToEntity() {
        ProductDto dto = new ProductDto(6L, "Produto F", "SKU777", 22.22);

        ProductEntity entity = mapper.toEntity(dto);

        assertNull(entity.getId()); // ID não é setado no método
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.sku(), entity.getSku());
        assertEquals(dto.price(), entity.getPrice());
    }
}
