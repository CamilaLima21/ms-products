package br.com.fiap.msproducts.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.msproducts.infrastructure.persistence.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByProductSku(String productSku); // Corrigido
    boolean existsByProductSku(String productSku);              // Corrigido
    List<ProductEntity> findAllByProductSkuIn(List<String> productSkus); // Corrigido
}
