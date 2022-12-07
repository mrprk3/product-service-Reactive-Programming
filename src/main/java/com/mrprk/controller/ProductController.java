package com.mrprk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrprk.dto.ProductDto;
import com.mrprk.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService service;

	// get all product
	@GetMapping
	public Flux<ProductDto> getProducts() {
		return service.getProducts();
	}

	// get product based on id
	@GetMapping("/{id}")
	public Mono<ProductDto> getProduct(@PathVariable String id) {
		return service.getProduct(id);

	}

	// get product between range
	@GetMapping("/product-range")
	public Flux<ProductDto> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max") double max) {
		return service.getProductInRange(min, max);
	}

	// save product
	@PostMapping
	public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono) {
		return service.saveProduct(productDtoMono);
	}

	@PutMapping("/update/{id}")
	public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable String id) {
		return service.updateProduct(productDtoMono, id);
	}

	// To delete
	@DeleteMapping("/delete/{id}")
	public Mono<Void> deleteProduct(@PathVariable String id) {
		return service.deleteProduct(id);
	}

}
