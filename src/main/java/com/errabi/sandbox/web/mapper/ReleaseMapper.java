package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Release;
import com.errabi.sandbox.services.ProductService;
import com.errabi.sandbox.web.model.ReleaseDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ReleaseMapper {
    @Autowired
    private ProductService productService;
    public abstract Release toEntity(ReleaseDto releaseDto);
    public abstract ReleaseDto toDto(Release release);
    public abstract void updateFromDto(ReleaseDto releaseDto, @MappingTarget Release release);
    @AfterMapping
    protected void setProduct(ReleaseDto releaseDto, @MappingTarget Release release) {
        if (releaseDto.getProductId() != null) {
            release.setProduct(productService.getProductById(releaseDto.getProductId()));
        }
    }
}
