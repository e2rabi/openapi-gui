package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.services.ProductService;
import com.errabi.sandbox.web.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sandbox-api/v1/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto){
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}")
    @Cacheable(value = "sandbox", key = "#productId")
    public ResponseEntity<ProductDto> getProductById(@PathVariable  Long productId){
        return new ResponseEntity<>(productService.findProductById(productId), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(productService.findAllProducts(PageRequest.of(page,pageSize)), HttpStatus.OK);
    }

    @GetMapping("/workspace/{id}/products")
    public ResponseEntity<List<ProductDto>> getReleaseByProductId(@PathVariable  Long id){
        List<ProductDto> productsDto = productService.getProductsByWorkspaceId(id);
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProduct(productDto), HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ErrorResponse> deleteProduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }
}
