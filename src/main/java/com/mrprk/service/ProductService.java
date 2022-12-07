package com.mrprk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.mrprk.dto.ProductDto;
import com.mrprk.repository.ProductRepository;
import com.mrprk.util.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;

	// To get list of all product from db
	public Flux<ProductDto> getProducts() {
		return repository.findAll().map(AppUtils::entityToDto);
	}

	// To get single product object based on Id
	public Mono<ProductDto> getProduct(String id) {
		return repository.findById(id).map(AppUtils::entityToDto);
	}

	// To get product based on price range
	public Flux<ProductDto> getProductInRange(double min, double max) {
		return repository.findByPriceBetween(Range.closed(min, max));
	}

	// To insert product data
	public Mono<ProductDto> saveProduct(Mono<ProductDto> productdtoMono) {
		return productdtoMono.map(AppUtils::dtoToEntity).flatMap(repository::insert).map(AppUtils::entityToDto);

	}

	// To update product
	public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id) {
		return repository.findById(id)
				.flatMap(p -> productDtoMono.map(AppUtils::dtoToEntity).doOnNext(e -> e.setId(id)))
				.flatMap(repository::save).map(AppUtils::entityToDto);

	}

	// To delete
	public Mono<Void> deleteProduct(String id) {
		return repository.deleteById(id);
	}

}
