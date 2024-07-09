package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Api;
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

import java.util.List;
import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;

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
            apiRepository.save(api);
            return apiDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Api", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the Api",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ApiDto findApiById(Long apiId) {
        log.info("Finding Api with id {}",apiId);
        Optional<Api> optionalApi = apiRepository.findById(apiId);
        if(optionalApi.isPresent()){
            return apiMapper.toDto(optionalApi.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Api found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ApiDto> getApiByModuleId(Long moduleId){
        log.info("Finding APIs with module id");
        List<ApiDto> apisDto = apiRepository.findApisByModuleId(moduleId).stream()
                .map(apiMapper::toDto)
                .toList();
        if(!apisDto.isEmpty()){
            return apisDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No APIs found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<ApiDto> findAllApi() {
        try {
            log.info("Fetching all APIs...");
            List<Api> apis = apiRepository.findAll();
            return apis.stream().map(apiMapper::toDto).toList();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all APIs", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public ApiDto updateApi(ApiDto apiDto) {
        try {
            log.info("Updating API {} ..", apiDto.getId());
            Api existingApi = apiMapper.toEntity(findApiById(apiDto.getId()));
            apiMapper.updateFromDto(apiDto, existingApi);
            Api updatedApi = apiRepository.save(existingApi);
            return apiMapper.toDto(updatedApi);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating API with ID {}", apiDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating API",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteApi(Long apiId) {
        try {
            log.info("Deleting API with ID {}", apiId);
            apiRepository.deleteById(findApiById(apiId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting the API with ID {}", apiId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the API",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
