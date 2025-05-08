package br.com.fiap.msproducts.application.service;

import br.com.fiap.msproducts.application.dto.ProductDto;
import br.com.fiap.msproducts.application.mapper.ProductMapper;
import br.com.fiap.msproducts.domain.exception.ProductNotFoundException;
import br.com.fiap.msproducts.domain.model.Product;
import br.com.fiap.msproducts.infrastructure.persistence.entity.ProductEntity;
import br.com.fiap.msproducts.infrastructure.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductDto create(ProductDto dto) {
        if (repository.existsByProductSku(dto.productSku())) {
            throw new IllegalArgumentException("SKU already registered.");
        }

        Product product = mapper.toDomain(dto);
        ProductEntity savedEntity = repository.save(mapper.toEntity(product));
        return mapper.toDto(mapper.toDomain(savedEntity));
    }

    public List<ProductDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(long id) throws ProductNotFoundException {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
        return mapper.toDto(mapper.toDomain(entity));
    }

    public ProductDto update(long id, ProductDto dto) throws ProductNotFoundException {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));

        entity.setName(dto.name());
        entity.setPrice(dto.price());

        ProductEntity updated = repository.save(entity);
        return mapper.toDto(mapper.toDomain(updated));
    }

    public String delete(long id) throws ProductNotFoundException {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
        repository.delete(entity);
        return "Product deleted successfully!";
    }

    public ProductDto findBySku(String sku) throws ProductNotFoundException {
        ProductEntity entity = repository.findByProductSku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product with SKU " + sku + " not found."));
        return mapper.toDto(mapper.toDomain(entity));
    }
    
    public void validateSkus(List<String> skus) {
        List<String> existingSkus = repository.findAllByProductSkuIn(skus)
                .stream()
                .map(ProductEntity::getProductSku)
                .toList();

        List<String> notFoundSkus = skus.stream()
                .filter(sku -> !existingSkus.contains(sku))
                .toList();

        if (!notFoundSkus.isEmpty()) {
            throw new ProductNotFoundException("The following SKUs were not found: " + notFoundSkus);
        }
    }
}
