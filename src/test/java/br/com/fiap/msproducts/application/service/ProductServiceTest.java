package br.com.fiap.msproducts.application.service;

import br.com.fiap.msproducts.application.dto.ProductDto;
import br.com.fiap.msproducts.application.mapper.ProductMapper;
import br.com.fiap.msproducts.domain.exception.ProductNotFoundException;
import br.com.fiap.msproducts.domain.model.Product;
import br.com.fiap.msproducts.infrastructure.persistence.entity.ProductEntity;
import br.com.fiap.msproducts.infrastructure.persistence.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        ProductDto dto = new ProductDto(0L, "Product A", "SKU123", 100.0);
        Product product = new Product(0L, "Product A", "SKU123", 100.0); // CORRIGIDO
        ProductEntity entity = new ProductEntity();
        ProductEntity savedEntity = new ProductEntity();
        Product savedProduct = new Product(1L, "Product A", "SKU123", 100.0);
        ProductDto savedDto = new ProductDto(1L, "Product A", "SKU123", 100.0);

        when(repository.existsBySku(dto.sku())).thenReturn(false);
        when(mapper.toDomain(dto)).thenReturn(product);
        when(mapper.toEntity(product)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedProduct);
        when(mapper.toDto(savedProduct)).thenReturn(savedDto);

        ProductDto result = service.create(dto);

        assertNotNull(result);
        assertEquals("SKU123", result.sku());
        verify(repository, times(1)).save(entity);
    }


    @Test
    void shouldThrowExceptionWhenSkuAlreadyExists() {
        ProductDto dto = new ProductDto(0L, "Product A", "SKU123", 100.0);

        when(repository.existsBySku("SKU123")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
    }

    @Test
    void shouldReturnAllProducts() {
        ProductEntity entity1 = new ProductEntity();
        ProductEntity entity2 = new ProductEntity();
        Product product1 = new Product(1L, "Product A", "SKU123", 50.0);
        Product product2 = new Product(2L, "Product B", "SKU456", 75.0);
        ProductDto dto1 = new ProductDto(1L, "Product A", "SKU123", 50.0);
        ProductDto dto2 = new ProductDto(2L, "Product B", "SKU456", 75.0);

        when(repository.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(product1);
        when(mapper.toDomain(entity2)).thenReturn(product2);
        when(mapper.toDto(product1)).thenReturn(dto1);
        when(mapper.toDto(product2)).thenReturn(dto2);

        List<ProductDto> result = service.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void shouldFindProductById() throws ProductNotFoundException {
        ProductEntity entity = new ProductEntity();
        Product product = new Product(1L, "Product A", "SKU123", 50.0);
        ProductDto dto = new ProductDto(1L, "Product A", "SKU123", 50.0);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(product);
        when(mapper.toDto(product)).thenReturn(dto);

        ProductDto result = service.findById(1L);

        assertEquals("SKU123", result.sku());
    }

    @Test
    void shouldThrowWhenProductNotFoundById() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void shouldUpdateProductSuccessfully() throws ProductNotFoundException {
        ProductDto dto = new ProductDto(1L, "Updated Product", "SKU123", 150.0);
        ProductEntity entity = new ProductEntity();
        Product updatedProduct = new Product(1L, "Updated Product", "SKU123", 150.0);
        ProductDto updatedDto = new ProductDto(1L, "Updated Product", "SKU123", 150.0);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(updatedProduct);
        when(mapper.toDto(updatedProduct)).thenReturn(updatedDto);

        ProductDto result = service.update(1L, dto);

        assertEquals("Updated Product", result.name());
        verify(repository, times(1)).save(entity);
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentProduct() {
        ProductDto dto = new ProductDto(1L, "Updated Product", "SKU123", 150.0);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.update(1L, dto));
    }

    @Test
    void shouldDeleteProductSuccessfully() throws ProductNotFoundException {
        ProductEntity entity = new ProductEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        String result = service.delete(1L);

        assertEquals("Product deleted successfully!", result);
        verify(repository).delete(entity);
    }

    @Test
    void shouldThrowWhenDeletingNonexistentProduct() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void shouldFindProductBySku() throws ProductNotFoundException {
        ProductEntity entity = new ProductEntity();
        Product product = new Product(1L, "Product", "SKU999", 99.99);
        ProductDto dto = new ProductDto(1L, "Product", "SKU999", 99.99);

        when(repository.findBySku("SKU999")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(product);
        when(mapper.toDto(product)).thenReturn(dto);

        ProductDto result = service.findBySku("SKU999");

        assertEquals("SKU999", result.sku());
    }

    @Test
    void shouldThrowWhenProductNotFoundBySku() {
        when(repository.findBySku("NOT_FOUND")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.findBySku("NOT_FOUND"));
    }
}
