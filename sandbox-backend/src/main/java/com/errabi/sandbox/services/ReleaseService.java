package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Release;
import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ProductRepository;
import com.errabi.sandbox.repositories.ReleaseRepository;
import com.errabi.sandbox.web.mapper.ReleaseMapper;
import com.errabi.sandbox.web.model.ReleaseDto;
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
import static com.errabi.sandbox.utils.SandboxConstant.*;
import static com.errabi.sandbox.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final ReleaseRepository releaseRepository;
    private final ReleaseMapper releaseMapper;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Transactional
    public ReleaseDto createRelease(ReleaseDto releaseDto){
        try {
            log.info("Creating release {} ..", releaseDto.getName());
            Release release = releaseMapper.toEntity(releaseDto);
            if (releaseDto.getProductId() != null) {
                release.setProduct(productRepository.findById(releaseDto.getProductId()).orElse(null));
            }
            release = releaseRepository.save(release);
            releaseDto = releaseMapper.toDto(release);
            releaseDto.setResponseInfo(buildSuccessInfo());
            return releaseDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the release");
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ReleaseDto findReleaseById(Long releaseId) {
        log.info("Finding release with id {}",releaseId);
        Optional<Release> optionalRelease =  releaseRepository.findById(releaseId);
        if(optionalRelease.isPresent()){
            ReleaseDto releaseDto = releaseMapper.toDto(optionalRelease.get());
            releaseDto.setResponseInfo(buildSuccessInfo());
            return releaseDto;
        }else{
            log.info("Could not find release with id {}",releaseId);
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ReleaseDto> getReleaseByProductId(Long productId) {
        try {
            log.info("Finding release with product id");
            List<Release> releases = releaseRepository.findReleasesByProductId(productId);
            if (!releases.isEmpty()) {
                return releases.stream()
                        .map(releaseMapper::toDto)
                        .toList();
            }else{
                return Collections.emptyList();
            }
        } catch (Exception ex) {
            log.error("Unexpected error occurred while fetching all releases by product id");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public Page<ReleaseDto> findAllReleases(Pageable pageable) {
        try {
            log.info("Fetching all releases...");
            Page<Release> releases = releaseRepository.findAll(pageable);
            if (!releases.isEmpty()) {
                return releases.map(release -> {
                    ReleaseDto releaseDto = releaseMapper.toDto(release);
                    releaseDto.setProductName(productService.findProductById(release.getProduct().getId()).getName());
                    return releaseDto;
                });
            }else{
                return Page.empty();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all releases");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ReleaseDto updateRelease(ReleaseDto releaseDto) {
        try {
            log.info("Updating release");
            Release existingRelease = releaseMapper.toEntity(findReleaseById(releaseDto.getId()));
            releaseMapper.updateFromDto(releaseDto, existingRelease);
            releaseRepository.save(existingRelease);
            releaseDto.setResponseInfo(buildSuccessInfo());
            return releaseDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating release");
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteRelease(Long releaseId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting release with ID {}", releaseId);
            releaseRepository.deleteById(findReleaseById(releaseId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the release with ID {}", releaseId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
