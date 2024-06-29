package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Product;
import com.errabi.sandbox.repositories.ProductRepository;
import com.errabi.sandbox.web.mapper.ProductMapper;
import com.errabi.sandbox.web.model.ProductDto;
import com.errabi.sandbox.web.model.ResponseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.SYSTEM_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto createProduct(ProductDto productDto){
        log.info("Creating product {} ..", productDto);
        try {
            Product product = productMapper.toEntity(productDto);
            productRepository.save(product);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the product", ex);
            var responseInfo = ResponseInfo.builder()
                                            .errorCode(SYSTEM_ERROR)
                                            .errorDescription(ex.getMessage())
                                            .build();
            productDto.setResponseInfo(responseInfo);
        }
        return productDto;
    }

    public ProductDto findProductById(Long productId) {
        log.info("Finding product with id {}",productId);
        Optional<Product> optionalProduct =  productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            return productMapper.toDto(optionalProduct.get());
        }else{
            log.error("Product not found with id {}", productId);
            return null;
        }
    }

    public List<ProductDto> findAllProducts() {
        log.info("Fetching all products...");
        List<Product> products;
        try {
            products = productRepository.findAll();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all products", ex);
            return Collections.emptyList();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    public ProductDto updateProduct(ProductDto productDto) {
        log.info("Updating product {} ..", productDto.getId());
        Product existingProduct = productRepository.findById(productDto.getId()).orElse(null);
        if (existingProduct == null) {
            log.error("Product not found");
        }
        try {
            productMapper.updateFromDto(productDto, existingProduct);
            Product updatedProduct = productRepository.save(existingProduct);
            return productMapper.toDto(updatedProduct);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating product with ID {}", productDto.getId(), ex);
            throw ex;
        }
    }

    public void deleteProduct(Long productId) {
        log.info("Deleting product with ID {}", productId);
        if (!productRepository.existsById(productId)) {
            log.error("Product not found");
        }
        try {
            productRepository.deleteById(productId);
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting product with ID {}", productId, ex);
            throw ex;
        }
    }
}
