package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Product;
import com.errabi.sandbox.web.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
    void updateFromDto(ProductDto productDto, @MappingTarget Product product);
}
