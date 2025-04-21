package br.com.fiap.msproducts.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
	
	private long id;
	private String name;
	private String sku;
	private Double price;

}
