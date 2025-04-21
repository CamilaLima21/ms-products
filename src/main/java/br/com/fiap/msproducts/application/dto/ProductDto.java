package br.com.fiap.msproducts.application.dto;

public record ProductDto(
    	long id,
    	String name,
    	String sku,
    	Double price
    ) {}