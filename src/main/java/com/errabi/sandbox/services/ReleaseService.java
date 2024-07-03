package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Release;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ReleaseRepository;
import com.errabi.sandbox.web.mapper.ReleaseMapper;
import com.errabi.sandbox.web.model.ReleaseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.errabi.sandbox.utils.SandboxConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final ReleaseRepository releaseRepository;
    private final ReleaseMapper releaseMapper;

    public ReleaseDto createRelease(ReleaseDto releaseDto){
        log.info("Creating release {} ..", releaseDto.getName());
        try {
            Release release = releaseMapper.toEntity(releaseDto);
            releaseRepository.save(release);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the release", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the release",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return releaseDto;
    }

    public ReleaseDto findReleaseById(Long releaseId) {
        log.info("Finding release with id {}",releaseId);
        Optional<Release> optionalRelease =  releaseRepository.findById(releaseId);
        if(optionalRelease.isPresent()){
            return releaseMapper.toDto(optionalRelease.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No release found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ReleaseDto> getReleaseByProductId(Long productId) {
        log.info("Finding release with product id");
        List<ReleaseDto> releases = releaseRepository.findReleasesByProductId(productId).stream()
                .map(releaseMapper::toDto)
                .toList();
        if(!releases.isEmpty()){
            return releases;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No release found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ReleaseDto> findAllReleases() {
        log.info("Fetching all releases...");
        List<Release> releases;
        try {
            releases = releaseRepository.findAll();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all releases", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        if (releases.isEmpty()) {
            log.warn("No release found in the database.");
            return Collections.emptyList();
        }
        return releases.stream().map(releaseMapper::toDto).toList();
    }

    public ReleaseDto updateRelease(ReleaseDto releaseDto) {
        log.info("Updating release {} ..", releaseDto.getId());
        Release existingRelease = releaseRepository.findById(releaseDto.getId()).orElse(null);
        if (existingRelease == null) {
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Product was found",
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            releaseMapper.updateFromDto(releaseDto, existingRelease);
            Release updatedRelease = releaseRepository.save(existingRelease);
            return releaseMapper.toDto(updatedRelease);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating product with ID {}", releaseDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating product",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public void deleteRelease(Long releaseId) {
        log.info("Deleting release with ID {}", releaseId);
        if (!releaseRepository.existsById(releaseId)) {
            log.error("Release not found");
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "release not found",
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            releaseRepository.deleteById(releaseId);
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the release with ID {}", releaseId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the release",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
