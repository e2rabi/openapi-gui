package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Product;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ProductRepository;
import com.errabi.sandbox.web.mapper.ProductMapper;
import com.errabi.sandbox.web.model.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.errabi.sandbox.utils.SandboxConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final WorkspaceService workspaceService;

    @Transactional
    public ProductDto createProduct(ProductDto productDto){
        try {
            log.info("Creating product {} ..", productDto.getName());
            Product product = productMapper.toEntity(productDto);

            if (productDto.getWorkspaceId() != null) {
                product.setWorkspace(workspaceService.getWorkspaceById(productDto.getWorkspaceId()));
            }
            productRepository.save(product);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the product", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the product",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return productDto;
    }


    public ProductDto findProductById(Long productId) {
        log.info("Finding product with id {}",productId);
        Optional<Product> optionalProduct =  productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            return productMapper.toDto(optionalProduct.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No product found",
                    HttpStatus.NOT_FOUND);
        }
    }

    //@Transactional
    //public Product getProductById(Long productId){return productRepository.findById(productId).orElse(null);}

    public List<ProductDto> getProductsByWorkspaceId(Long workspaceId) {
        log.info("Finding release with product id");
        List<ProductDto> products = productRepository.findProductsByWorkspaceId(workspaceId).stream()
                .map(productMapper::toDto)
                .toList();
        if(!products.isEmpty()){
            return products;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No products found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ProductDto> findAllProducts() {
        log.info("Fetching all products...");
        List<Product> products;
        try {
            products = productRepository.findAll();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all products", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        if (products.isEmpty()) {
            log.warn("No product found in the database.");
            return Collections.emptyList();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    public ProductDto updateProduct(ProductDto productDto) {
        log.info("Updating product {} ..", productDto.getId());
        Product existingProduct = productRepository.findById(productDto.getId()).orElse(null);
        if (existingProduct == null) {
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Product was found",
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            productMapper.updateFromDto(productDto, existingProduct);
            Product updatedProduct = productRepository.save(existingProduct);
            return productMapper.toDto(updatedProduct);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating product with ID {}", productDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating product",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        try {
            log.info("Deleting product with ID {}", productId);
            productRepository.deleteById( findProductById(productId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting product with ID {}", productId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the product",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
