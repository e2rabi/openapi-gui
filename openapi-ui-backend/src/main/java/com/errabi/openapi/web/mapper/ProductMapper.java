package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Product;
import com.errabi.openapi.web.model.ProductDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
    void updateFromDto(ProductDto productDto, @MappingTarget Product product);
}
