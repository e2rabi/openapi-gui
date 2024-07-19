package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Product;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ProductRepository;
import com.errabi.sandbox.repositories.WorkspaceRepository;
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
import static com.errabi.sandbox.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public ProductDto createProduct(ProductDto productDto){
        try {
            log.info("Creating product {} ..", productDto.getName());
            Product product = productMapper.toEntity(productDto);
            productRepository.save(product);
            if (productDto.getWorkspaceId() != null) {
                product.setWorkspace(workspaceRepository.findById(productDto.getWorkspaceId()).orElse(null));
            }
            productDto.setResponseInfo(buildSuccessInfo());
            return productDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the product");
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ProductDto findProductById(Long productId) {
        log.info("Finding product with id {}",productId);
        Optional<Product> optionalProduct =  productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            ProductDto productDto = productMapper.toDto(optionalProduct.get());
            productDto.setResponseInfo(buildSuccessInfo());
            return productDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ProductDto> getProductsByWorkspaceId(Long workspaceId) {
        try {
            log.info("Finding products with workspace id");
            List<ProductDto> products = productRepository.findProductsByWorkspaceId(workspaceId).stream()
                    .map(productMapper::toDto)
                    .toList();
            if (!products.isEmpty()) {
                return products;
            } else {
                return Collections.emptyList();
            }
        }catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all products with workspace id");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<ProductDto> findAllProducts() {
        try {
            log.info("Fetching all products...");
            List<Product> products = productRepository.findAll();
            if(!products.isEmpty()){
                return products.stream().map(productMapper::toDto).toList();
            }else{
                return Collections.emptyList();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all products");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ProductDto updateProduct(ProductDto productDto) {
        try {
            log.info("Updating product {} ..", productDto.getId());
            Product existingProduct = productMapper.toEntity(findProductById(productDto.getId()));
            productMapper.updateFromDto(productDto, existingProduct);
            productRepository.save(existingProduct);
            productDto.setResponseInfo(buildSuccessInfo());
            return productDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating product with ID {}", productDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        try {
            log.info("Deleting product with ID {}", productId);
            productRepository.deleteById(findProductById(productId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting product with ID {}", productId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
