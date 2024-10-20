package com.errabi.openapi.services;

import com.errabi.openapi.entities.Product;
import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.exception.TechnicalException;
import com.errabi.openapi.repositories.ProductRepository;
import com.errabi.openapi.repositories.WorkspaceRepository;
import com.errabi.openapi.web.mapper.ProductMapper;
import com.errabi.openapi.web.model.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.errabi.openapi.utils.SandboxConstant.*;
import static com.errabi.openapi.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceService workspaceService;

    @Transactional
    public ProductDto createProduct(ProductDto productDto){
        try {
            log.info("Creating product {} ..", productDto.getName());
            Product product = productMapper.toEntity(productDto);
            if (productDto.getWorkspaceId() != null) {
                product.setWorkspace(workspaceRepository.findById(productDto.getWorkspaceId()).orElse(null));
            }
            product = productRepository.save(product);
            productDto = productMapper.toDto(product);
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
            log.info("Could not find product with id {}",productId);
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ProductDto> getProductsByWorkspaceId(Long workspaceId) {
        try {
            log.info("Finding products with workspace id");
            List<Product> products = productRepository.findProductsByWorkspaceId(workspaceId);
            if (!products.isEmpty()) {
                return products.stream()
                        .map(productMapper::toDto)
                        .toList();
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

    public Page<ProductDto> findAllProducts(Pageable pageable) {
        try {
            log.info("Fetching all products...");
            Page<Product> products = productRepository.findAll(pageable);
            if(!products.isEmpty()){
                return products.map(product -> {
                    ProductDto productDto = productMapper.toDto(product);
                    productDto.setWorkspaceName(workspaceService.findWorkspaceById(product.getWorkspace().getId()).getName());
                    return productDto;
                });
            }else{
                return Page.empty();
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

    @Transactional
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
    public ErrorResponse deleteProduct(Long productId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting product with ID {}", productId);
            productRepository.deleteById(findProductById(productId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting product with ID {}", productId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
