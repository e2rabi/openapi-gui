package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Configuration;
import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ConfigurationRepository;
import com.errabi.sandbox.web.mapper.ConfigurationMapper;
import com.errabi.sandbox.web.model.ConfigurationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;
import static com.errabi.sandbox.utils.SandboxConstant.DELETE_ERROR_DESCRIPTION;
import static com.errabi.sandbox.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;
    private final ConfigurationMapper configurationMapper;

    @Transactional
    public ConfigurationDto createConfig(ConfigurationDto configurationDto){
        try {
            log.info("Creating Configuration {} ..", configurationDto.getKey());
            Configuration configuration =  configurationRepository.save(configurationMapper.toEntity(configurationDto));
            configurationDto = configurationMapper.toDto(configuration);
            configurationDto.setResponseInfo(buildSuccessInfo());
            return configurationDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the configuration");
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ConfigurationDto findConfigById(Long configId) {
        log.info("Finding configuration with id {}",configId);
        Optional<Configuration> optionalConfiguration =  configurationRepository.findById(configId);
        if(optionalConfiguration.isPresent()){
            ConfigurationDto configurationDto = configurationMapper.toDto(optionalConfiguration.get());
            configurationDto.setResponseInfo(buildSuccessInfo());
            return configurationDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public ConfigurationDto findConfigByKey(String key) {
        log.info("Finding configuration with key {}",key);
        Optional<Configuration> optionalConfiguration =  configurationRepository.findConfigurationByKey(key);
        if(optionalConfiguration.isPresent()){
            ConfigurationDto configurationDto = configurationMapper.toDto(optionalConfiguration.get());
            configurationDto.setResponseInfo(buildSuccessInfo());
            return configurationDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Page<ConfigurationDto> findAllConfigurations(int page, int size) {
        try {
            log.info("Fetching all configurations...");
            Pageable pageable = PageRequest.of(page, size);
            Page<Configuration> configurationPage = configurationRepository.findAll(pageable);
            return configurationPage.isEmpty() ?
                            Page.empty() :
                            configurationPage.map(configurationMapper::toDto);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all configurations", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Transactional
    public ErrorResponse deleteConfiguration(Long configId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting configuration with ID {}", configId);
            configurationRepository.deleteById(findConfigById(configId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting configuration with ID {}", configId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
