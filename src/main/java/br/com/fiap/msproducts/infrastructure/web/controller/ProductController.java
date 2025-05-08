package br.com.fiap.msproducts.infrastructure.web.controller;

import br.com.fiap.msproducts.application.dto.ProductDto;
import br.com.fiap.msproducts.application.service.ProductService;
import br.com.fiap.msproducts.domain.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable long id) throws ProductNotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDto> findBySku(@PathVariable String sku) throws ProductNotFoundException {
        return ResponseEntity.ok(service.findBySku(sku));
    }
    
    @GetMapping("/sku")
    public ResponseEntity<List<ProductDto>> findBySkus(@RequestParam List<String> skus) {
        List<ProductDto> products = skus.stream()
                .map(sku -> {
                    try {
                        return service.findBySku(sku);
                    } catch (ProductNotFoundException e) {
                        throw new RuntimeException("Produto com SKU " + sku + " não encontrado.");
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable long id, @Valid @RequestBody ProductDto dto) throws ProductNotFoundException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) throws ProductNotFoundException {
        return ResponseEntity.ok(service.delete(id));
    }
}
