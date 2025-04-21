package br.com.fiap.msproducts.infrastructure.persistence.repository;

import br.com.fiap.msproducts.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    
	Optional<ProductEntity> findBySku(String sku);
    
	boolean existsBySku(String sku);
}
