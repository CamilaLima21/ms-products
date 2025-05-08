package br.com.fiap.msproducts.application.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.msproducts.application.dto.ProductDto;
import br.com.fiap.msproducts.domain.model.Product;
import br.com.fiap.msproducts.infrastructure.persistence.entity.ProductEntity;

@Component
public class ProductMapper {
	
	public Product toDomain(ProductEntity entity) {
		return new Product(
				entity.getId(),
				entity.getName(),
				entity.getProductSku(),
				entity.getPrice()
		);
	}
	
	public ProductEntity toEntity(Product domain) {
		ProductEntity entity = new ProductEntity();
		entity.setName(domain.getName());
		entity.setProductSku(domain.getProductSku());
		entity.setPrice(domain.getPrice());
		return entity;
	}
	
	public ProductDto toDto(Product domain) {
        return new ProductDto(
                domain.getId(),
                domain.getName(),
                domain.getProductSku(),
                domain.getPrice()
        );
    }
	
	public Product toDomain(ProductDto dto) {
        return new Product(
                dto.id(),
                dto.name(),
                dto.productSku(),
                dto.price()
        );
    }
    
	public ProductDto toDto(ProductEntity entity) {
        return new ProductDto(
        		entity.getId(), 
        		entity.getName(), 
        		entity.getProductSku(), 
        		entity.getPrice());
    }

    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.name());
        entity.setProductSku(dto.productSku());
        entity.setPrice(dto.price());
        return entity;
    }
    
    
}