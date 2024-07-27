package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Api;
import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.ApiRepository;
import com.errabi.sandbox.repositories.ModuleRepository;
import com.errabi.sandbox.web.mapper.ApiMapper;
import com.errabi.sandbox.web.model.ApiDto;
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
public class ApiService {
    private final ApiRepository apiRepository;
    private final ApiMapper apiMapper;
    private final ModuleRepository moduleRepository;

    @Transactional
    public ApiDto createApi(ApiDto apiDto){
        try {
            log.info("Creating api {} ..", apiDto.getName());
            Api api = apiMapper.toEntity(apiDto);
            if(apiDto.getModuleId() != null){
                api.setModule(moduleRepository.findById(apiDto.getModuleId()).orElse(null));
            }
            api = apiRepository.save(api);
            apiDto = apiMapper.toDto(api);
            apiDto.setResponseInfo(buildSuccessInfo());
            return apiDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Api");
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ApiDto findApiById(Long apiId) {
        log.info("Finding Api with id {}",apiId);
        Optional<Api> optionalApi = apiRepository.findById(apiId);
        if(optionalApi.isPresent()){
            ApiDto apiDto = apiMapper.toDto(optionalApi.get());
            apiDto.setResponseInfo(buildSuccessInfo());
            return apiDto;
        }else{
            log.info("Could not find module with id {}",apiId);
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ApiDto> getApiByModuleId(Long moduleId){
        try {
            log.info("Finding APIs with module id");
            List<Api> apis = apiRepository.findApisByModuleId(moduleId);
            if (!apis.isEmpty()) {
                return apis.stream()
                        .map(apiMapper::toDto)
                        .peek(apiDto -> apiDto.setResponseInfo(buildSuccessInfo()))
                        .toList();
            } else {
                return Collections.emptyList();
            }
        }catch (Exception ex){
            log.error("Unexpected error occurred while fetching all api with module id");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ApiDto> findAllApi() {
        try {
            log.info("Fetching all APIs...");
            List<Api> apis = apiRepository.findAll();
            if (!apis.isEmpty()) {
                return apis.stream()
                        .map(apiMapper::toDto)
                        .peek(apiDto -> apiDto.setResponseInfo(buildSuccessInfo()))
                        .toList();
            } else {
                return Collections.emptyList();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all APIs");
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ApiDto updateApi(ApiDto apiDto) {
        try {
            log.info("Updating API");
            Api existingApi = apiMapper.toEntity(findApiById(apiDto.getId()));
            apiMapper.updateFromDto(apiDto, existingApi);
            apiRepository.save(existingApi);
            apiDto.setResponseInfo(buildSuccessInfo());
            return apiDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating API");
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteApi(Long apiId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting API with ID {}", apiId);
            apiRepository.deleteById(findApiById(apiId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the API with ID {}", apiId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}
