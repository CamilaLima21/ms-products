package br.com.fiap.msproducts.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
	
	private long id;
	private String name;
	private String productSku;
	private BigDecimal price;

}
